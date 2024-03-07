package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.presentation.view.adapter.HomeFragmentAdapter
import ru.kpfu.itis.ponomarev.lexify.databinding.FragmentHomeBinding
import ru.kpfu.itis.ponomarev.lexify.util.StringInterpolator
import ru.kpfu.itis.ponomarev.lexify.util.dpToPx
import java.lang.RuntimeException
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        val msgInterpolator = StringInterpolator(msgTemplate, MESSAGE_WORDS)
        val pagerIndicatorItems = arrayOfNulls<AnimatedVectorDrawable>(NUM_PAGES)
        for (i in 0 until NUM_PAGES) {
            val avd = AppCompatResources.getDrawable(
                requireContext(),
                when (i) {
                    0 -> R.drawable.avd_point_to_discover
                    1 -> R.drawable.avd_point_to_search
                    2 -> R.drawable.avd_point_to_book
                    else -> throw RuntimeException("Unexpected value")
                }
            ) as AnimatedVectorDrawable
//            avd.setTint(com.google.android.material.R.attr.colorOnSurface)
            val iv = ImageView(requireContext()).apply {
                setImageDrawable(avd)
            }
            pagerIndicatorItems[i] = avd
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
                    val marginDp = (position - (NUM_PAGES - 1) / 2.0 + positionOffset) * (16 + 24 + 12 + 12 + 16)
                    val marginPx = requireContext().dpToPx(marginDp.toInt())
                    if (marginPx < 0.0) {
                        binding.llPagerIndicator.updatePadding(left = -marginPx, right = 0)
                    } else {
                        binding.llPagerIndicator.updatePadding(right = marginPx, left = 0)
                    }

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
            setCurrentItem(1, false)

            binding.tvMessage.text = msgInterpolator.interpolate(currentItem.toDouble())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val NUM_PAGES = 3

        private val MESSAGE_WORDS = listOf("discover", "learn", "recollect")
    }

}