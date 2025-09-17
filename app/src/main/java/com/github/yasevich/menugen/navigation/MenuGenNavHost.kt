package com.github.yasevich.menugen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.yasevich.menugen.feature.menu.MenuScreen
import com.github.yasevich.menugen.feature.upload.UploadScreen

@Composable
fun MenuGenNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = UploadDestination::class,
        modifier = modifier,
    ) {
        composable<UploadDestination> {
            UploadScreen(
                navigateToMenu = {
                    navController.navigate(route = MenuDestination)
                }
            )
        }
        composable<MenuDestination> {
            MenuScreen()
        }
    }
}
