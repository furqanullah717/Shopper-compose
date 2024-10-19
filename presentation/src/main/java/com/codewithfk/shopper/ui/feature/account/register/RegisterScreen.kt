package com.codewithfk.shopper.ui.feature.account.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codewithfk.shopper.R
import com.codewithfk.shopper.navigation.HomeScreen
import com.codewithfk.shopper.navigation.RegisterScreen
import com.codewithfk.shopper.ui.feature.account.login.LoginState
import com.codewithfk.shopper.ui.feature.account.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = koinViewModel()) {

    val loginState = viewModel.registerState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = loginState.value) {
            is RegisterState.Success -> {
                LaunchedEffect(loginState.value) {
                    navController.navigate(HomeScreen) {
                        popUpTo(HomeScreen) {
                            inclusive = true
                        }
                    }
                }
            }

            is RegisterState.Error -> {
                Text(text = state.message)
                // Show error message
            }

            is RegisterState.Loading -> {
                CircularProgressIndicator()
                Text(text = stringResource(id = R.string.loading))
            }

            else -> {
                RegisterContent(onRegisterClicked = { email, password, name ->
                    viewModel.register(email = email, password = password, name = name)
                },
                    onSignInClick = {
                        navController.popBackStack()
                    })
            }
        }
    }
}


@Composable
fun RegisterContent(
    onRegisterClicked: (String, String, String) -> Unit,
    onSignInClick: () -> Unit
) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val name = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = stringResource(id = R.string.register), style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.name)) }
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.email)) }
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                onRegisterClicked(email.value, password.value, name.value)
            }, modifier = Modifier.fillMaxWidth(),
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty() && name.value.isNotEmpty()
        ) {
            Text(text = stringResource(id = R.string.register))
        }
        Text(text = stringResource(id = R.string.alread_have_an_account), modifier = Modifier
            .padding(8.dp)
            .clickable {
                onSignInClick()
            })
    }
}
