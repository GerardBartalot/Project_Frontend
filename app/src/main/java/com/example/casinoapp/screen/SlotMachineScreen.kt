package com.example.casinoapp.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.math.roundToLong
import kotlin.random.Random
import com.example.casinoapp.R

@Composable
fun SlotMachineScreen(
    navController: NavController
) {
    val symbols = listOf("bar", "diamond", "heart", "seven", "watermelon", "horseshoe")
    var targetSymbols by remember { mutableStateOf(List(3) { symbols.random() }) }
    var isAnimating by remember { mutableStateOf(false) }
    var startAnimation by remember { mutableStateOf(false) }
    var completedAnimations by remember { mutableIntStateOf(0) }
    var fondoCoins by remember { mutableIntStateOf(100) }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Volver atrás",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text("Fondo Coins: $fondoCoins", style = MaterialTheme.typography.bodyLarge)
            }

            // El contenido principal debe estar centrado
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Frame image
                    Box(
                        modifier = Modifier
                            .size(320.dp, 160.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.frame),
                            contentDescription = "Frame",
                            modifier = Modifier.fillMaxSize()
                        )

                        // Slots dentro del marco
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            targetSymbols.forEach { target ->
                                SlotMachine(
                                    targetSymbol = target,
                                    textStyle = MaterialTheme.typography.headlineMedium,
                                    startAnimation = startAnimation,
                                    onAnimationComplete = {
                                        completedAnimations++
                                        if (completedAnimations == 3) {
                                            isAnimating = false
                                            completedAnimations = 0
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para girar
                    Button(
                        onClick = {
                            if (!isAnimating && fondoCoins >= 10) {
                                isAnimating = true
                                startAnimation = true
                                fondoCoins -= 10
                                targetSymbols = List(3) { symbols.random() }
                            }
                        },
                        enabled = fondoCoins >= 10
                    ) {
                        Text("JUEGA (Cuesta 10 fondopoints)")
                    }
                }
            }
        }
    }

    LaunchedEffect(isAnimating) {
        if (!isAnimating && startAnimation) {
            fondoCoins += calculateScore(targetSymbols)
            startAnimation = false
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlotMachine(
    targetSymbol: String,
    textStyle: TextStyle,
    startAnimation: Boolean,
    delay: Long = (Random.nextFloat() * 300).roundToLong(),
    onAnimationComplete: () -> Unit
) {
    var animationDuration by remember { mutableLongStateOf(100L) }
    var targetOffset by remember { mutableIntStateOf(Random.nextInt(15, 30)) }
    var currentSymbol by remember { mutableStateOf(getSourceSymbol(targetSymbol, targetOffset)) }

    LaunchedEffect(startAnimation) {
        if (startAnimation) {
            targetOffset = Random.nextInt(15, 30)
            animationDuration = 100L
            currentSymbol = getSourceSymbol(targetSymbol, targetOffset)
            delay(delay)
            while (targetOffset > 0) {
                targetOffset--
                animationDuration += ((10 - targetOffset) * 10L).coerceAtLeast(0L)
                currentSymbol = getNextSymbol(currentSymbol)
                delay(animationDuration)
            }
            onAnimationComplete()
        }
    }

    AnimatedContent(
        targetState = currentSymbol,
        transitionSpec = {
            slideInVertically(
                animationSpec = tween(animationDuration.toInt()),
                initialOffsetY = { -it },
            ).togetherWith(
                slideOutVertically(
                    animationSpec = tween(animationDuration.toInt()),
                    targetOffsetY = { it }
                )
            )
        }
    ) {
        Image(
            painter = painterResource(id = getSymbolImageResource(currentSymbol)),
            contentDescription = currentSymbol,
            modifier = Modifier.size(64.dp)
        )
    }
}

private fun getSourceSymbol(targetSymbol: String, targetOffset: Int): String {
    val symbols = listOf("bar", "diamond", "heart", "seven", "watermelon", "horseshoe")
    val index = (symbols.indexOf(targetSymbol) - targetOffset) % symbols.size
    return if (index < 0) symbols[symbols.size + index] else symbols[index]
}

private fun getNextSymbol(currentSymbol: String): String {
    val symbols = listOf("bar", "diamond", "heart", "seven", "watermelon", "horseshoe")
    val index = (symbols.indexOf(currentSymbol) + 1) % symbols.size
    return symbols[index]
}

private fun calculateScore(symbols: List<String>): Int {
    val distinctSymbols = symbols.distinct()
    return when {
        distinctSymbols.size == 1 -> 100
        distinctSymbols.size == 2 -> 50
        else -> 0
    }
}

private fun getSymbolImageResource(symbol: String): Int {
    return when (symbol) {
        "bar" -> R.drawable.bar
        "diamond" -> R.drawable.diamond
        "heart" -> R.drawable.heart
        "seven" -> R.drawable.seven
        "watermelon" -> R.drawable.watermelon
        "horseshoe" -> R.drawable.horseshoe
        else -> R.drawable.bar
    }
}

@Preview
@Composable
fun SlotMachinePreview() {
    SlotMachineScreen(
        navController = TODO()
    )
}