package com.example.weatherforecast.ext

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.weatherforecast.R
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("InflateParams")
fun Context.progressDialog(): Dialog {
    val dialog = Dialog(this)
    val inflate = LayoutInflater.from(this).inflate(R.layout.progress_dialog, null)
    dialog.setContentView(inflate)
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawable(
        ColorDrawable(Color.TRANSPARENT)
    )
    return dialog
}

const val YYYY_MM_dd_hhmmss = "yyyy-MM-dd hh:mm:ss"
const val EEE_dd_MMM = "EEE dd MMM"

fun String.dateFormatChange(from: String, to: String): String {
    val sdfIn = SimpleDateFormat(from, Locale.ENGLISH)
    val sdfOut = SimpleDateFormat(to, Locale.ENGLISH)
    val time = sdfIn.parse(this)

    time?.let {
        return when (sdfOut.format(it)) {
            currentDate() -> "Today"
            1.addDays() -> "Tomorrow"
            else -> sdfOut.format(it)
        }
    } ?: return ""
}

fun currentDate(): String {
    val sdf = SimpleDateFormat(EEE_dd_MMM, Locale.ENGLISH)
    return sdf.format(Date())
}

fun Int.addDays(): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date()
    calendar.add(Calendar.DATE, this)
    val sdf = SimpleDateFormat(EEE_dd_MMM, Locale.ENGLISH)
    return sdf.format(calendar.time)
}