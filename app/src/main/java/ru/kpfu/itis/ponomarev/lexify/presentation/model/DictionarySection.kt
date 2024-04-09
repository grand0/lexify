package ru.kpfu.itis.ponomarev.lexify.presentation.model

import androidx.annotation.StringRes
import ru.kpfu.itis.ponomarev.lexify.R

enum class DictionarySection(
    @StringRes val tabNameId: Int,
    @StringRes val sectionNameId: Int,
) {

    DEFINITIONS(R.string.definitions_tab, R.string.definitions_section),
    ETYMOLOGIES(R.string.etymologies_tab, R.string.etymologies_section),
    EXAMPLES(R.string.examples_tab, R.string.examples_section),
    RELATED(R.string.related_tab, R.string.related_section),
    AUDIO(R.string.audio_tab, R.string.audio_section);
}