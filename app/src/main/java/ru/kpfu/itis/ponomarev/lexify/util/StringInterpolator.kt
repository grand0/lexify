package ru.kpfu.itis.ponomarev.lexify.util

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class StringInterpolator(val template: String, val substitutions: List<String>, val useBlankSpaceChar: Boolean = false) {

    fun interpolate(pos: Double): String {
        val index = pos.roundToInt()
        val frac = 1 - abs(pos - index) / 0.5
        var sub = substitutions[index % substitutions.size]
        sub = sub.substring(0, (sub.length * frac).toInt())
        if (sub.isEmpty() && useBlankSpaceChar) sub = " "
        return String.format(template, sub)
    }
}