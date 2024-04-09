package ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.model.HomePages

class HomeFragmentAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount() = HomePages.entries.size

    override fun createFragment(position: Int): Fragment {
        return HomePages.entries[position].fragmentProvider.invoke()
    }
}