package com.anton.movie_catalog_kotlin.utils

import android.app.Activity
import android.app.AlertDialog
import androidx.annotation.StringRes
import com.anton.movie_catalog_kotlin.R

fun Activity.showSuccessDialog(
    @StringRes titleRes: Int,
    @StringRes messageRes: Int,
    onSuccess: () -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle(getString(titleRes))
        .setMessage(getString(messageRes))
        .setCancelable(false)
        .setPositiveButton(getString(R.string.OK_notion)) { dialog, _ ->
            dialog.dismiss()
            onSuccess()
            finish()
        }
        .show()
}

fun Activity.showErrorDialog(@StringRes titleRes: Int, @StringRes messageRes: Int) {
    AlertDialog.Builder(this)
        .setTitle(getString(titleRes))
        .setMessage(getString(messageRes))
        .setCancelable(false)
        .setPositiveButton(getString(R.string.OK_notion), null)
        .show()
}

