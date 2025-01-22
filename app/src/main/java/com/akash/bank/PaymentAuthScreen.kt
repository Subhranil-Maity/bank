package com.akash.bank.ui.paymentauth

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun PaymentAuthScreen(
    onBack: () -> Unit,
    amount: Double,
    cardData: com.akash.bank.ui.cards.CardData,
    onPaymentComplete: () -> Unit
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
            PaymentForm(amount = amount, cardData = cardData, onPaymentComplete = onPaymentComplete)
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
            text = "Proceed to Pay",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentForm(amount: Double, cardData: com.akash.bank.ui.cards.CardData,  onPaymentComplete: () -> Unit) {
    var pin by remember{ mutableStateOf(TextFieldValue("1234"))}
    var paymentDone by remember{ mutableStateOf(false)}
    var showIncorrectPin by remember{ mutableStateOf(false)}

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Please enter your 4 digit pin", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = pin, onValueChange = {
            if(it.text.length <=4)
                pin = it
        },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.6f)
        )
        if(showIncorrectPin){
            Text(text = "Incorrect Pin", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(25.dp))

        Button(onClick = {

            if(pin.text == "1234"){
                paymentDone = true
                showIncorrectPin = false
                onPaymentComplete()
            }else{
                paymentDone = false
                showIncorrectPin = true
                Log.d("PaymentAuthScreen", "Incorrect pin")
            }
        },
            modifier = Modifier.fillMaxWidth(0.6f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF303346)),

            ) {
            Text(text = "Confirm Payment", color = Color.White)
        }

        if(paymentDone){
            Text(text = "Payment has been done successfully!", color = Color.Green)
        }
        Spacer(modifier = Modifier.height(10.dp))
        if(paymentDone){
            Card(modifier = Modifier
                .fillMaxWidth(0.6f)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)){
                    Text(text = "Payment of Rs. ${amount.toInt()} has been processed", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp), modifier = Modifier.padding(bottom = 5.dp))
                    Text(text = "From ${cardData.cardType}", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }

}