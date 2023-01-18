package com.tae.baselibrary.ui

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tae.baselibrary.R

class AppDialogFragment : DialogFragment() {

    var message: String = ""
    private var dialogListener: DialogListener? = null
    var cancel = false
    var needTwoButton = false

    override fun onStart() {
        super.onStart()
        (dialog as AlertDialog?)?.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.parseColor("#5481FF"))
        (dialog as AlertDialog?)?.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.parseColor("#5481FF"))
    }
    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ): Dialog {
        if (needTwoButton) {
            return MaterialAlertDialogBuilder(requireContext())
                // Add customization options here
                .setMessage(message)
                .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                    dialogListener?.okEvent() ?: run { dismiss() }
                }
                .setNegativeButton(getString(android.R.string.cancel)) { _, _ ->
                    dialogListener?.cancel()?: run { dismiss() }
                }
                .setCancelable(cancel)
                .create()
        } else return MaterialAlertDialogBuilder(requireContext())
            // Add customization options here
            .setMessage(message)
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                dialogListener?.okEvent() ?: run { dismiss() }
            }
            .setCancelable(cancel)
            .create()
    }

    fun setEvent(listener: DialogListener) {
        dialogListener = listener
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (isAdded) return
        super.show(manager, tag)
    }

    interface DialogListener {
        fun okEvent()
        fun cancel()
    }
}