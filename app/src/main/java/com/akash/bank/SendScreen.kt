package com.akash.bank.ui.send

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import androidx.compose.ui.zIndex
import com.akash.bank.ui.cards.CardData
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SendScreen(
    availableBalance: Double,
    onBack: () -> Unit,
    onSendSuccess: (Double, CardData) -> Unit,
    cardList: List<CardData>,
    onNavigateToPaymentAuth: (Double, CardData, () -> Unit) -> Unit
) {
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
            Column(modifier = Modifier.fillMaxSize()) {
                Content(availableBalance = availableBalance, onSendSuccess = onSendSuccess, cardList = cardList, onBack = onBack,  onNavigateToPaymentAuth = onNavigateToPaymentAuth)
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
            text = "Send Money",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}


@Composable
fun Content(availableBalance: Double, onSendSuccess: (Double, CardData) -> Unit, cardList: List<CardData>, onBack: () -> Unit,  onNavigateToPaymentAuth: (Double, CardData, () -> Unit) -> Unit) {

    var selectedAmount by remember { mutableFloatStateOf(50f) }
    var isInsufficientBalance by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf(cardList.firstOrNull())}
    var isDropdownOpen by remember{ mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column{

            CardSelector(cardList = cardList, onCardSelected = {
                selectedCard = it
                isDropdownOpen = false
            }, selectedCard = selectedCard, onDropdownToggle = {
                isDropdownOpen = it
            }, isDropdownOpen = isDropdownOpen
            )

            Spacer(modifier = Modifier.height(15.dp))
            SendToSection()
            Spacer(modifier = Modifier.height(20.dp))
            EnterNominalSection(selectedAmount = selectedAmount,
                onAmountChanged = {selectedAmount = it}
            )
            Spacer(modifier = Modifier.height(20.dp))
            AmountSelection(selectedAmount = selectedAmount, onAmountSelected = {
                selectedAmount = it
            })
        }
        Column {
            if(isInsufficientBalance){
                Text(text = "Insufficient Balance", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Button(onClick = {

                    if (selectedCard == null){
                        // Do nothing or you can add an error here
                        Log.d("SendScreen", "Please Select a card")
                    }
                    else if (selectedAmount > (selectedCard?.balance ?: 0.0)){
                        isInsufficientBalance = true
                    }else{
                        isInsufficientBalance = false
                        onNavigateToPaymentAuth(selectedAmount.toDouble(), selectedCard!! ) {
                            onSendSuccess(selectedAmount.toDouble(), selectedCard!!)
                        }
                    }
                },
                    modifier = Modifier.weight(1f)
                    ,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF303346))
                ) {
                    Text(text = "Proceed", color = Color.White)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    Log.d("SendScreen", "Cancel Button clicked")
                    onBack()
                },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)

                ) {
                    Text(text = "Cancel")
                }
            }

        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSelector(cardList: List<CardData>, onCardSelected: (CardData) -> Unit, selectedCard: CardData?, onDropdownToggle: (Boolean) -> Unit, isDropdownOpen: Boolean) {

    var expanded by remember { mutableStateOf(false) }
    Card(modifier = Modifier.fillMaxWidth()
        .clickable {
            expanded = !expanded
            onDropdownToggle(expanded)
        }
        .zIndex(if(isDropdownOpen) 1f else 0f),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if(selectedCard != null){
                    Card(
                        modifier = Modifier.size(50.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Box (modifier = Modifier.background(Color.Black)){
                            Text(text = selectedCard.cardType.take(3), color = Color.White ,modifier = Modifier.padding(5.dp))
                        }
                    }
                    Text(text = selectedCard.cardType, modifier = Modifier.padding(start = 10.dp))
                }else{
                    Card(
                        modifier = Modifier.size(50.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Box (modifier = Modifier.background(Color.Black)){
                            Text(text = "Card", color = Color.White ,modifier = Modifier.padding(5.dp))
                        }
                    }
                    Text(text = "Select Card", modifier = Modifier.padding(start = 10.dp))
                }
            }
            Text(text = "Rs. ${selectedCard?.balance?.toInt() ?: 0}", modifier = Modifier.padding(end = 5.dp), fontWeight = FontWeight.Bold)

        }
        AnimatedVisibility(
            visible = isDropdownOpen,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)) + slideInVertically(animationSpec = tween(durationMillis = 300), initialOffsetY = { -it }),
            exit = fadeOut(animationSpec = tween(durationMillis = 300)) + slideOutVertically(animationSpec = tween(durationMillis = 300), targetOffsetY = { -it })
        )
        {
            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
                onDropdownToggle(expanded)
            }) {
                cardList.forEach {
                    DropdownMenuItem(text = { Text(text = it.cardType) }, onClick = {
                        onCardSelected(it)
                        expanded = false
                        onDropdownToggle(expanded)
                    })
                }
            }
        }

    }
}



