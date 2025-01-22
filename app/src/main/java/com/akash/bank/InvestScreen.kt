package com.akash.bank.ui.invest

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import com.akash.bank.R

@Composable
fun InvestScreen(onBack: () -> Unit) {
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
            InvestContent(onBack = onBack)
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
            text = "Invest",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun InvestContent(onBack: () -> Unit) {

    val cardList = listOf(
        TopUpCardData(cardName = "DEMAT", icon = R.drawable.invest, onClick = { Log.d("InvestScreen", "DEMAT Clicked") }),
        TopUpCardData(cardName = "BONDS AND SECURITY", icon = R.drawable.invest, onClick = { Log.d("InvestScreen", "BONDS AND SECURITY Clicked") }),
        TopUpCardData(cardName = "MUTUAL FUNDS",  icon = R.drawable.invest,onClick = { Log.d("InvestScreen", "MUTUAL FUNDS Clicked") }),
        TopUpCardData(cardName = "IPO APPLICATION", icon = R.drawable.invest, onClick = { Log.d("InvestScreen", "IPO APPLICATION Clicked") }),
        TopUpCardData(cardName = "EQUITY AND DEBT MUTUAL FUNDS",  icon = R.drawable.invest, onClick = { Log.d("InvestScreen", "EQUITY AND DEBT MUTUAL FUNDS Clicked") }),
        TopUpCardData(cardName = "RESEARCH REPORTS",  icon = R.drawable.invest,onClick = { Log.d("InvestScreen", "RESEARCH REPORTS Clicked") }),
        TopUpCardData(cardName = "PROTECT LIFE AND GROW WEALTH", icon = R.drawable.invest, onClick = { Log.d("InvestScreen", "PROTECT LIFE AND GROW WEALTH Clicked") }),
        TopUpCardData(cardName = "SMARWEALTH", icon = R.drawable.invest,onClick = { Log.d("InvestScreen", "SMARWEALTH Clicked") }),
        TopUpCardData(cardName = "SECURE CHILDRENS FUTURE", icon = R.drawable.invest, onClick = { Log.d("InvestScreen", "SECURE CHILDRENS FUTURE Clicked") }),
        TopUpCardData(cardName = "DEPOSITS", icon = R.drawable.invest, onClick = { Log.d("InvestScreen", "DEPOSITS Clicked") }),
        TopUpCardData(cardName = "NATIONAL PENSION SYSTEM", icon = R.drawable.invest,onClick = { Log.d("InvestScreen", "NATIONAL PENSION SYSTEM Clicked") }),
        TopUpCardData(cardName = "GOLD MONETIZATON SCHEME", icon = R.drawable.invest,onClick = { Log.d("InvestScreen", "GOLD MONETIZATON SCHEME Clicked") })
    )
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(cardList.size){ index ->
                TopUpCardItem(cardData = cardList[index])
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Button(onClick = onBack,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF303346))) {
                Text(text = "Go Back", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

}

@Composable
fun TopUpCardItem(cardData: TopUpCardData) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
        .clickable {
            cardData.onClick()
        },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFE8F5E9),
                        Color(0xFFF1F8E9)
                    )
                )
            )
            , verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(id = cardData.icon), contentDescription = cardData.cardName, modifier = Modifier.size(40.dp), tint = Color.Blue)
            Text(text = cardData.cardName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(5.dp))
        }
    }
}

data class TopUpCardData(val cardName: String, val onClick: () -> Unit, val icon: Int )