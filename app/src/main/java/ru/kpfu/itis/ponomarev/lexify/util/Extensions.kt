package ru.kpfu.itis.ponomarev.lexify.util

import android.content.Context
import android.util.TypedValue
import kotlinx.coroutines.flow.MutableStateFlow

fun Context.dpToPx(dp: Int): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
).toInt()

fun Context.spToPx(sp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    sp.toFloat(),
    resources.displayMetrics
)

fun String?.indexesOf(substr: String?, ignoreCase: Boolean = true): List<Int> {
    if (substr == null) return emptyList()
    return this?.let {
        val regex = if (ignoreCase) Regex("\\b$substr\\b", RegexOption.IGNORE_CASE) else Regex(substr)
        regex.findAll(this).map { it.range.first }.toList()
    } ?: emptyList()
}

operator fun <T> MutableStateFlow<List<T>>.set(index: Int, element: T) {
    value = value.take(index) + element + value.takeLast(value.size - index - 1)
}
