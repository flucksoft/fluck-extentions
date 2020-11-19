package com.flucksoft.android.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


fun Activity.hideKeyboard() = hideKeyboard(
    currentFocus
        ?: View(this)
)

fun Fragment.hideKeyboard() = view?.let { activity?.hideKeyboard(it) }
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.activity(): FragmentActivity =
    if (this is FragmentActivity) this else (this as ContextWrapper).baseContext.activity()

fun Context.colorOf(@ColorRes colorInt: Int) = ContextCompat.getColor(this, colorInt)
fun Context.chooseColor(
    statement: Boolean, @ColorRes firstOption: Int, @ColorRes secondOption: Int
) = if (statement) colorOf(firstOption) else colorOf(secondOption)

fun Context.drawableOf(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)
fun Context.chooseDrawable(
    statement: Boolean, @DrawableRes firstOption: Int, @DrawableRes secondOption: Int
) = if (statement) drawableOf(firstOption) else drawableOf(secondOption)


fun Activity.openUrl(url: String) {
    val browserIntent = Intent(ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}

fun Activity.getPathFromDeepLink(): String? {
    val data = intent?.data
    val action = intent?.action
    return if (action != null && data != null && action == ACTION_VIEW) data.path
    else null
}

fun Context?.isInParent(view: View?, targetId: Int): Boolean {
    if (this == null) return false
    if (view?.id == targetId) return true
    val parent = (if (view?.parent != null && view.parent is View) view.parent as View else null)
        ?: return false
    return isInParent(parent, targetId)
}

fun Activity.navigateToParent(bundle: Bundle) {
    val upIntent: Intent? = NavUtils.getParentActivityIntent(this)
    upIntent?.putExtras(bundle)
    when {
        upIntent == null -> throw IllegalStateException("No Parent Activity Intent")
        NavUtils.shouldUpRecreateTask(this, upIntent) -> {
            TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities()
        }
        else -> {
            NavUtils.navigateUpTo(this, upIntent)
        }
    }
}
