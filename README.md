# 基于Kotlin+MVVM+JetPack+协程的在线教育APP
- 项目技术选型与架构:
- Kotlin+MVVM
- Jetpack( ViewModel、LiveData、 Lifecycle、DataBinding、Room、Navigation、Paging)
- Okhttp+Retrofit+Kotlin协程进行网络请求
- Glide进行图片加载与缓存、Json解析框架Gson
- AgentWeb加载h5页面
- 使用阿里云视频点播SDK进行课程视频播放
- 第三方库辅助工具：
- ARouter：组件间、模块间的界面跳转
- banner：首页轮播图
## 模块说明
|app|common|home|login|mine|service|study|course|
|--|--|--|--|--|--|--|--|
|入口模块|公共模块|首页模块|登录注册模块|用户信息模块|网络请求封装模块|学习中心模块|教程学习模块|


项目中使用的API来自WanAndroid网站免费API
