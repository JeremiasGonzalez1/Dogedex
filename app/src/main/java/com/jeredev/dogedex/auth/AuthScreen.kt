package com.jeredev.dogedex.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.auth.AuthNavDestinations.LoginScreenDestination
import com.jeredev.dogedex.auth.AuthNavDestinations.SingUpScreenDestination
import com.jeredev.dogedex.composables.ErrorDialog
import com.jeredev.dogedex.composables.LoadingWheel
import com.jeredev.dogedex.model.User

@Composable
fun AuthScreen(
    status: ApiResponseStatus<User>?,
    onErrorDialogDismiss: () -> Unit,
    onLoginButtonClick: (String, String) -> Unit, onRegisterButtonClick: (
        email: String,
        password: String,
        passwordConfirmation: String
    ) -> Unit,
    viewModel: AuthViewModel
) {

    val navControler = rememberNavController()

    AuthNavHost(
        navControler = navControler,
        onLoginButtonClick = onLoginButtonClick,
        onRegisterButtonClick = onRegisterButtonClick,
        viewModel
    )

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(
            status.message,
            onDialogDismiss = onErrorDialogDismiss
        )
    }
}

@Composable
private fun AuthNavHost(
    navControler: NavHostController,
    onLoginButtonClick: (String, String) -> Unit,
    onRegisterButtonClick: (
        email: String,
        password: String,
        passwordConfirmation: String
    ) -> Unit,
    viewModel: AuthViewModel
) {
    NavHost(navController = navControler, startDestination = LoginScreenDestination) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                onRegisterButtonClick = {
                    navControler.navigate(route = SingUpScreenDestination)
                },
                onLoginButtonClick = onLoginButtonClick,
                viewModel = viewModel
            )
        }
        composable(route = SingUpScreenDestination) {
            SingUpScreen(
                onNavigationBackClicked = { navControler.navigateUp() },
                onRegisterButtonClick = onRegisterButtonClick,
                viewModel = viewModel
            )
        }
    }
}