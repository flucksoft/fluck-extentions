package com.flucksoft.android.extensions

fun Int?.orZero(): Int {
    return this
            ?: 0
}
