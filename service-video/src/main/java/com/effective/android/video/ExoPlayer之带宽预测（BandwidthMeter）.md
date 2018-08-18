### BandwidthMeter

处理在线 `DataSource` 资源时 ， 常需要考虑当前设备的网络问题 。 `BandwidthMeter` 用于检测当前数据下载的带宽问题 ， 方便分析因网络问题导致的数据源下载问题 。

`BandwidthMeter` 是带宽检测的核心接口 ， 提供了暴露数据转移信息的 `EventListener` 接口 。 同时提供 `#getBitrateEstimate` 用于获取当前 **比特率** 。

*tip* : 如果你不了解 **比特率** ， 可查看 [多媒体音视频核心概念](http://yummylau.com/2018/05/25/%E5%A4%9A%E5%AA%92%E4%BD%93_2018-05-25_%E5%A4%9A%E5%AA%92%E4%BD%93%E9%9F%B3%E8%A7%86%E9%A2%91%E6%A0%B8%E5%BF%83%E6%A6%82%E5%BF%B5/) 。

#### 前置知识

* 移动平均数

    即特定时间段内 ， 对事件序列数据进行移动计算平均值 。 举个直观的栗子 。
    如果存在数据序列 [ 10 , 2 , 5 , 4 , 7 , 6 , 7 , 6 , 5 , 9 , 10 , 8 ] 共 12 个序列数据 。
    对时间序列内的所有数据求**算术平均值** :

    ```
    ( 10 + 2 + 5 + 4 + 7 + 6 + 7 + 6 + 5 + 9 + 10 + 8 ) / 12 ≈ 6.58
    ```
    若取 6 项移动平均 ， 则**移动平均数**分别为
    ```
    ( 10 + 2 + 5 + 4 + 7 + 6 ) / 6 ≈ 5.66
    ( 2 + 5 + 4 + 7 + 6 + 7 ) / 6 ≈ 5.16
    ( 5 + 4 + 7 + 6 + 7 + 6 ) / 6 ≈ 5.83
    ( 4 + 7 + 6 + 7 + 6 + 5 ) / 6 ≈ 5.83
    ( 7 + 6 + 7 + 6 + 5 + 9 ) / 6 ≈ 6.66
    ( 6 + 7 + 6 + 5 + 9 + 10 ) / 6 ≈ 7.16
    ( 7 + 6 + 5 + 9 + 10 + 8 ) / 6 ≈ 7.5
    ```
    下图是该栗子的数据对比及图表
    事实上 ， 算术平均预测法是对时间序列的全部观察数据求一个平均值 ， 该平均值只能反映现象在观察期内的平均水平 ， 不能反映出趋势的变化 。 而移动平均预测法是按一定的平均项数滑动着对时间序列求一系列平均值 (也叫平滑值) 。 这些平滑值可用于做下一时间序列数据的预测 。 值得注意的是 ， 移动平均算法还可以考虑加权处理 。

* 滑动窗口

    即定义一个大小为 W 的线性数据结构 （可用数组） 作为窗口 。 存在一个线性数据集 Array ， 窗口从数据集的最左边开始滑动至最右边 ， 每次向右滑动一个数据集单元 。
    常见的场景存在于 TCP 协议中应用的 *滑动窗口协议* 。

* 预测逻辑
    *  对于每一次请求到的数据块封装成特定数据结构 ， 其下标递增 （index） ， 依次进入窗口接口 （区别于常规窗口主动滑动） 。
    *  给定窗口临界点 ， 每当数据块进入窗口时 ， 计算是否满足临界点 ， 如果满足则需要删减窗口内数据块 。
    *  给定数据块下载临界点 ， 用于特定时刻下 ， 计算当前带宽预估值 。


#### 带宽预测实现

**ExoPlayer** 源码中提供了 `SlidingPercentile` 用于滑动窗口逻辑处理 ， 其完整带宽检测逻辑封装于 `DefaultBandwidthMeter` 。 该类默认实现了带宽检测核心接口 `BandwidthMeter` 。

先列出预测实现的流程 , 可清晰地了解掌握整个逻辑。
1. 在线媒体源加载时 ， 对加载数据进行统计收集
2. 定义预测模型 ， 通过 “ 滑动窗口 ” 算法处理每一块传输的样本数据 ， 在特定时刻计算带宽
    * 实现样本数据结构
    * 实现滑动窗口 ， 保留最能代表当前带宽量的数据源
    * 实现触发计算带宽的逻辑


##### 收集数据
`DefaultBandwidthMeter` 除了实现了 `BandwidthMeter` 接口外 ， 还实现了 `TransferListener<Object>` 接口 。 该接口定义了一下三个方法 ：

```
public interface TransferListener<S> {
   void onTransferStart(S source, DataSpec dataSpec);
   void onBytesTransferred(S source, int bytesTransferred);
   void onTransferEnd(S source);
 }
```

这三个方法分别对应 **转移开始** ， **转移时** ， **转移结束**  阶段 。
在 [ExoPlayer之媒体源加载（DataSource）]() 中讲过 `DataSouce` 的数据加载流程 ， 而三个接口分别会在 `DataSource` 的实现类中被调用 。

* open()  ->  DataSouce#onTransferStart ， 单个 DataSpec 加载只会调用一次
* read()  ->  DataSouce#onBytesTransferred ， 单个 DataSpec
* close()  ->  DataSouce#onTransferEnd

上述 *dataSource* 数据中可能加载多个数据段 *dataSpec* , 每一个 *dataSpec* 的加载过程会分别调用上述3个方法一次 。

##### 样本结构

在数据收集流程中 ， 每一个 *dataSpec* 的数据量即转移量 。

根据每次得到的转移量封装成特定的数据结构 ， `SlidingPercentile` 中存在 `Sample` 就是该接口的封装 。

```
  private static class Sample {
    public int index;
    public int weight;
    public float value;

  }
```

其中各个属性定义为 ：

* *index* : 用于递增标识每一个数据样本进入窗口的顺序
* *weight* : 数据样本的权重 ， **ExoPlayer** 定义为每一个 *dataSpec* 转移量（ 字节 ）的平方根作为 *weight* 值
* *value* ： 数据样本的值 ， **ExoPlayer** 定义为每一个 *dataSpec* 获取时的比特率作为 *value* 值

##### 滑动窗口

`SlidingPercentile` 中用 *list* 来实现滑动窗口的逻辑 。

```
  private final ArrayList<Sample> samples;      //用于存放样本的队列
  private final Sample[] recycledSamples;       //用于复用样本对象
```

下面通过 `SlidingPercentile` 添加和删除样本的逻辑来了解如何用 *samples* 队列模拟滑动窗口 。

```
 public void addSample(int weight, float value) {

    //1. 按照index排序
    ensureSortedByIndex();

    //2. 构建 sample 对象
    Sample newSample = recycledSampleCount > 0 ? recycledSamples[--recycledSampleCount]
        : new Sample();
    newSample.index = nextSampleIndex++;
    newSample.weight = weight;
    newSample.value = value;
    .add(newSample);
    totalWeight += weight;

    //3. 已存在的样本总权重超过了临界值 ，则需要依次从链表的头部开始删除 。
    // 如果待删样本的权重大于溢出权重 ， 则直接从待删样本的权重中减去但不删除
    // 反之则直接删除待删样本
    while (totalWeight > maxWeight) {
      int excessWeight = totalWeight - maxWeight;
      Sample oldestSample = samples.get(0);
      if (oldestSample.weight <= excessWeight) {
        totalWeight -= oldestSample.weight;
        samples.remove(0);
        if (recycledSampleCount < MAX_RECYCLED_SAMPLES) {
          recycledSamples[recycledSampleCount++] = oldestSample;
        }
      } else {
        oldestSample.weight -= excessWeight;
        totalWeight -= excessWeight;
      }
    }
  }

```

队列每次新增样本之后 ， 都需要重新计算队列内的样本总权重是否超过 *maxWeight* 阈值 。
* 如果超过 ， 则需要计算是否删除最先进 *samples* 的样本 ， 记为 A
    * 如果权重的溢出量小于 A.weight ， 则从 A.weight 中减去溢出量
    * 如果权重的溢出量大于 A.weigth ， 则删除 A 并继续循环判断是否超出 。
所以 ， 样本队列始终保持 “ 窗口 ” 内的数据经过计算之后能反应当前的带宽情况 。

##### 预估带宽条件
在 **收集数据** 中得知 ， 单次 *dataSpec* 结束转移发生在 `onTransferEnd` 方法中 ， 所以触发预估的时间点就在 `onTransferEnd` 。

先看下 **转移开始** ， **转移时** ， **转移结束** 在 `DefaultBandwidthMeter` 中的实现 。

```
  @Override
  public synchronized void onTransferStart(Object source, DataSpec dataSpec) {
    if (streamCount == 0) {
      sampleStartTimeMs = clock.elapsedRealtime();
    }
    streamCount++;
  }
```
从 `open` 打开 *dataSpec* 时记录转移前的时刻 。 *streamCount* 用于记录 *dataSource* 中多个 *dataSpec* 的数据流请求总数 。

```
  @Override
  public synchronized void onBytesTransferred(Object source, int bytes) {
    sampleBytesTransferred += bytes;
  }
```

*sampleBytesTransferred* 记录了 *dataSource* 请求过程累计请求数据总量 。

```
  @Override
  public synchronized void onTransferEnd(Object source) {
    Assertions.checkState(streamCount > 0);
    long nowMs = clock.elapsedRealtime();

    //1. 记录上一次 dataSpec 数据传输结束到本次 dataSpec数据传输结束之间的时间间隔 A
    int sampleElapsedTimeMs = (int) (nowMs - sampleStartTimeMs);
    totalElapsedTimeMs += sampleElapsedTimeMs;
    totalBytesTransferred += sampleBytesTransferred;

    if (sampleElapsedTimeMs > 0) {
      //2. 计算比特率 ， 添加样本到 samples 队列
      float bitsPerSecond = (sampleBytesTransferred * 8000) / sampleElapsedTimeMs;
      slidingPercentile.addSample((int) Math.sqrt(sampleBytesTransferred), bitsPerSecond);

      //3. 如果 datasource 传输量已达到512 KB 或者 A 大于 2000 毫秒
      if (totalElapsedTimeMs >= ELAPSED_MILLIS_FOR_ESTIMATE
          || totalBytesTransferred >= BYTES_TRANSFERRED_FOR_ESTIMATE) {
        bitrateEstimate = (long) slidingPercentile.getPercentile(0.5f);
      }
    }
    notifyBandwidthSample(sampleElapsedTimeMs, sampleBytesTransferred, bitrateEstimate);

    //4. 更新 "上一次 dataSpec 传输数据结束" 时间点
    if (--streamCount > 0) {
      sampleStartTimeMs = nowMs;
    }
    sampleBytesTransferred = 0;
  }
```

从 `onTransferEnd` 可看到数据块 *dataSpec* 是异步传输 。 每一个传输方法都是上锁处理的 。 所以优先结束下载 （并不代表成功下载 ， 有可能是发生异常导致结束）的 *dataSpec* 会优先返回处理 ， 这个场景几乎和 `TCP协议` 中 `滑动窗口协议` 场景一模一样 。

实际上 ， 每一次 *dataSpec* 的结束加载都会调用 `notifyBandwidthSample` 通知当前带宽情况 ， 单并不是每次都会重新预估 。 除非满足一下条件 ：

* *dataSource* 数据传输量已达到 512 kB
* 距上次结束传输时刻已超过 2000 毫秒

##### 得到预测值

既然知道了触发条件 ， 那么在触发时刻 ， 得到的预估值是否能代表当前带宽的情况呢 ？

> 忽略 ExoPlayer 预置比特率阈值 。假如用户在下载一个视频 ， 一直保持了 1 M/s , 下载了一段时间外进入弱网 （1 b / s）， ExoPlayer 此时的感知带宽环境应该是极其糟糕的 。

`SlidingPercentile#getPercentile` 给出了预估值的实现 。

```
  //1. 获取预测值
  bitrateEstimate = (long) slidingPercentile.getPercentile(0.5f)

  public float getPercentile(float percentile) {
    //2. 按照样本比特率排序
    ensureSortedByValue();
    float desiredWeight = percentile * totalWeight;
    int accumulatedWeight = 0;

    //3. 从低到高开始累积样本 ， 累积的样本权重不超过 1/2 totalWeight ， 取累积最后一个样本的比特率作为带宽预测值 。
    for (int i = 0; i < samples.size(); i++) {
      Sample currentSample = samples.get(i);
      accumulatedWeight += currentSample.weight;
      if (accumulatedWeight >= desiredWeight) {
        return currentSample.value;
      }
    }
    // Clamp to maximum value or NaN if no values.
    return samples.isEmpty() ? Float.NaN : samples.get(samples.size() - 1).value;
  }

```

从源码中可知 *totalWeight* 的值为 2000 ， 按照 *sample.weight* 的含义可知 ， 累积的样本代表的转移量不能超过 2000 * 2000 / 2 B 约等于 1.9 MB 。

如果当前下载速度 1M/s ， 每个数据块 *dataSpec* 大约 0.25 M ， 则理想情况队列能放 3.8 MB 结构数据 。

```
sample1 , sample2 , sample3 ... sample16
```

此时进入弱网 ， 下载速度下降为 1 B/s ， 如果因为超时或者下载完成的次数足够多的话 ， 理论上队列能存放的 sample 就变得很多

```
sample1 , sample2 , sample3 ... sampleN (N 极大)
```

