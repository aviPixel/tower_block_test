package com.androidtest.management

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.androidtest.myapp.R
import qiu.niorgai.StatusBarCompat

class MainConfig {

    class COLOR {
        companion object {
            const val BLUE = "BLUE"
            const val PINK = "PINK"
            const val PURPLE = "PURPLE"
        }
    }

}

fun Any.toast(context: Context?) {
    if (context == null) {
        return
    }
    Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT).show()
}

fun Activity.setStatusBarWhite() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//23
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }
}

fun Activity.setStatusBarTransparent() {
    StatusBarCompat.translucentStatusBar(this, true)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}