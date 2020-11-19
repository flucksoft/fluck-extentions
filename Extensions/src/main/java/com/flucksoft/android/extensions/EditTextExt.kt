package com.flucksoft.android.extensions

import android.widget.EditText

fun EditText.placeCursorToEnd() {
    requestFocus()
    setSelection(text.length)
}
