package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.DiscoverFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.HomeFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.LocalSpaceFragment
import ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.SearchFragment

class HomeFragmentAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount() = HomeFragment.NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DiscoverFragment()
            1 -> SearchFragment()
            2 -> LocalSpaceFragment()
            else -> throw IllegalArgumentException("Unexpected value")
        }
    }

}