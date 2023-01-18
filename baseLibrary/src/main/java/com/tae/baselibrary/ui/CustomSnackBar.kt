package com.tae.baselibrary.ui

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.tae.baselibrary.databinding.CustomviewSnackBarBinding
import com.tae.baselibrary.ext.dp

object CustomSnackBar {
    var binding: CustomviewSnackBarBinding? = null
    fun showToast(text: String, activity: Activity) {
        binding = CustomviewSnackBarBinding.inflate(LayoutInflater.from(activity))
        binding?.root?.let {
            val snackBar = Snackbar.make(
                activity.window.decorView.findViewById(android.R.id.content),
                text,
                Snackbar.LENGTH_SHORT
            )
            val layout = (snackBar.view as Snackbar.SnackbarLayout)

            val cl = CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.MATCH_PARENT
            )
            layout.removeAllViews()
            binding?.text = text
            cl.gravity = Gravity.CENTER or Gravity.BOTTOM
            cl.setMargins(0, 0, 0, 112.dp)
            layout.addView(it, cl)
            snackBar.view.setBackgroundColor(Color.TRANSPARENT)
            snackBar.show()
        }
    }
}