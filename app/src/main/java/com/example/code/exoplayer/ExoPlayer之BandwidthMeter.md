### BandwidthMeter

处理在线 `DataSource` 资源时 ， 常需要考虑当前设备的网络问题 。 `BandwidthMeter` 用于检测当前数据下载的带宽问题 ， 方便分析因网络问题导致的数据源下载问题 。

`BandwidthMeter` 是带宽检测的核心接口 ， 提供了暴露数据转移信息的 `EventListener` 接口 。 同时提供 `#getBitrateEstimate` 用于获取当前 **比特率** 。

*tip* : 如果你不了解 **比特率** ， 可查看 [多媒体音视频核心概念](http://yummylau.com/2018/05/25/%E5%A4%9A%E5%AA%92%E4%BD%93_2018-05-25_%E5%A4%9A%E5%AA%92%E4%BD%93%E9%9F%B3%E8%A7%86%E9%A2%91%E6%A0%B8%E5%BF%83%E6%A6%82%E5%BF%B5/) 。

#### 带宽检测原理



#### DefaultBandwidthMeter

**ExoPlayer** 默认提供了 `DefaultBandwidthMeter` 实现带宽检测 。 该类同时实现了 `TransferListener<Object>` 接口 。

```
public interface TransferListener<S> {

  void onTransferStart(S source, DataSpec dataSpec);

  void onBytesTransferred(S source, int bytesTransferred);

  void onTransferEnd(S source);
}
```

该接口定义了转移数据的过程 。
