### MediaSource
定义如何加载播放 media , 从 `ExoPlayer.prepare` 开始注入。

### TrackSelector
从 media 中选择轨道用于内一个可用的 `Render` , 在播放器被创建时注入。

### LoadControl
控制 `MediaSource` buffers细节，包括需要缓冲多少 media ， 在播放器被创建时注入。




### 流媒体技术

DASH(Dynamic Adaptive Streaming over
HTTP),是一种自适应比特率流媒体技术。可以通过HTTP Web服务器传送流媒体
MPEG-DASH通过把内容分割成小的基于HTTP的文件段序列，来进行流媒体播放。各个文件段可以设置成不同的比特率进行编码，以满足不同的客户端的网络需求。比如，DASH客户端可以根据当前的网络状况，自动选择对应的最匹配的比特率文件段下载，进行回放，而不会引起停顿或重新缓冲。这样，DASH客户端可以无缝地适应不断变化的网络条件，并提供高品质的播放，而能够尽量减少播放的停顿或缓冲。
DASH与音频/视频编解码器无关。一般情况下，会有多个不同分辨率和比特率版本的多媒体文件可用，客户端可以根据网络状况，设备能力和用户偏好进行选择，从而实现自适应比特率流媒体和QoE。DASH还可以与任何底层应用层协议通用。

### 结构组成

基于HTTP的自适应流技术一般有两个组成部分
* 编码的A / V流本身（Media Presentation），包含流文件的URL地址清单以指定播放器应该播放哪一个流文件。是一组结构化的音频/视频内容集合，包含
    * Period（片段），Media Presentation指的是一段连续的视频片段，每个片段可以包含多个音频/视频内容集合（Adaptation Set）
    * Adaptation Set,每一个都包含多个Representation，
    * Representation，代表一个独立的流，由一系列媒体段组成。可以是640X480 @ 800Kbps的一个流，而另一个Representation 代表640X480 @ 500kbps的一个流。通过这样不同的Representation， DASH来达到自适应码率的目的
    * Segments（媒体段），可以是独立的文件，也可以是单一的媒体文件中指定的字节范围
* 标识文件（Media Presentation Description）,定义各个内容组件和可替代的流文件的位置。这样DASH的播放端就可以知道何时启动播放，为适应CPU和缓冲状态必要时如何进行切换。也可以因为用户的输入不同（如启用/禁用字幕或改变语言设置），相应改变Adaptation Set.

