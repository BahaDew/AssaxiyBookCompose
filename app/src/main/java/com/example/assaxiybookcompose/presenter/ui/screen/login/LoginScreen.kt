package com.example.assaxiybookcompose.presenter.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.presenter.ui.theme.Active
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.presenter.ui.theme.ThreeColorBg
import com.example.assaxiybookcompose.presenter.ui.screen.login.LoginIntent.*

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = getViewModel<LoginViewModelImpl>()
        ScreenContent(eventDispatcher = viewModel::onDispatch)
    }
}

@Composable
fun ScreenContent(eventDispatcher: (LoginIntent) -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppBgColor)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                val gmailText = rememberSaveable { mutableStateOf("y@gmail.com") }
                val passwordText = rememberSaveable { mutableStateOf("12345678") }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(AppBgColor)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 10.dp)
                            .width(30.dp)
                            .height(30.dp),
                        painter = painterResource(id = R.drawable.ic_logo_1),
                        contentDescription = ""
                    )
                    Image(
                        modifier = Modifier
                            .width(100.dp)
                            .height(60.dp),
                        painter = painterResource(id = R.drawable.ic_logo_2),
                        contentDescription = ""
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Xush kelibsiz !",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 25.sp
                )
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Log In",
                    color = ThreeColorBg,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    text = "Emailingizni kiriting",
                    color = Color.White
                )
                TextField(
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .clip(RoundedCornerShape(25))
                        .fillMaxWidth(),
                    colors = TextFieldDefaults
                        .colors(
                            focusedContainerColor = Color(0xFF616161),
                            unfocusedContainerColor = Color(0xFF616161),
                            focusedTextColor = Color.White,
                            disabledTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                    value = gmailText.value,
                    onValueChange = {
                        gmailText.value = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    maxLines = 1
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    text = "Parol",
                    color = Color.White
                )
                TextField(
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .clip(RoundedCornerShape(25))
                        .fillMaxWidth(),
                    colors = TextFieldDefaults
                        .colors(
                            focusedContainerColor = Color(0xFF616161),
                            unfocusedContainerColor = Color(0xFF616161),
                            focusedTextColor = Color.White,
                            disabledTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                    value = passwordText.value,
                    onValueChange = {
                        passwordText.value = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )
                Button(
                    modifier = Modifier
                        .padding(
                            top = 40.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .height(54.dp)
                        .fillMaxWidth(),
                    onClick = {
                        eventDispatcher.invoke(
                            OnClickNext(
                                email = gmailText.value,
                                password = passwordText.value
                            )
                        )
                    },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(containerColor = Active)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Kirish",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .height(2.dp)
                            .clip(CircleShape)
                            .padding(horizontal = 20.dp)
                            .background(Color.White)
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(color = AppBgColor)
                            .padding(horizontal = 10.dp),
                        text = "Yoki",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .height(54.dp)
                        .fillMaxWidth(),
                    onClick = { eventDispatcher.invoke(OnClickRegister) },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF616161))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp),
                        text = "Email orqali ro'yhatdan o'tish",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AssaxiyBookComposeTheme {
        ScreenContent({})
    }
}