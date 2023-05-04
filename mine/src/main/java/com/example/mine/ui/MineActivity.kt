package com.example.mine.ui


import android.graphics.Color
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.example.common.base.BaseActivity
import com.example.mine.R

import com.example.mine.databinding.ActivityMineBinding
import com.example.mine.widget.ItemSettingBean

class MineActivity : BaseActivity<ActivityMineBinding>() {
    override fun getLayoutRes()= R.layout.activity_mine


    override fun initView() {
        super.initView()
        mBinding.apply {

            val ib=ItemSettingBean(title="学习卡",iconRes = R.drawable.ic_shopping)
            val obBean=ObservableField(ib)
            bean=obBean

            ib.titleColor=R.color.red
            ib.arrowColor= Color.RED

//            val bean=ItemSettingBean(title="学习卡")
//            isvCard.setInfo(bean)
//            bean.title="你的学习卡"
            ib.iconRes="https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png"



            isvCard.onClickArrow{
                ToastUtils.showShort("点击了Arrow箭头")
            }
            //如果不进行拦截，ViewGroup中的子view点击事件是不生效的
            isvCard.setOnClickListener {
                ToastUtils.showShort("点击了整个Item")
            }
        }
    }
}