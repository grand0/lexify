package ru.kpfu.itis.ponomarev.lexify.data.remote

import io.ktor.resources.Resource

@Resource("/words.json")
class WordsResources {

    @Resource("randomWord")
    class RandomWord(val parent: WordsResources = WordsResources(), val hasDictionaryDef: Boolean? = true, val minLength: Int? = 5, val maxLength: Int? = null)

    @Resource("randomWords")
    class RandomWords(val parent: WordsResources = WordsResources(), val hasDictionaryDef: Boolean? = true, val minLength: Int? = 5, val maxLength: Int? = null, val limit: Int? = 10)

    @Resource("wordOfTheDay")
    class WordOfTheDay(val parent: WordsResources = WordsResources())
}