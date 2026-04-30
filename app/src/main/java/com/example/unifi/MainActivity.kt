package com.example.unifi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unifi.navigation.AppNavigation
import com.example.unifi.state.ThemeState
import com.example.unifi.ui.theme.UnifiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnifiTheme (darkTheme = ThemeState.isDarkTheme.value) {
                AppNavigation()
            }
        }
    }
}

/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnifiTheme {
        Greeting("Android")
    }
}*/