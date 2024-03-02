package ru.kpfu.itis.ponomarev.lexify.util

import android.content.Context
import android.util.TypedValue
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.resources.Resource
import kotlinx.serialization.SerializationException
import java.io.IOException

fun Context.toPx(dp: Int): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
).toInt()
