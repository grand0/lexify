package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentHomeBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.HomePages
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.HomeFragmentAdapter
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.HomeViewModel
import ru.kpfu.itis.ponomarev.lexify.util.StringInterpolator
import ru.kpfu.itis.ponomarev.lexify.util.dpToPx
import java.util.Calendar

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val msgTemplate = if (hour in 6..19) getString(R.string.home_message_day) else getString(R.string.home_message_night)

        val msgInterpolator = StringInterpolator(msgTemplate, HomePages.actionsMessages(requireContext()))
        val pagerIndicatorItems = arrayOfNulls<AnimatedVectorDrawable>(HomePages.entries.size)
        for (page in HomePages.entries) {
            val avd = AppCompatResources.getDrawable(
                requireContext(),
                page.iconDrawable,
            ) as AnimatedVectorDrawable
            avd.setTint(requireContext().getColor(R.color.high_emphasis))
            val iv = ImageView(requireContext()).apply {
                setImageDrawable(avd)
            }
            pagerIndicatorItems[page.ordinal] = avd
            binding.llPagerIndicator.addView(iv)
        }

        val pagerAdapter = HomeFragmentAdapter(childFragmentManager, lifecycle)
        binding.viewPager.apply {
            adapter = pagerAdapter
            registerOnPageChangeCallback(object: OnPageChangeCallback() {

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    val translateDp = (position - (HomePages.entries.size - 1) / 2.0 + positionOffset) * (16 + 24)
                    val translatePx = requireContext().dpToPx(translateDp.toInt())
                    binding.llPagerIndicator.translationX = -translatePx.toFloat()

                    val message = msgInterpolator.interpolate((position + positionOffset).toDouble())
                    binding.tvMessage.text = message
                }

                override fun onPageSelected(position: Int) {
                    pagerIndicatorItems.forEach {
                        it?.reset()
                    }
                    pagerIndicatorItems[position]?.start()
                }
            })
            setCurrentItem(homeViewModel.currentPageIndex, false)

            binding.tvMessage.text = msgInterpolator.interpolate(currentItem.toDouble())
        }
    }

    override fun onStop() {
        homeViewModel.currentPageIndex = binding.viewPager.currentItem
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}