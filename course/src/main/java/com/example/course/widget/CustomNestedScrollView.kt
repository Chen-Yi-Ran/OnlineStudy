package com.example.course.widget

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.widget.NestedScrollView

//扩展播放界面的滑动ScrollView
class CustomNestedScrollView : NestedScrollView {
    constructor(@NonNull context: Context?) : super(context!!) {}
    constructor(@NonNull context: Context?, @Nullable attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {

    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener!!.scrollChangedListener(t)
        }
    }

    //滑动屏幕时y轴顶部的距离
    interface OnScrollChangedListener {
        fun scrollChangedListener(y: Int)
    }

    private var mOnScrollChangedListener: OnScrollChangedListener? = null
    fun setOnScrollChangedListener(onScrollChangedListener: OnScrollChangedListener?) {
        mOnScrollChangedListener = onScrollChangedListener
    }
}