@Composable
fun SendToSection() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .shadow(elevation = 3.dp, shape = RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Send to", style = MaterialTheme.typography.titleMedium)
                Row (verticalAlignment = Alignment.CenterVertically){
                    Box(modifier = Modifier
                        .size(35.dp)
                        .background(
                            Color(0xFFD9D9D9),
                            shape = RoundedCornerShape(10.dp)
                        )
                    ) {
                        Text(text = "A", modifier = Modifier.align(Alignment.Center))
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column {
                        Text(text = "A.Chatterjee", fontWeight = FontWeight.Bold)
                        Text(text = "4444 3333 2222 1111", fontSize = 12.sp, color = Color.Gray)
                    }
                }

            }
            IconButton(onClick = {Log.d("SendScreen", "Edit User clicked")}) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
            }
        }
    }
}

@Composable
fun EnterNominalSection(selectedAmount: Float, onAmountChanged: (Float) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Set the nominal", color = Color.Gray, fontSize = 12.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if(selectedAmount > 0 ) onAmountChanged(selectedAmount - 1)
                }) {
                    Text(text = "-", style = MaterialTheme.typography.headlineMedium)
                }

                Text(text = "Rs. ${selectedAmount.toInt()}", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
                IconButton(onClick = {
                    onAmountChanged(selectedAmount + 1)
                }) {
                    Text(text = "+", style = MaterialTheme.typography.headlineMedium)
                }
            }
            Slider(
                value = selectedAmount,
                onValueChange = onAmountChanged,
                valueRange = 0f..100000f,
                // steps = 4,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun AmountSelection(selectedAmount: Float, onAmountSelected: (Float) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AmountItem("Rs. 1", onAmountSelected = onAmountSelected, selectedAmount = selectedAmount)
        AmountItem("Rs. 50", onAmountSelected = onAmountSelected, selectedAmount = selectedAmount)
        AmountItem("Rs. 100", onAmountSelected = onAmountSelected, selectedAmount = selectedAmount)
    }
    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        AmountItem("Rs. 150", onAmountSelected = onAmountSelected, selectedAmount = selectedAmount)
        AmountItem("Rs. 200", onAmountSelected = onAmountSelected, selectedAmount = selectedAmount)
        AmountItem("Rs. 250", onAmountSelected = onAmountSelected, selectedAmount = selectedAmount)
    }
}


@Composable
fun AmountItem(text: String, onAmountSelected: (Float) -> Unit, selectedAmount: Float) {
    val isSelected = text.substring(3).toFloat() == selectedAmount
    val color = if(isSelected) Color(0xFF303346) else Color.White
    val textColor = if(isSelected) Color.White else Color.Black
    Card(modifier = Modifier
        .clickable {
            onAmountSelected(text.substring(3).toFloat())
        },
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ){
        Text(text = text,
            modifier = Modifier.padding(8.dp),
            color = textColor,
            fontWeight = FontWeight.Bold)
    }
}