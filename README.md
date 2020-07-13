# AnyCast
Android视频播放类app，MVVM+rxJava+Retrofit+okHttp+Glide

# 项目目标
视频点播
直播互动

# 依赖
    - androidx
    - rxjava
    - materialsearchview
    - flycoTabLayout
    - glide
    - retrofit
    - okhttp
    - steho
    - jsoup
    - dom4j

# 模块介绍
	验证架构设计的实验性项目，采用MVVM模式，使用AndroidX + RxJava2 + Retrofit2 + OKhttp + Glide + GSON；
	视频点播系统 - 通过restFUL API接入数据，GSON解析，目前已接入阿里云点播平台、bilibili，计划接入youtube、搜狐视频等后台获取点播数据；
	视频播放 - 独立的视频播放模块，抽象的视频播放接口层，可接入多个主流播放框架，目前已接入ExoPlayer、IjkPlayer；计划对接入的player进行优化；后期规划实现基于FFMpeg的自己的播放模块；
	动态布局切换 – 对container layout进行抽象，实现根据配置文件改变页面布局，规划中；
	视频直播 - 规划中；
	用户信息管理 – 规划中；
	更多功能 – 规划中…
