package com.akash.bank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.akash.bank.ui.auth.AuthScreen
import com.akash.bank.ui.home.HomeScreen
import com.akash.bank.ui.send.SendScreen
import com.akash.bank.ui.theme.BankTheme
import com.akash.bank.ui.cards.AddCardScreen
import com.akash.bank.ui.cards.CardData
import com.akash.bank.ui.topup.TopUpScreen
import com.akash.bank.ui.notifications.NotificationData
import com.akash.bank.ui.notifications.NotificationsScreen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.akash.bank.ui.paymentauth.PaymentAuthScreen
import com.akash.bank.ui.pay.PayScreen
import com.akash.bank.ui.invest.InvestScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BankTheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                var userName by remember { mutableStateOf("") }
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Auth) }
                var availableBalance by remember { mutableStateOf(10000.0) }
                var cardList by remember { mutableStateOf(listOf<CardData>())}
                var notifications by remember { mutableStateOf(listOf<NotificationData>()) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = {
                            if (targetState == Screen.Home){
                                (slideInVertically(initialOffsetY = { it }) + fadeIn(animationSpec = tween(durationMillis = 300)))
                                    .togetherWith(slideOutVertically(targetOffsetY = { -it }) + fadeOut(animationSpec = tween(durationMillis = 200)))
                            } else if (targetState == Screen.Auth){
                                (slideInVertically(initialOffsetY = { -it }) + fadeIn(animationSpec = tween(durationMillis = 300)))
                                    .togetherWith(slideOutVertically(targetOffsetY = { it }) + fadeOut(animationSpec = tween(durationMillis = 200)))
                            }
                            else {
                                fadeIn(animationSpec = tween(durationMillis = 300))
                                    .togetherWith(fadeOut(animationSpec = tween(durationMillis = 200)))
                            }
                        }, label = "Screen Transition"
                    ) { targetState ->
                        when (targetState) {
                            Screen.Auth -> AuthScreen(
                                modifier = Modifier,
                                onLoginSuccess = { name: String ->
                                    isLoggedIn = true
                                    userName = name
                                    currentScreen = Screen.Home
                                },
                                onRegisterSuccess = { name: String ->
                                    isLoggedIn = true
                                    userName = name
                                    currentScreen = Screen.Home
                                }
                            )
                            Screen.Home ->  HomeScreen(userName = userName, modifier = Modifier,
                                onNavigateToSend = { balance, onUpdateBalance ->
                                    availableBalance = balance
                                    currentScreen = Screen.Send(onUpdateBalance)
                                },
                                availableBalance = availableBalance,
                                onNavigateToAddCard = {
                                    currentScreen = Screen.AddCard
                                },
                                cardList = cardList,
                                onNavigateToTopUp = {
                                    currentScreen = Screen.TopUp
                                },
                                onNavigateToNotification = {
                                    currentScreen = Screen.Notifications
                                },
                                onNavigateToPay = {
                                    currentScreen = Screen.Pay
                                },
                                onNavigateToInvest = {
                                    currentScreen = Screen.Invest
                                }
                            )

                            is Screen.Send -> SendScreen(
                                availableBalance = availableBalance,
                                onBack = { currentScreen = Screen.Home },
                                onSendSuccess = {amount, card ->
                                    availableBalance -= amount

                                    val cardIndex = cardList.indexOf(card)
                                    if(cardIndex != -1) {
                                        val updatedCard = card.copy(balance = card.balance - amount)
                                        cardList = cardList.toMutableList().also { it[cardIndex] = updatedCard }
                                    }

                                    val calendar = Calendar.getInstance()
                                    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                                    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

                                    val notification = NotificationData(
                                        amount = amount,
                                        date = dateFormat.format(calendar.time),
                                        time = timeFormat.format(calendar.time)
                                    )
                                    notifications = notifications + notification
                                    currentScreen = Screen.Home
                                    targetState.onUpdateBalance(amount)
                                },
                                cardList = cardList,
                                onNavigateToPaymentAuth = { amount, card, onPaymentComplete ->
                                    currentScreen = Screen.PaymentAuth(amount, card,  onPaymentComplete )
                                }
                            )
                            Screen.AddCard -> AddCardScreen(
                                onBack = { currentScreen = Screen.Home },
                                onCardAdded = { card ->
                                    cardList = cardList + card
                                    currentScreen = Screen.Home

                                }
                            )
                            Screen.TopUp -> TopUpScreen(
                                onBack = { currentScreen = Screen.Home }
                            )
                            Screen.Notifications -> NotificationsScreen(onBack = { currentScreen = Screen.Home }, notifications = notifications)
                            is Screen.PaymentAuth -> PaymentAuthScreen(
                                onBack = { currentScreen = Screen.Home },
                                amount = targetState.amount,
                                cardData = targetState.cardData,
                                onPaymentComplete = {
                                    targetState.onPaymentComplete()
                                }
                            )
                            Screen.Pay -> PayScreen(
                                onBack = { currentScreen = Screen.Home}
                            )
                            Screen.Invest -> InvestScreen(onBack = { currentScreen = Screen.Home })
                        }
                    }

                }
            }
        }
    }
}

sealed class Screen {
    object Auth : Screen()
    object Home : Screen()
    object AddCard: Screen()
    object TopUp: Screen()
    object Notifications : Screen()
    object Pay : Screen()
    object Invest : Screen()
    data class Send(val onUpdateBalance: (Double) -> Unit) : Screen()
    data class PaymentAuth(val amount: Double, val cardData: CardData, val onPaymentComplete: () -> Unit) : Screen()
}