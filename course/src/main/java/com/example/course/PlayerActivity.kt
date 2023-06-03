package com.example.course

import android.content.res.Configuration
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.aliyun.player.IPlayer
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig
import com.aliyun.player.alivcplayerexpand.theme.Theme
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView
import com.aliyun.player.aliyunplayerbase.util.ScreenUtils
import com.aliyun.player.nativeclass.PlayerConfig
import com.blankj.utilcode.util.LogUtils
import com.example.common.base.BaseActivity
import com.example.common.utils.MySizeUtils.dip2px
import com.example.course.databinding.PlayerActivityBinding
import com.example.course.widget.BigView
import com.example.course.widget.CustomNestedScrollView
import java.io.IOException
import java.io.InputStream


//课程播放界面
class PlayerActivity : BaseActivity<PlayerActivityBinding>() {

    private val viewModel: CourseViewModel by viewModels { defaultViewModelProviderFactory }

    override fun getLayoutRes(): Int {
        return R.layout.player_activity
    }

    private var videoPlayHeight = 0
    private var smallVideoHeight = 0
    private var mFrame_layout2: FrameLayout? = null
    private var mFrameLayout: LinearLayout? = null
    private var layoutParams: LinearLayout.LayoutParams? = null
    private var layoutParamsSmallVideo: FrameLayout.LayoutParams? = null
    private var isSmallVideoDisplay = false
    private lateinit var mLinearLayout: LinearLayout
    private var mView: View? = null
    private lateinit var bigView: BigView

    private lateinit var videoView: AliyunVodPlayerView
    private lateinit var customNestedScrollView: CustomNestedScrollView
    override fun initView() {
        layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, videoPlayHeight)

