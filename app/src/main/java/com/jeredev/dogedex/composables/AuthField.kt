package com.jeredev.dogedex.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthField(
    label: String,
    email: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessageId: Int? = null
) {
    Column(modifier) {
        if (errorMessageId != null) {
            Text(modifier = modifier.fillMaxWidth(), text = stringResource(id = errorMessageId))
        }
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            label = { Text(text = label) },
            value = email,
            onValueChange = { onTextChanged(it) },
            visualTransformation = visualTransformation,
            isError = errorMessageId != null
        )
    }


}