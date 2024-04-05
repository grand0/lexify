package ru.kpfu.itis.ponomarev.lexify.domain.sorting

import ru.kpfu.itis.ponomarev.lexify.R

enum class LovedWordsSorting(
    val displayNameId: Int,
) {
    ALPHABETICALLY(R.string.alphabetically),
    RECENT(R.string.recent);
}