package ru.kpfu.itis.ponomarev.lexify.presentation.exception

sealed class DiscoverScreenException : Throwable()

class DiscoverScreenWordOfTheDayException : DiscoverScreenException()
class DiscoverScreenRandomWordException : DiscoverScreenException()