package ru.kpfu.itis.ponomarev.lexify.presentation.exception

import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySection
import javax.inject.Inject

class DictionarySectionExceptionHandler @Inject constructor() {

    fun handleException(e: Throwable, section: DictionarySection): DictionarySectionException {
        val clientRequestException = e as? ClientRequestException ?: return DictionarySectionException(section)
        return when (clientRequestException.response.status) {
            HttpStatusCode.NotFound -> DictionarySectionNotFoundException(section)
            HttpStatusCode.TooManyRequests -> DictionarySectionRateLimitException(section)
            else -> DictionarySectionException(section, "${clientRequestException.response.status.value} ${clientRequestException.response.status.description}")
        }
    }
}