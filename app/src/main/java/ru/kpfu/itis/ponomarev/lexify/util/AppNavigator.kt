package ru.kpfu.itis.ponomarev.lexify.util

import androidx.navigation.NavController

interface AppNavigator {
    val navController: NavController

    fun initNavController(hostFragmentId: Int): NavController
}