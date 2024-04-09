package ru.kpfu.itis.ponomarev.lexify.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClipboardUtil @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private var clipboardManager: ClipboardManager? = null

    fun copyText(text: CharSequence) {
        if (clipboardManager == null) {
            clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        }
        clipboardManager?.setPrimaryClip(ClipData.newPlainText(Keys.CLIPBOARD_TEXT_LABEL, text))
    }
}