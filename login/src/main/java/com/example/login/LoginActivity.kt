package com.example.login


import android.widget.Toast
import androidx.activity.viewModels
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.common.base.BaseActivity
import com.example.login.databinding.ActivityLoginBinding

class LoginActivity :BaseActivity<ActivityLoginBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_login
    }

    private val viewModel: LoginViewModel by viewModels { defaultViewModelProviderFactory }

    override fun initData() {
        super.initData()
        //对liveData对象进行观察
        viewModel.loginLiveData.observe(this,{
            result->
            val data=result.getOrNull()
            if(data!=null){
                LogUtils.d("获取成功${data}")
            }else{
                Toast.makeText(this,"无法成功获取账号信息",Toast.LENGTH_LONG).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    override fun initView() {
        super.initView()
        mBinding.apply {
         vm=viewModel
            //点击事件
            mtoolbarLogin.setNavigationOnClickListener {
                //返回键
                finish()
            }
            tvRegisterLogin.setOnClickListener {
                Toast.makeText(this@LoginActivity,"当前课程项目为实现注册账号功能",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun initConfig() {
        super.initConfig()
    }
}