package com.flucksoft.android.extensions

import android.content.res.Resources

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
val Float.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
val Float.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
