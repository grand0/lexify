package ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper

import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordDefinitionDataModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionLabelModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordDefinitionModelMapper @Inject constructor() {

    fun mapDataModelToModel(data: WordDefinitionDataModel): WordDefinitionModel? {
        if (data.text == null) {
            return null
        }
        val doc = Jsoup.parse(data.text, "", Parser.xmlParser())
        val xrefs = doc.select("xref, internalXref").map { el ->
            el.text()
        }.toSet()
        return WordDefinitionModel(
            id = data.id ?: defaultId(data),
            partOfSpeech = data.partOfSpeech,
            attributionText = data.attributionText,
            attributionUrl = data.attributionUrl,
            text = doc.text(),
            labels = data.labels.map { WordDefinitionLabelModel(type = it.type, text = it.text) },
            word = data.word,
            xrefs = xrefs,
        )
    }

    private fun defaultId(data: WordDefinitionDataModel) = data.word + data.hashCode()
}