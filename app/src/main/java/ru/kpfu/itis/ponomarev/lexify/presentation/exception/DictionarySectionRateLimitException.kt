package ru.kpfu.itis.ponomarev.lexify.presentation.exception

import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySection

class DictionarySectionRateLimitException(
    override val section: DictionarySection,
) : DictionarySectionException(section = section)
