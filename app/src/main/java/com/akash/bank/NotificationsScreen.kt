package com.akash.bank.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificationsScreen(onBack: () -> Unit, notifications: List<NotificationData>) {
    val gradientColors = listOf(
        Color(0xFFF0F0F0),
        Color(0xFFE0E0E0)
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(gradientColors)),
        color = Color.Transparent
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(onBack)
            if(notifications.isEmpty()){
                EmptyNotifications()
            }else {
                NotificationList(notifications = notifications)
            }
        }
    }
}


@Composable
fun TopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun EmptyNotifications() {
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "\uD83D\uDE1E", style = MaterialTheme.typography.headlineLarge) // Sad Face Emoji
        Text(text = "No Notifications", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold, fontSize = 18.sp), modifier = Modifier.padding(top = 10.dp))
    }
}

@Composable
fun NotificationList(notifications: List<NotificationData>){
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)){
        items(notifications){ notification ->
            NotificationItem(notification = notification)
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData){
    Card(modifier = Modifier
        .fillMaxWidth()
        .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(text = "Payment Made", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Rs. ${notification.amount.toInt()}", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 18.sp))
                Text(text = "${notification.date} ${notification.time}", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray, fontSize = 12.sp))
            }
        }

    }
}

data class NotificationData(
    val amount: Double,
    val date: String,
    val time: String
)