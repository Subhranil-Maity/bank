package com.akash.bank.ui.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: (String) -> Unit, // Pass the username when logging in/registering
    onRegisterSuccess: Any
) {
    var isLoginScreen by remember { mutableStateOf(true) }
    val gradientColors = listOf(
        Color(0xFFE0EAFC),
        Color(0xFFCFDEF3)
    )
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(gradientColors)),
        color = Color.Transparent
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = isLoginScreen,
                transitionSpec = {
                    if (targetState) {
                        (slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(durationMillis = 300)))
                            .togetherWith(slideOutHorizontally(targetOffsetX = { it }) + fadeOut(animationSpec = tween(durationMillis = 200)))
                    }else{
                        (slideInHorizontally(initialOffsetX = { it }) + fadeIn(animationSpec = tween(durationMillis = 300)))
                            .togetherWith(slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(durationMillis = 200)))
                    }
                }, label = "Form Animation"
            ) { targetState ->
                if (targetState) {
                    LoginForm(
                        onRegisterClick = { isLoginScreen = false },
                        onLoginSuccess = onLoginSuccess // Pass on the login success callback
                    )
                }else{
                    RegisterForm(
                        onLoginClick = { isLoginScreen = true},
                        onRegisterSuccess = onLoginSuccess // Pass on the register success callback
                    )
                }
            }


        }
    }
}


@Composable
fun LoginForm(
    onRegisterClick: () -> Unit,
    onLoginSuccess: (String) -> Unit //  callback for login
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            // TODO: Handle actual login logic here, once successfully logged in you call onLoginSuccess
            val userName = "Alexander Michael"
            onLoginSuccess(userName) // pass the name and then navigate
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "Don't have an account? ")
            TextButton(onClick = onRegisterClick) {
                Text(text = "Register Here")
            }
        }
    }
}

@Composable
fun RegisterForm(
    onLoginClick: () -> Unit,
    onRegisterSuccess: (String) -> Unit // callback for registration
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Register", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            // TODO: Handle actual registration logic, once successfully registered you call onRegisterSuccess
            onRegisterSuccess(name) // Pass on the name to the home screen
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Register")
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "Already have an account? ")
            TextButton(onClick = onLoginClick) {
                Text(text = "Login Here")
            }
        }
    }
}