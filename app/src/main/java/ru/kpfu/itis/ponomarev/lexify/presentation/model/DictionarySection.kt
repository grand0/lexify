package ru.kpfu.itis.ponomarev.lexify.presentation.model

enum class DictionarySection(
    val tabName: String,
    val sectionName: String,
) {

    DEFINITIONS("define", "definitions"),
    ETYMOLOGIES("discover", "etymologies"),
    EXAMPLES("observe", "examples"),
    RELATED("relate", "related words"),
    AUDIO("hear", "audio"),
}