package ru.kpfu.itis.ponomarev.lexify.presentation.exception

import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySection
import javax.inject.Inject

class DictionarySectionExceptionHandler @Inject constructor() {

    fun handleException(e: Throwable, section: DictionarySection): DictionarySectionException {
        // special case for error 500 on "etymologies" endpoint because i hate this api so much.
        if (section == DictionarySection.ETYMOLOGIES && e is ServerResponseException) {
            return DictionarySectionNotFoundException(section)
        }

        val responseException = e as? ResponseException ?: return DictionarySectionException(section)
        return when (responseException.response.status) {
            HttpStatusCode.NotFound -> DictionarySectionNotFoundException(section)
            HttpStatusCode.TooManyRequests -> DictionarySectionRateLimitException(section)
            else -> DictionarySectionException(section, "${responseException.response.status.value} ${responseException.response.status.description}")
        }
    }
}