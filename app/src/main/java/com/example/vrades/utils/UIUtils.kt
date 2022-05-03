package com.example.vrades.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.vrades.utils.Constants.APP
import kotlin.coroutines.coroutineContext

object UIUtils {
    fun dismissKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        activity.currentFocus?.clearFocus()
    }
    fun toast(context: Context, text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}