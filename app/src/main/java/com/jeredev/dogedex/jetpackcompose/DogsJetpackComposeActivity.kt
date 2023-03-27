package com.jeredev.dogedex.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.jeredev.dogedex.jetpackcompose.ui.theme.Shapes
import com.jeredev.dogedex.jetpackcompose.ui.theme.Typography
import com.jeredev.dogedex.jetpackcompose.views.detail.DetailScreen
import com.jeredev.dogedex.jetpackcompose.views.main.BaseAppDoguedex
import com.jeredev.dogedex.jetpackcompose.views.main.MainScreen


@ExperimentalCoilApi
@ExperimentalFoundationApi
class DogsJetpackComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseAppDoguedex {
                val navControler = rememberNavController()
                NavHost(navController = navControler, startDestination = "main") {
                    composable("main") {
                        MainScreen()
                    }
                    composable(
                        route = "detail",
                        arguments = listOf(navArgument("argument") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("argument")
                        requireNotNull(id, { "Not null for usage for detail" })
                        DetailScreen(id)
                    }
                }
                MaterialTheme(
                    colors = lightColors(),
                    typography = Typography,
                    shapes = Shapes
                ) {
                    MainScreen()

                }
            }
        }
    }
}


@Composable
fun StateExample(value: String, onValueChange: (String) -> Unit) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
                .padding(8.dp)
        )
        Button(
            onClick = { text = "" },
            modifier = Modifier.fillMaxWidth(),
            enabled = text.isNotEmpty()
        ) {
            Text(text = "clear")
        }
    }
}

