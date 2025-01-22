package com.akash.bank.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.akash.bank.R
import com.akash.bank.ui.send.SendScreen
import com.akash.bank.ui.cards.AddCardScreen
import com.akash.bank.ui.cards.CardData
import com.akash.bank.ui.topup.TopUpScreen
import com.akash.bank.ui.notifications.NotificationData
import com.akash.bank.ui.notifications.NotificationsScreen
import com.akash.bank.ui.pay.PayScreen
import com.akash.bank.ui.invest.InvestScreen

@Composable
fun HomeScreen(
    userName: String,
    modifier: Modifier = Modifier,
    onNavigateToSend: (Double, (Double) -> Unit) -> Unit,
    availableBalance: Double = 10000.0,
    onNavigateToAddCard: (() -> Unit) -> Unit,
    cardList: List<CardData>,
    onNavigateToTopUp: () -> Unit,
    onNavigateToNotification: () -> Unit,
    onNavigateToPay: () -> Unit,
    onNavigateToInvest: () -> Unit,
    onNavigateToBorrow: () -> Unit
) {
    val gradientColors = listOf(
        Color(0xFFF0F0F0),
        Color(0xFFE0E0E0)
    )
    var balance by remember{ mutableStateOf(availableBalance) }
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(gradientColors)),
        color = Color.Transparent
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            StatusBarArea()
            HeaderSection(userName, onNavigateToNotification = onNavigateToNotification)
            BalanceAndActions(onNavigateToSend = onNavigateToSend, availableBalance = balance, onUpdateBalance = {
                balance -= it
            }, onNavigateToTopUp = onNavigateToTopUp, onNavigateToPay = onNavigateToPay)
            CardSection(cardList = cardList, onNavigateToAddCard = onNavigateToAddCard)
            InvestAndBorrow(onNavigateToInvest = onNavigateToInvest, onNavigateToBorrow = onNavigateToBorrow)
            SavingSection()
            BottomNavigation()
        }


    }
}

@Composable
fun StatusBarArea(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(24.dp) // Height of the status bar
        .padding(bottom = 5.dp)
    ) {
        // Can add system time or system icon in future if required
    }
}

@Composable
fun HeaderSection(userName:String, onNavigateToNotification: () -> Unit) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column {
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black.copy(alpha = 0.8f), fontSize = 18.sp)
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 24.sp)
            )
        }
        IconButton(onClick = {
            Log.d("HomeScreen", "Notification button clicked")
            onNavigateToNotification()
        }) {
            Icon(imageVector = Icons.Filled.Notifications, contentDescription = "Notification", modifier = Modifier.size(35.dp), tint = Color.Black )
        }
    }

}


@Composable
fun BalanceAndActions(onNavigateToSend: (Double, (Double) -> Unit) -> Unit, availableBalance: Double, onUpdateBalance: (Double) -> Unit, onNavigateToTopUp: () -> Unit, onNavigateToPay: () -> Unit) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200) // Delay to allow the screen to render
        visible = true // Update the state to start animation
    }


    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + scaleIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + scaleOut(animationSpec = tween(durationMillis = 300))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
                .clickable {
                    Log.d("HomeScreen", "Balance card clicked")
                },
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Balance",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "Rs. ${availableBalance.toInt()}",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ActionItem("Send", onClick = {
                        Log.d("HomeScreen", "Send action clicked")
                        onNavigateToSend(availableBalance, onUpdateBalance)

                    })
                    ActionItem("Top Up", onClick = {
                        Log.d("HomeScreen", "Top Up action clicked")
                        onNavigateToTopUp()
                    }, onNavigateToPay = onNavigateToPay)
                    ActionItem("Request", onClick = {Log.d("HomeScreen", "Request action clicked")})
                    ActionItem("Pay", onClick = {
                        Log.d("HomeScreen", "Pay action clicked")
                        onNavigateToPay()
                    })
                }
            }
        }
    }
}


@Composable
fun ActionItem(text: String, onClick: () -> Unit, onNavigateToPay: (() -> Unit)? = null) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier.shadow(elevation = 3.dp, shape = RoundedCornerShape(12.dp))
                .clickable {
                    if(onNavigateToPay != null && text == "Pay"){
                        onNavigateToPay()
                    }
                    else{
                        onClick()
                    }
                },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F1F4))
        ) {
            Box(modifier = Modifier.padding(12.dp)){
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = text,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
        Text(text = text, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium, fontSize = 16.sp))
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CardSection(cardList: List<CardData>, onNavigateToAddCard: (() -> Unit) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top=20.dp, bottom=20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Your Cards", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp))
        TextButton(onClick = {
            onNavigateToAddCard{}
            Log.d("HomeScreen", "View All card clicked")
        }) {
            Text(text = "Add a card", fontSize = 16.sp)
        }
    }
    LazyRow(
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ){
        items(cardList.size){ index ->
            AnimatedCardItem(cardItemData = cardList[index], onClick = {
                Log.d("HomeScreen", "Card item clicked: ${cardList[index].cardNumber}")
            })
        }

    }
}


