package com.akash.bank.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun AddCardScreen(onBack: () -> Unit, onCardAdded: (CardData) -> Unit) {
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
            CardForm(onCardAdded = onCardAdded)
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
            text = "Add a Card",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardForm(onCardAdded: (CardData) -> Unit) {
    var cardType by remember { mutableStateOf(TextFieldValue()) }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }
    var expiryDate by remember { mutableStateOf(TextFieldValue()) }
    var cvc by remember { mutableStateOf(TextFieldValue()) }
    var cardholderName by remember { mutableStateOf(TextFieldValue()) }
    var addressLine1 by remember { mutableStateOf(TextFieldValue()) }
    var addressLine2 by remember { mutableStateOf(TextFieldValue()) }
    var balance by remember{ mutableStateOf(TextFieldValue())}


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = cardType,
            onValueChange = { cardType = it },
            label = { Text("Card Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween){
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("Expired Date") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.weight(0.1f))
            OutlinedTextField(
                value = cvc,
                onValueChange = { cvc = it },
                label = { Text("CVC/CVV") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = cardholderName,
            onValueChange = { cardholderName = it },
            label = { Text("Cardholder Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = balance,
            onValueChange = { balance = it },
            label = { Text("Balance") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Billing Address", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = addressLine1,
            onValueChange = { addressLine1 = it },
            label = { Text("Address Line 1") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = addressLine2,
            onValueChange = { addressLine2 = it },
            label = { Text("Address Line 2") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            val card = CardData(
                cardType = cardType.text,
                cardNumber = cardNumber.text,
                expiry = expiryDate.text,
                cvc = cvc.text,
                cardholderName = cardholderName.text,
                addressLine1 = addressLine1.text,
                addressLine2 = addressLine2.text,
                balance = balance.text.toDoubleOrNull() ?: 0.0,
            )
            onCardAdded(card)
            Log.d("AddCardScreen", "Card added: $card")
        },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF303346)),

            ) {
            Text(text = "Save Card", color = Color.White)
        }
    }
}

data class CardData(
    val cardType:String,
    val cardNumber: String,
    val expiry: String,
    val cvc: String,
    val cardholderName: String,
    val addressLine1: String,
    val addressLine2: String,
    val balance: Double
)