package com.jeredev.dogedex.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jeredev.dogedex.R
import com.jeredev.dogedex.composables.AuthField
import com.jeredev.dogedex.composables.DogTopAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(onRegisterButtonClick: () -> Unit, onLoginButtonClick: (String, String) -> Unit) {
    Scaffold(topBar = { DogTopAppBar(text = stringResource(R.string.top_app_bar_login)) }) {
        Content(
            onRegisterButtonClick = onRegisterButtonClick,
            onLoginButtonClick = onLoginButtonClick
        )
    }
}

@Composable
private fun Content(
    onRegisterButtonClick: () -> Unit,
    onLoginButtonClick: (String, String) -> Unit
) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            label = stringResource(R.string.email),
            email = email.value,
            onTextChanged = {
                email.value = it
            },
            modifier = Modifier.fillMaxWidth(),
        )
        AuthField(
            label = stringResource(R.string.password),
            email = password.value,
            onTextChanged = {
                password.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = { onLoginButtonClick(email.value, password.value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 6.dp, end = 6.dp),
            colors = ButtonDefaults.buttonColors(Color.Cyan)
        ) {
            Text(text = stringResource(id = R.string.login))
        }

        Text(
            text = stringResource(R.string.not_have_an_accout),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { onRegisterButtonClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 6.dp, end = 6.dp),
            colors = ButtonDefaults.buttonColors(Color.White)
        ) {
            Text(text = stringResource(R.string.register_account))
        }

    }
}



