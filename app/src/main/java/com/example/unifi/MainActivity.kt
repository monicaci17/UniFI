package com.example.unifi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.data.repository.UserRepository
import com.example.unifi.navigation.AppNavigation
import com.example.unifi.state.ThemeState
import com.example.unifi.ui.theme.UnifiTheme
import com.example.unifi.viewmodel.UserViewModel
import com.example.unifi.viewmodel.UserViewModelFactory

class MainActivity : ComponentActivity() {
    val repository = UserRepository()
    val viewModel = UserViewModel(repository)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            UnifiTheme(
                darkTheme = viewModel.isDarkTheme
            ) {
                AppNavigation(viewModel)
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