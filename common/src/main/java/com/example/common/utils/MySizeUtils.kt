package com.example.common.utils

import android.content.Context

object MySizeUtils{
    //dp转px
     fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
