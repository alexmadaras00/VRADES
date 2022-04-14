package com.example.vrades.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

object UIUtils {
    fun dismissKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        activity.currentFocus?.clearFocus()
    }
}