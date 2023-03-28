package com.jeredev.dogedex.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.response.ApiResponseStatus

@Composable
fun ErrorDialog(
    messageId: Int,
    onDialogDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = stringResource(R.string.error_dialog_tittle))
        },
        text = {
            Text(
                stringResource(id = messageId)
            )
        },
        confirmButton = {
            Button(onClick = { onDialogDismiss() }) {
                Text(text = stringResource(R.string.try_again_alert_dialog))
            }
        }
    )
}