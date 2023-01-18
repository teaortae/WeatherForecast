package com.tae.baselibrary.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


/**
 * hide keyboard @param[FragmentActivity] */
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * @brief base flag FLAG_ACTIVITY_CLEAR_TASK, FLAG_ACTIVITY_NEW_TASK.
 * @param[intentClass] to activity
 */
fun <T> FragmentActivity.intentTo(intentClass: Class<T>) {
    val intent = Intent(this, intentClass)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    overridePendingTransition(0, 0)
    startActivity(intent)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.sp: Int
    get() = ((dp * Resources.getSystem().displayMetrics.density) / Resources.getSystem().displayMetrics.scaledDensity).toInt()