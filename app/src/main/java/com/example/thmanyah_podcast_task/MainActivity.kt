package com.example.thmanyah_podcast_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.core.designsystem.theme.ThmanyahTheme
import com.example.thmanyah_podcast_task.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThmanyahTheme {
                AppNavigation(modifier = Modifier.fillMaxSize())
            }
        }
    }
}