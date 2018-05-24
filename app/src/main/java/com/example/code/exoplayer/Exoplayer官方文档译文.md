## 开发者指南

视频播放和音乐播是安卓设备流行的用户行为 。 Android 框架提供 `MediaPlayer` 以最少的编码来解决播放媒体的问题 ， 也提供了低版本媒体 API ， 比如 `MediaCodec` , `AudioTrack` 和 `MediaDrm` 用于自主定制媒体播放的方案。

`ExoPlayer` 是一个构建于 Android 低版本 API 之上的开源应用项目 。 本指南描述了：
* 使用 `ExoPlayer` 库，并提供了具体场景的 [main demo app](https://github.com/google/ExoPlayer/tree/release-v2/demos/main)
* 描述 `ExoPlayer` 使用的利弊
* 使用 `ExoPlayer` 播放 `DASH` , `SmoothStreaming` 和 `HLS` 自适应流 , 以及更多的格式视频 ， 如 `MP4` ,`M4A` ,`FMP4` ,`WebM` ,`MKV` ,`MP3` ,`WAV` ,`Ogg` ,`MPEG-TS` ,`MPEG-PS` ,`FLV ` 和 `ADTS (ACC)` 等等。
* 描述 `ExoPlayer` 包含的事件、消息、定制及 DRM 的支持。

### 优缺点

相对于安卓内置的 `MediaPlayer` , `ExoPlayer` 有以下优点：
* 支持 `DASH` (动态自适应流媒体) 和 `SmoothStreaming` 格式 ， 具体支持的格式可查看 [Supported formats](https://github.com/google/ExoPlayer/tree/release-v2/demos/mainhttps://google.github.io/ExoPlayer%20/supported-formats.html)
* 支持 `HLS` 高级特性，比如处理 `#EXT-X-DISCONTINUIT` 标签
* 无缝地合并、连接和循环媒体
* 更新播放器版本，`ExoPlayer` 作为依赖库用于构建应用，你可以轻易地升级版本
* 良好的设备兼容，在不同的版本设备上很少产生问题
* 支持 4.4 及以上使用常见加密方式
* 支持开发者自定义扩展播放器来适应自己的项目 ，为此 `ExoPlayer` 预留了很多可替代的组件
* 支持使用官方扩展组件来整合其他开源库 ， 比如 [IMA extension](https://github.com/google/ExoPlayer/tree/release-v2/extensions/ima) 使得开发者更简便地接入 [Interactive Media Ads SDK](https://developers.google.com/interactive-media-ads) 来实现盈利

值得留意的是，`ExoPlayer` 也存在部分缺点：
* 对于仅实现音频播放的设备 ， `ExoPlayer` 比其他媒体播放器可能会消耗更多的电量 。 详情可参考 [Battery consumption page](https://google.github.io/ExoPlayer/battery-consumption.html)

### 库概述

库的核心是 `ExoPlayer` 接口 。 `ExoPlayer` 暴露了传统高级媒体播放器的功能 ， 如缓冲、播放、暂停及进度控制。 预先设想一些媒体实现了如何播放、如何存储及在哪存储，如何渲染等。而不是实现直接实现加载和渲染 media ，这些都是 `ExoPlayer` 在播放器被创建或准备回播时，注入组件委托者来实现 。 这些组件分别是：
* `MediaSource` ,  定义了如何播放、加载 media ， 以及在哪里加载可读的 media 。`MediaSource` 对象会在回播开始时调用 `ExoPlaer.prepare` 后被注入。
* `Render` ， 能渲染 `MediaSource` 中各个独立组件的渲染器 。 `Render` 对象会在播放器创建时注入
* `TrackSelector` ， 从 `MediaSource` 中选择轨道以供 `Render` 渲染器消费 。 `TrackSelector` 对象会在播放器创建时注入
* `LoadControl` , 控制 `MediaSource` 缓冲更多的 media 及 控制缓冲量 。 `LoadControl` 对象会在播放器创建时注入

库提供了一些默认实现组件以供常见用例使用，后续将有更详细的描述。一个 `ExoPlayer` 对象能使用多个默认组件，但也可以使用一些非标准行为的自定义组件 。 比如通过注入自定义 `LoadControl` 对象来改变播放器的缓冲策略 ， 或者注入自定义 `Render` 对象来使用 Android 不支持的视频编解码器。

注入实现播放器功能组件的理念贯穿整个库 。 上述默认实现的组件都是委托工作到进一步注入的组件中，这使得许多子组件可以通过自定义实现来替换其功能。例如， 默认的 `MediaSource` 实现中，其构造器需要注入一个或多个 `DataSource` 工厂 。 通过提供自定义工厂能从非标注输入源中或不同的网络堆栈中加载数据。

### 开始

对于简单用例，开发者可通过以下步骤使用 `ExoPlayer`:
* 添加 ExoPlayer 依赖到项目
* 创建一个 `SimpleExoPlayer` 对象
* 绑定播放器到界面 (用于视频输入及用户输入)
* 把 `MediaSource` 给播放器加载准备播放
* 播放工作完成后释放播放器

下面将详细描述这些步骤 。 对于完成实例 ， 请参考[main demo app](https://github.com/google/ExoPlayer/tree/release-v2/demos/main) 中的 `PlayerActivity` 。

#### 添加 ExoPlayer 作为项目依赖

确保项目文件 `build.gradle` 根节点中包含 `JCenter` 和 `Google` 仓库

```
repositories {
    jcenter()
    google()
}
```

接着在 app 模块中的 `build.gredle` 添加 ExoPlayer 库依赖：

```
implementation 'com.google.android.exoplayer:exoplayer:2.X.X'
```

`2.X.X` 是你所需要的库版本 。 另外 ， 你也可以只依赖项目所需要的库 。 比如 ， 如果项目只需要播放 `DASH` 格式的内容，将会添加 `Code` , `DASH` 和 `UI` 库 ：

```
implementation 'com.google.android.exoplayer:exoplayer-core:2.X.X'
implementation 'com.google.android.exoplayer:exoplayer-dash:2.X.X'
implementation 'com.google.android.exoplayer:exoplayer-ui:2.X.X'
```

下面列出的是可用的模块 。 添加一个完整的 ExoPlayer 库相当于添加下面所有的独立库 :
* `exoplayer-core` : 核心功能 (必须)
* `exoplayer-dash` : 用于支持 DASH 格式的内容
* `exoplayer-hls` : 用于支持 HLS 格式的内容
* `exoplayer-smoothstreaming` : 用于支持 SmoothStreaming 格式的内容
* `exoplayer-ui` : ExoPlayer 能使用的 UI 组件和资源

除了库模块外 ， ExoPlayer 有多个依赖外部库实现的扩展模块用于提供额外的功能 。 这些内容不再本指南的范围内，具体细节可以查看 [extensions directory](https://github.com/google/ExoPlayer/tree/release-v2/extensions/) 及 README 文件 。

### 创建播放器

你可以使用 `ExoPlayerFactory` 来创建一个 `ExoPlayer` 对象 。 工厂类提供了一系列方法来创建可定制化的 `ExoPlayer` 对象 。 对于绝大部分用例 , `ExoPlayerFactory.newSimpleInstance` 会被调用 。 这些方法会返回 `SimpleExoPlayer` 对象 ， 该继承于 `ExoPlayer` 并添加了额外的高级播放器高级功能。 下面的代码是展示如何创建一个 `SimpleExoPlayer` 对象 。

```
// 1. Create a default TrackSelector
Handler mainHandler = new Handler();
BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
TrackSelection.Factory videoTrackSelectionFactory =
    new AdaptiveTrackSelection.Factory(bandwidthMeter);
TrackSelector trackSelector =
    new DefaultTrackSelector(videoTrackSelectionFactory);

// 2. Create the player
SimpleExoPlayer player =
    ExoPlayerFactory.newSimpleInstance(context, trackSelector);
```

### 绑定一个播放器到视图
ExoPlayer库提供了一个 `PlayerView` 类 , 封装了 `PlayerControlView` 控制层视图和 `Surface` 视频呈现层 。 `PlayerView` 能包含写入程序 layout 布局中 。 下面代码是展示绑定一个播放器到视图上：

```
// Bind the player to the view.
playerView.setPlayer(player);
```

如果你需要进一步控制播放器的控制层及视频呈现层 ， 可以直接调用 `SimpleExoPlayer` 的 `setVideoSurfaceView`  , `setVideoTextureView` , `setVideoSurfaceHolder ` 或 `setVideoSurface ` 来分别设置 `SurfaceView` , `TextureView` , `SurfaceHolder` 或者 `Surface` 。 可以把 `PlayerControlView` 当做一个独立的组件使用 ， 或者实现自己的播放控制来直接与用户进行互动 。 在播放的时候， `setTextOutput` 和 `setId3OutPut` 能够被用于接收标题和 ID3 元数据 。

### 准备播放器

在 ExoPlayer 中每一块媒体都由 `MediaSource` 提供。 为了播放每一块媒体 ， 你必须先创建一个对应的 `MediaSource` 对象并传递给 `ExoPlayer.prepare` 。 ExoPlayer 库提供了一系列 `MediaSource` 实现类 ， 如 `DASH (DashMediaSource)` , `SmoothStreaming (SsMediaSource)` , `HLS (HlsMediaSource)` 和 常见的媒体文件 (ExtractorMediaSource) 。 这些实现的细节会在后续的指南中描述 。 下面的代码展示了播放器利用 `MediaSource` 准备播放一个 MP4 文件 :

```
// Measures bandwidth during playback. Can be null if not required.
DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

// Produces DataSource instances through which media data is loaded.
DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
    Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter);

// This is the MediaSource representing the media to be played.
MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
    .createMediaSource(mp4VideoUri);

// Prepare the player with the source.
player.prepare(videoSource);
```

### 控制播放器

一旦播放器准备就绪 ， 就可以通过调用方法来控制播放行为 。 比如 `setPlayWhenReady` 可控制是否暂停/播放 ； `seekTo` 可在媒体进度中寻求播放点 ； `setRepeatMode` 可可控制媒体是否循环和如何循环 ； `setPlaybackParameters` 可调整播放时的速度和音调 。

如果播放器绑定了 `PlayerView` 或者 `PlayerControlView` ，则用户与视图的互动会调用播放器的对应方法。

### 监听播放器事件

状态的改变或者播放异常这些事件会被上报到已注册的 `Player.EventListener` 对象 。