        mBinding.apply {
            vm = viewModel
            this@PlayerActivity.customNestedScrollView = nestedscrollview
            this@PlayerActivity.mLinearLayout = linearLayout
            this@PlayerActivity.mFrameLayout = frameLayout
            this@PlayerActivity.mFrame_layout2 = frameLayout2
//            this@PlayerActivity.bigView=bigView
        }
        videoView = AliyunVodPlayerView(this)
        mView = View(this)
        mFrameLayout?.addView(videoView, 0, layoutParams)
        videoPlayHeight = dip2px(this, 200F)
        smallVideoHeight = dip2px(this, 150F)
    }

    override fun initData() {

        initConfig()
        initEvent()
        viewModel.playerVideo(videoView)
//        var `is`: InputStream? = null
//        try {
//            `is` = resources.assets.open("bigpicture.jpg")
//            bigView.setImage(`is`)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    }


    fun initConfig() {

        //镜像模式为默认
        //镜像模式为默认
        GlobalPlayerConfig.mMirrorMode = IPlayer.MirrorMode.MIRROR_MODE_NONE
        //自适应码
        //自适应码
        GlobalPlayerConfig.PlayConfig.mAutoSwitchOpen = false
        //seek模式
        //seek模式
        GlobalPlayerConfig.PlayConfig.mEnableAccurateSeekModule = false
        //是否允许后台播放
        //是否允许后台播放
        GlobalPlayerConfig.PlayConfig.mEnablePlayBackground = false
        //默认播放方式
        //默认播放方式
        GlobalPlayerConfig.mCurrentPlayType = GlobalPlayerConfig.PLAYTYPE.DEFAULT
        //起播码率3000
        //起播码率3000
        GlobalPlayerConfig.mCurrentMutiRate = GlobalPlayerConfig.MUTIRATE.RATE_3000
        //旋转0
        //旋转0
        GlobalPlayerConfig.mRotateMode = IPlayer.RotateMode.ROTATE_0
        videoView.setEnableHardwareDecoder(GlobalPlayerConfig.mEnableHardDecodeType)
        videoView.setRenderMirrorMode(GlobalPlayerConfig.mMirrorMode)
        videoView.setRenderRotate(GlobalPlayerConfig.mRotateMode)
        videoView.setDefaultBandWidth(GlobalPlayerConfig.mCurrentMutiRate.value)
        //播放配置设置
        //播放配置设置
        val playerConfig: PlayerConfig = videoView.getPlayerConfig()
        playerConfig.mStartBufferDuration = GlobalPlayerConfig.PlayConfig.mStartBufferDuration
        playerConfig.mHighBufferDuration = GlobalPlayerConfig.PlayConfig.mHighBufferDuration
        playerConfig.mMaxBufferDuration = GlobalPlayerConfig.PlayConfig.mMaxBufferDuration
        playerConfig.mMaxDelayTime = GlobalPlayerConfig.PlayConfig.mMaxDelayTime
        playerConfig.mNetworkTimeout = GlobalPlayerConfig.PlayConfig.mNetworkTimeout
        playerConfig.mMaxProbeSize = GlobalPlayerConfig.PlayConfig.mMaxProbeSize
        playerConfig.mReferrer = GlobalPlayerConfig.PlayConfig.mReferrer
        playerConfig.mHttpProxy = GlobalPlayerConfig.PlayConfig.mHttpProxy
        playerConfig.mNetworkRetryCount = GlobalPlayerConfig.PlayConfig.mNetworkRetryCount
        playerConfig.mEnableSEI = GlobalPlayerConfig.PlayConfig.mEnableSei
        playerConfig.mClearFrameWhenStop = GlobalPlayerConfig.PlayConfig.mEnableClearWhenStop
        videoView.setPlayerConfig(playerConfig)
        //保持屏幕高亮
        //保持屏幕高亮
        videoView.setKeepScreenOn(true)
        //设置更新UI播放器的主题
        //设置更新UI播放器的主题
        videoView.setTheme(Theme.Blue)
        //是否循环播放
        //是否循环播放
        videoView.setCirclePlay(true)
        //是否自动播放
        //是否自动播放
        videoView.setAutoPlay(false)
        videoView.enableNativeLog()

        videoView.startNetWatch()
    }


    fun initEvent() {
        customNestedScrollView.setOnScrollChangedListener(object :
            CustomNestedScrollView.OnScrollChangedListener {
            override fun scrollChangedListener(y: Int) {
                LogUtils.d(this@PlayerActivity, "y" + y)
                LogUtils.d(this@PlayerActivity, "videoPlayHeight" + videoPlayHeight)
                if (y >= videoPlayHeight) {//顶部播放器不可见了
                    LogUtils.d(this@PlayerActivity, "(y >= videoPlayHeight经来了")
                    if (!isSmallVideoDisplay) {
                        LogUtils.d(this@PlayerActivity, "!isSmallVideoDisplay")
                        isSmallVideoDisplay = true;
                        //不设置控制栏
                        videoView.setControlBarCanShow(false);
                        //更新UI
                        videoView.post(object : Runnable {
                            override fun run() {
                                mFrameLayout?.removeView(videoView);
                                //为了不让移除布局后总布局高度不变，动态增加一个同样高度的view
                                mLinearLayout.addView(
                                    mView,
                                    -1,
                                    LinearLayout.LayoutParams(0, videoPlayHeight)
                                );
                                //小屏幕显示区域
                                layoutParamsSmallVideo =
                                    FrameLayout.LayoutParams(smallVideoHeight, smallVideoHeight);
                                layoutParamsSmallVideo?.gravity =
                                    Gravity.CENTER_VERTICAL or Gravity.RIGHT
                                layoutParamsSmallVideo?.rightMargin = 20;
                                mFrame_layout2?.addView(videoView, layoutParamsSmallVideo)
                            }

                        })
                    } else {
                        if (isSmallVideoDisplay) {
                            LogUtils.d(this@PlayerActivity, "isSmallVideoDisplay")
                            isSmallVideoDisplay = false;
                            videoView.setControlBarCanShow(true);
                            mFrameLayout?.post(object : Runnable {
                                override fun run() {
                                    //去除小屏幕
                                    mFrame_layout2?.removeView(videoView);
                                    //除去动态添加高度的view
                                    mLinearLayout.removeView(mView);
                                    //增加最顶部上面的播放界面
                                    mFrameLayout?.addView(videoView, 0, layoutParams);
                                }


                            })
                        }
                    }
                }
            }
        })
    }


    //变换横竖屏屏幕
    private fun updatePlayerViewMode() {
        if (videoView != null) {
            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                this.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE)

                //设置view的布局，宽高之类
                val aliVcVideoViewLayoutParams = videoView
                    .getLayoutParams() as LinearLayout.LayoutParams
                aliVcVideoViewLayoutParams.height =
                    ((ScreenUtils.getWidth(this) * 9.0f / 16).toInt())
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
//                if (!isStrangePhone()) {
//                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//                }
                //设置view的布局，宽高
                val aliVcVideoViewLayoutParams = videoView
                    .getLayoutParams() as LinearLayout.LayoutParams
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updatePlayerViewMode()
        if (videoView != null) {
            videoView.setAutoPlay(false);
            videoView.onResume()
        }
    }

    override fun onStop() {
        super.onStop()
        if (videoView != null) {
            videoView.setAutoPlay(false);
            videoView.onStop()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updatePlayerViewMode()
    }
}






