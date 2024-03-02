package ru.kpfu.itis.ponomarev.lexify.data.remote

import io.ktor.resources.Resource

@Resource("/word.json")
class WordResources {

    @Resource("{word}")
    class Word(val parent: WordResources = WordResources(), val word: String) {

        @Resource("definitions")
        class Definitions(val parent: Word, val limit: Int = 100)
    }
}