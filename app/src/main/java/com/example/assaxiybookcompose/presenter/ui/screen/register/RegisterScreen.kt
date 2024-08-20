package com.example.assaxiybookcompose.presenter.ui.screen.register

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
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.example.assaxiybookcompose.presenter.ui.screen.register.RegisterIntent.*

class RegisterScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: RegisterViewModel = getViewModel<RegisterViewModelImpl>()
        val uiState = viewModel.registerState.collectAsState()
        ScreenContent(eventDispatcher = viewModel::onDispatch)
    }
}

@Composable
fun ScreenContent(eventDispatcher: (RegisterIntent) -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBgColor)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(AppBgColor)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppBgColor)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val gmailText = rememberSaveable { mutableStateOf("") }
                    val passwordText = rememberSaveable { mutableStateOf("") }
                    val fullNameText = rememberSaveable { mutableStateOf("") }
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FilledIconButton(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .width(40.dp)
                                .height(40.dp)
                                .clip(CircleShape)
                                .background(color = Color.Transparent)
                                .align(alignment = Alignment.CenterStart),
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Color.Transparent
                            ),
                            onClick = { eventDispatcher.invoke(OnClickBack) },
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp),
                                painter = painterResource(id = R.drawable.ic_left_chevron_svgrepo_com),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .align(Alignment.Center)
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
                    }

                    Text(
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Yangi akkaunt ochish",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 25.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Assaxiy books ilovasidan ro'yhatdan oltin",
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
                        text = "Ism Sharifingiz",
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
                                focusedPlaceholderColor = Color(0xFFB1B1B1),
                                unfocusedPlaceholderColor = Color(0xFFB1B1B1),
                                cursorColor = Color.White
                            ),
                        value = fullNameText.value,
                        onValueChange = {
                            fullNameText.value = it
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        placeholder = {
                            Text(text = "Ism Sharifingizni kiriting")
                        }
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
                                focusedPlaceholderColor = Color(0xFFB1B1B1),
                                unfocusedPlaceholderColor = Color(0xFFB1B1B1),
                                cursorColor = Color.White
                            ),
                        value = gmailText.value,
                        onValueChange = {
                            gmailText.value = it
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        placeholder = {
                            Text(text = "example@gmail.com")
                        }
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
                                focusedPlaceholderColor = Color(0xFFB1B1B1),
                                unfocusedPlaceholderColor = Color(0xFFB1B1B1),
                                cursorColor = Color.White
                            ),
                        value = passwordText.value,
                        onValueChange = {
                            passwordText.value = it
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        placeholder = {
                            Text(text = "Parol")
                        },
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
                                OnClickSubmit(
                                    fullName = fullNameText.value,
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