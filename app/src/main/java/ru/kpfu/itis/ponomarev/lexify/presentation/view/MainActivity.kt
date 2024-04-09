package ru.kpfu.itis.ponomarev.lexify.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.ActivityMainBinding
import ru.kpfu.itis.ponomarev.lexify.util.AppNavigator
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null;
    private val binding get() = _binding!!
    @Inject lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        navigator.initNavController(binding.navHostFragmentContainer.id)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}