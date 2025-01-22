package com.akash.bank.ui.topup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import android.util.Log

@Composable
fun TopUpScreen(onBack: () -> Unit) {
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
            TopUpContent()
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
            text = "Add Amount",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun TopUpContent(){
    val cardList = listOf(
        TopUpCardData("Play Store", listOf(Color(0xFF34A853), Color(0xFFB7E9C2)), onClick = { Log.d("TopUpScreen", "Play Store clicked") }),
        TopUpCardData("Apple Store", listOf(Color.White, Color.LightGray), onClick = { Log.d("TopUpScreen", "Apple Store clicked") }),
        TopUpCardData("Steam", listOf(Color(0xFF171A21), Color(0xFF66C0F4)), onClick = { Log.d("TopUpScreen", "Steam clicked") }),
        TopUpCardData("Epic Games", listOf(Color(0xFF2F2F2F), Color(0xFFC9D9E1)), onClick = { Log.d("TopUpScreen", "Epic Games clicked") }),
        TopUpCardData("Play Station", listOf(Color(0xFF00439C), Color(0xFF6CBDF4)), onClick = { Log.d("TopUpScreen", "Play Station clicked") }),
        TopUpCardData("Xbox", listOf(Color(0xFF107C10), Color(0xFF67A467)), onClick = { Log.d("TopUpScreen", "Xbox clicked") }),
        TopUpCardData("Amazon", listOf(Color(0xFF3D4856), Color(0xFFF9AE25)), onClick = { Log.d("TopUpScreen", "Amazon clicked") }),
        TopUpCardData("Flipkart", listOf(Color(0xFF2874F0), Color(0xFF9BC8FC)), onClick = { Log.d("TopUpScreen", "Flipkart clicked") })

    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(cardList.size){ index ->
            TopUpCardItem(cardData = cardList[index])
        }
    }


}

@Composable
fun TopUpCardItem(cardData: TopUpCardData) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
        .clickable {
            cardData.onClick()
        },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box (modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(cardData.cardColor)), contentAlignment = Alignment.Center){
            Text(text = cardData.cardName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

data class TopUpCardData(val cardName: String, val cardColor: List<Color>, val onClick: () -> Unit)