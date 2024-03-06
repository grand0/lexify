package ru.kpfu.itis.ponomarev.lexify.presentation.exception

import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySection

open class DictionarySectionException(
    open val section: DictionarySection,
    override val message: String? = null,
) : Throwable()
