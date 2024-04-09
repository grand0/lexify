package ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    var screenBitmap: Bitmap? = null
        private set

    fun nightModeDidChange() = screenBitmap != null

    fun clearNightModeChangedStatus() {
        screenBitmap = null
    }

    fun changeNightMode(viewToCapture: View) {
        screenBitmap = Bitmap.createBitmap(
            viewToCapture.measuredWidth,
            viewToCapture.measuredHeight,
            Bitmap.Config.ARGB_8888,
        ).also {
            val canvas = Canvas(it)
            viewToCapture.draw(canvas)
        }
    }
}