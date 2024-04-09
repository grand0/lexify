package ru.kpfu.itis.ponomarev.lexify.presentation.view

import android.os.Bundle
import android.view.ViewAnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import ru.kpfu.itis.ponomarev.lexify.databinding.ActivityMainBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.viewmodel.MainActivityViewModel
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import javax.inject.Inject
import kotlin.math.hypot

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null;
    private val binding get() = _binding!!
    @Inject lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        navigator.initNavController(binding.navHostFragmentContainer.id)

        binding.ivNightModeBtn.setOnClickListener {
            switchNightMode()
        }
        if (mainActivityViewModel.nightModeDidChange()) {
            startNightModeChangedAnimation()
            mainActivityViewModel.clearNightModeChangedStatus()
        }
    }

    fun switchNightMode() {
        mainActivityViewModel.changeNightMode(binding.root)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun startNightModeChangedAnimation() {
        val screenBitmap = mainActivityViewModel.screenBitmap
        if (screenBitmap == null || binding.ivScreenAnim.isVisible) {
            return
        }

        with (binding.ivScreenAnim) {
            setImageBitmap(screenBitmap)
            isVisible = true
            binding.ivNightModeBtn.post { // wait till button is laid out
                val w = screenBitmap.width
                val h = screenBitmap.height
                val radius = hypot(w.toFloat(), h.toFloat())
                val x = binding.ivNightModeBtn.x.toInt() + binding.ivNightModeBtn.measuredWidth / 2
                val y = binding.ivNightModeBtn.y.toInt() + binding.ivNightModeBtn.measuredHeight / 2

                val anim = ViewAnimationUtils.createCircularReveal(this, x, y, radius, 0f)
                anim.duration = 800
                anim.doOnEnd {
                    setImageDrawable(null)
                    isVisible = false
                }
                anim.start()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}