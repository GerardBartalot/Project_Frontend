package com.example.casinoapp.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.casinoapp.R
import com.example.casinoapp.viewModel.RemoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    remoteViewModel: RemoteViewModel,
    navController: NavController
) {
    val currentUser by remoteViewModel.loggedInUser.collectAsState()
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var updateMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            name = it.name
            username = it.username
            password = ""
        }
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1A1A1A),
            Color(0xFF2D2D2D),
            Color(0xFF1A1A1A)
        ),
        startY = 0f,
        endY = 1000f
    )

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                modifier = Modifier.height(100.dp),
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Editar Perfil",
                            color = Color.White,
                            modifier = Modifier.padding(start = 0.dp)
                        )
                    }
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D0D0D),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        ) {
            Image(
                painter = painterResource(id = R.drawable.subtle_texture),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.1f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre", color = Color.White) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF333333),
                        unfocusedContainerColor = Color(0xFF333333),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color.LightGray,
                        cursorColor = Color(0xFFFFD700),
                        focusedIndicatorColor = Color(0xFFFFD700),
                        unfocusedIndicatorColor = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(5.dp))

                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nombre de usuario", color = Color.White) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF333333),
                        unfocusedContainerColor = Color(0xFF333333),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color.LightGray,
                        cursorColor = Color(0xFFFFD700),
                        focusedIndicatorColor = Color(0xFFFFD700),
                        unfocusedIndicatorColor = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(5.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Nueva contraseña", color = Color.White) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF333333),
                        unfocusedContainerColor = Color(0xFF333333),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color.LightGray,
                        cursorColor = Color(0xFFFFD700),
                        focusedIndicatorColor = Color(0xFFFFD700),
                        unfocusedIndicatorColor = Color.Gray
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(10.dp)
                )

                updateMessage?.let { message ->
                    Text(
                        text = message,
                        color = if (message.startsWith("Error")) Color(0xFFFF5252) else Color(0xFF4CAF50),
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        currentUser?.let { user ->
                            val updatedUser = user.copy(
                                name = name,
                                username = username,
                                password = if (password.isNotEmpty()) password else user.password
                            )
                            remoteViewModel.updateUser(updatedUser) { message ->
                                updateMessage = message
                                if (message.contains("éxito")) {
                                    navController.popBackStack()
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFD700),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(60.dp)
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Aplicar cambios",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(
        navController = rememberNavController(),
        remoteViewModel = RemoteViewModel(),
    )
}