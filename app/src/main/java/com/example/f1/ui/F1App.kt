package com.example.f1

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.f1.ui.F1NavGraph
import com.example.f1.ui.navigation.NavigationBar
import com.example.f1.ui.navigation.NavigationSections
import com.example.f1.ui.theme.F1Theme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun F1App() {
    ProvideWindowInsets {
        F1Theme {
            val tabs = remember { NavigationSections.values() }
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { NavigationBar(navController = navController, tabs = tabs) }
            ) {
                F1NavGraph(navController = navController, modifier = Modifier.padding(it))
            }
        }
    }
}