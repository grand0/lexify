package ru.kpfu.itis.ponomarev.lexify.util

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResManager @Inject constructor(@ApplicationContext private val context: Context) {

    fun getString(@StringRes resId: Int) = context.getString(resId)

    fun getString(@StringRes resId: Int, vararg args: Any?) = context.getString(resId, args)
}