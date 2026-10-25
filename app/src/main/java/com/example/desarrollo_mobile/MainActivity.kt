package com.example.desarrollo_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.desarrollo_mobile.navigation.WimNavGraph
import com.example.desarrollo_mobile.ui.theme.Desarrollo_mobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Desarrollo_mobileTheme {
                val navController = rememberNavController()
                WimNavGraph(navController = navController, modifier = Modifier.fillMaxSize())
            }
        }
    }
}