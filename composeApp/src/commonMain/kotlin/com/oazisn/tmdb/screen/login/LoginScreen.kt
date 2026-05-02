package com.oazisn.tmdb.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.oazisn.tmdb.theme.NetflixColors
import com.oazisn.tmdb.theme.NetflixSpacing

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NetflixColors.BlackCanvas)
            .padding(NetflixSpacing.Base),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to TMDb",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = NetflixColors.WhitePrimary
            )
        )

        Spacer(modifier = Modifier.height(NetflixSpacing.LG))

        OutlinedTextField(
            value = viewModel.username,
            onValueChange = { viewModel.onUsernameChanged(it) },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = NetflixColors.WhitePrimary,
                unfocusedTextColor = NetflixColors.WhitePrimary,
                focusedBorderColor = NetflixColors.GrayMuted,
                unfocusedBorderColor = NetflixColors.GrayMedium,
                focusedLabelColor = NetflixColors.GrayMuted,
                unfocusedLabelColor = NetflixColors.GrayMuted,
                cursorColor = NetflixColors.RedPrimary
            )
        )

        Spacer(modifier = Modifier.height(NetflixSpacing.Base))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = NetflixColors.WhitePrimary,
                unfocusedTextColor = NetflixColors.WhitePrimary,
                focusedBorderColor = NetflixColors.GrayMuted,
                unfocusedBorderColor = NetflixColors.GrayMedium,
                focusedLabelColor = NetflixColors.GrayMuted,
                unfocusedLabelColor = NetflixColors.GrayMuted,
                cursorColor = NetflixColors.RedPrimary
            )
        )

        Spacer(modifier = Modifier.height(NetflixSpacing.Base))

        if (viewModel.viewState is LoginViewState.Error) {
            Text(
                text = (viewModel.viewState as LoginViewState.Error).message,
                color = NetflixColors.RedPrimary,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(NetflixSpacing.SM))
        }

        Button(
            onClick = { viewModel.login(onLoginSuccess) },
            modifier = Modifier
                .fillMaxWidth()
                .height(NetflixSpacing.XXL),
            enabled = viewModel.viewState !is LoginViewState.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = NetflixColors.RedPrimary,
                contentColor = NetflixColors.WhitePrimary,
                disabledContainerColor = NetflixColors.RedPrimary.copy(alpha = 0.5f)
            )
        ) {
            if (viewModel.viewState is LoginViewState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(NetflixSpacing.Base + NetflixSpacing.XXS),
                    color = NetflixColors.WhitePrimary,
                    strokeWidth = NetflixSpacing.XXS
                )
            } else {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
