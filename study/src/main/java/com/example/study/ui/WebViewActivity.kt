package com.example.study.ui


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import com.blankj.utilcode.util.LogUtils
import com.example.common.base.BaseActivity
import com.example.study.R
import com.example.study.databinding.ActivityWebViewBinding
import com.just.agentweb.AgentWeb

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {

    private lateinit var agentWeb: AgentWeb
    private lateinit var shareTitle: String
    private lateinit var shareUrl: String
    private var shareId: Int = 0
    override fun getLayoutRes(): Int {
        return R.layout.activity_web_view
    }

    override fun initView() {
        super.initView()

        mBinding.apply {

            //设置当前的ActionBar
            toolbar.run {
                title = getString(R.string.loading)
                setSupportActionBar(this)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }


            intent.extras?.let {
                shareId = it.getInt("CONTENT_ID_KEY", 0)
                shareUrl = it.getString("CONTENT_URL_KEY").toString()
                shareTitle = it.getString("CONTENT_TITLE_KEY").toString()
                agentWeb=shareUrl.getAgentWeb(
                    this@WebViewActivity,
                    webContent,
                    LinearLayout.LayoutParams(-1, -1)
                )
            }
            toolbar.title = shareTitle
        }


    }

    //引入菜单项
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        LogUtils.d("进来选择页面")
        when(item.itemId){
            //分享
            R.id.menuShare ->{
               Intent().run {
                   LogUtils.d("点击了分享")
                   action=Intent.ACTION_SEND
                   putExtra(Intent.EXTRA_TEXT,
                       getString(
                           R.string.share_article_url,
                           getString(R.string.app_name),
                           shareTitle,
                           shareUrl
                       )
                   )
                   type = "text/plain"
                   startActivity(Intent.createChooser(this, getString(R.string.share_title)))
               }
                return true
            }
            //收藏
            R.id.menuLike ->{
                //TODO:这里应该加个登录验证,判断当前状态是否登录，如果没有登录则跳转到登录页

                return true
            }
            //用浏览器打开
            R.id.menuBrowser->{
                Intent().run {
                    action = "android.intent.action.VIEW"
                    data = Uri.parse(shareUrl)
                    startActivity(this)
                }
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }


        return true
    }

    /**
     * getAgentWeb
     */
    private fun String.getAgentWeb(
        activity: Activity, webContent: ViewGroup,
        layoutParams: ViewGroup.LayoutParams,
    ) = AgentWeb.with(activity)//传入Activity or Fragment
        .setAgentWebParent(webContent, layoutParams)//传入AgentWeb 的父控件
        .useDefaultIndicator()// 使用默认进度条
        .createAgentWeb()//
        .ready()
        .go(this)!!


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        LogUtils.d("点击了onKeyDown")
        return if (agentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }
}