@Composable
fun AnimatedCardItem(cardItemData: CardData, onClick: () -> Unit){
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200) // Delay to allow the screen to render
        visible = true // Update the state to start animation
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + scaleIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + scaleOut(animationSpec = tween(durationMillis = 300))
    ) {
        CardItem(cardItemData = cardItemData,  onClick = onClick)
    }
}


@Composable
fun CardItem(cardItemData: CardData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF303346)
        )
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
            Text(text = cardItemData.cardType, color = Color.White.copy(alpha = 0.8f), fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "****-****-****-${cardItemData.cardNumber.takeLast(4)}", color = Color.White, fontSize = 18.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Rs. ${cardItemData.balance.toInt()}", color = Color.White, fontSize = 16.sp)
                if(cardItemData.expiry != null){
                    Text(text = cardItemData.expiry, color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SavingSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top=20.dp, bottom=20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Your Saving", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp))
        TextButton(onClick = {
            Log.d("HomeScreen", "View All savings clicked")
        }) {
            Text(text = "View All", fontSize = 16.sp)
        }
    }
    val savingItems = listOf(
        SavingItemData(title = "Buy Car Remote", subTitle = "Mercedes Benz 001", percent = 80, cardColor = listOf(Color(0xFF4CAF50), Color(0xFF81C784))),
        SavingItemData(title = "Buy Playstation", subTitle = null, percent = null, cardColor = listOf(Color(0xFFFF9800), Color(0xFFF4B74D)))
    )
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        items(savingItems.size) { index ->
            AnimatedSavingItem(savingItemData = savingItems[index], onClick = {
                Log.d("HomeScreen", "Saving item clicked: ${savingItems[index].title}")
            })
        }
    }

}

data class SavingItemData(val title: String, val subTitle:String?, val percent: Int?, val cardColor: List<Color>)

@Composable
fun AnimatedSavingItem(savingItemData: SavingItemData, onClick: () -> Unit){
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200) // Delay to allow the screen to render
        visible = true // Update the state to start animation
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + scaleIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + scaleOut(animationSpec = tween(durationMillis = 300))
    ) {
        SavingItem(title = savingItemData.title, subTitle = savingItemData.subTitle, percent = savingItemData.percent, cardColor = savingItemData.cardColor, onClick = onClick)
    }
}

@Composable
fun SavingItem(title: String, subTitle:String?, percent: Int?, cardColor: List<Color>, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(cardColor))
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium, fontSize = 18.sp))
                    if(subTitle != null){
                        Text(text = subTitle, style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontSize = 14.sp))
                    }
                }
                if (percent != null) {
                    Text(text = "${percent}%", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}


@Composable
fun InvestAndBorrow(onNavigateToInvest: () -> Unit, onNavigateToBorrow: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + scaleIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + scaleOut(animationSpec = tween(durationMillis = 300))
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp), contentAlignment = Alignment.Center) {
            Column( horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Extras", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
                            .clickable {
                                Log.d("HomeScreen", "Invest Clicked")
                                onNavigateToInvest()
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F1F4))
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.invest),
                                contentDescription = "Invest",
                                modifier = Modifier.size(30.dp),
                                tint = Color.Blue
                            )
                            Text(
                                text = "Invest",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
                            .clickable { Log.d("HomeScreen", "Borrow Clicked")
                                onNavigateToBorrow()
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F1F4))
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.invest),
                                contentDescription = "Borrow",
                                modifier = Modifier.size(30.dp),
                                tint = Color.Blue
                            )
                            Text(
                                text = "Borrow",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

        }

    }
}
@Composable
fun BottomNavigation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomNavItem("Home", isSelected = true, onClick = {
            Log.d("HomeScreen", "Home nav button clicked")
        })
        BottomNavItem("History", onClick = {
            Log.d("HomeScreen", "History nav button clicked")
        })
        BottomNavItem("Profile", onClick = {
            Log.d("HomeScreen", "Profile nav button clicked")
        })
    }
}

@Composable
fun BottomNavItem(title: String, isSelected: Boolean = false, onClick: () -> Unit) {
    val textColor = if(isSelected) Color.Black else Color.Gray
    val fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }) {
        Icon(imageVector = Icons.Filled.Home, contentDescription = title, modifier = Modifier.size(30.dp), tint = textColor )
        Text(text = title, style = MaterialTheme.typography.bodySmall.copy(color = textColor, fontWeight = fontWeight, fontSize = 14.sp))
    }
}