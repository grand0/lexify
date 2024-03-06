package ru.kpfu.itis.ponomarev.lexify.presentation.exception

import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySection

class DictionarySectionNotFoundException(
    override val section: DictionarySection,
) : DictionarySectionException(section = section)
