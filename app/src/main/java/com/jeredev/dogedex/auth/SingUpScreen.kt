package com.jeredev.dogedex.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.jeredev.dogedex.R
import com.jeredev.dogedex.composables.AuthField
import com.jeredev.dogedex.composables.DogTopAppBarWithArrowBack

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SingUpScreen(onNavigationBack: () -> Unit) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val repeatPassword = remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            DogTopAppBarWithArrowBack(
                text = stringResource(R.string.register),
                onClick = onNavigationBack
            )
        }
    ) {
        Content(email, password, repeatPassword)
    }
}

@Composable
private fun Content(
    email: MutableState<String>,
    password: MutableState<String>,
    repeatPassword: MutableState<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AuthField(
            label = stringResource(id = R.string.email),
            email = email.value,
            onTextChanged = { email.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 6.dp)
        )

        AuthField(
            label = stringResource(id = R.string.password),
            email = password.value,
            onTextChanged = { password.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 6.dp, top = 24.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        AuthField(
            label = stringResource(id = R.string.repeat_password),
            email = repeatPassword.value,
            onTextChanged = { repeatPassword.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 6.dp, top = 24.dp),
            visualTransformation = PasswordVisualTransformation()

        )

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 6.dp, start = 6.dp, top = 24.dp),
            colors = ButtonDefaults.buttonColors(Color.Cyan)
        ) {
            Text(text = stringResource(R.string.create_user))
        }

    }
}