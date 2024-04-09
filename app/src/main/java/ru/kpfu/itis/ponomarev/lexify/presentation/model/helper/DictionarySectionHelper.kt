package ru.kpfu.itis.ponomarev.lexify.presentation.model.helper

import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionarySection
import ru.kpfu.itis.ponomarev.lexify.util.ResManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionarySectionHelper @Inject constructor(
    private val resManager: ResManager,
) {

    fun tabName(section: DictionarySection) = resManager.getString(section.tabNameId)
    fun sectionName(section: DictionarySection) = resManager.getString(section.sectionNameId)
    fun allTabNames() = DictionarySection.entries.map(::tabName)
}