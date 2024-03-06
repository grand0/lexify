package ru.kpfu.itis.ponomarev.lexify.domain.model.response

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordExampleModel

data class WordExamplesResponse(
    val examples: List<WordExampleModel>,
)
