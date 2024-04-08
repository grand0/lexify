package ru.kpfu.itis.ponomarev.lexify.presentation.model

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.DiscoverFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.LocalSpaceFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.SearchFragment

enum class HomePages(
    @StringRes val actionMessage: Int,
    @DrawableRes val iconDrawable: Int,
    val fragmentProvider: () -> Fragment,
) {
    DISCOVER(
        R.string.discover,
        R.drawable.avd_point_to_discover,
        { DiscoverFragment() },
    ),
    SEARCH(
        R.string.learn,
        R.drawable.avd_point_to_search,
        { SearchFragment() },
    ),
    LOCAL_SPACE(
        R.string.recollect,
        R.drawable.avd_point_to_book,
        { LocalSpaceFragment() },
    );

    companion object {
        fun actionsMessages(ctx: Context): List<String> {
            return HomePages.entries.map { ctx.getString(it.actionMessage) }
        }
    }
}