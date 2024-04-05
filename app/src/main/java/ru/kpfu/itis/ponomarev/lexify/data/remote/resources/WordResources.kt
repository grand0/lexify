package ru.kpfu.itis.ponomarev.lexify.data.remote.resources

import io.ktor.resources.Resource

@Resource("/word.json")
class WordResources {

    @Resource("{word}")
    class Word(val parent: WordResources = WordResources(), val word: String) {

        @Resource("definitions")
        class Definitions(val parent: Word, val limit: Int = 100)

        @Resource("etymologies")
        class Etymologies(val parent: Word)

        @Resource("examples")
        class Examples(val parent: Word, val limit: Int = 50, val skip: Int? = null)

        @Resource("relatedWords")
        class RelatedWords(val parent: Word)

        @Resource("audio")
        class Audio(val parent: Word, val limit: Int = 50)
    }
}