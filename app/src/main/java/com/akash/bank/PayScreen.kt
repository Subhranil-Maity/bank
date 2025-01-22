package com.akash.bank.ui.pay

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Button
import androidx.compose.material3.Text as Text1

@Composable
fun PayScreen(onBack: () -> Unit) {
    val gradientColors = listOf(
        Color(0xFFF0F0F0),
        Color(0xFFE0E0E0)
    )
    var currentScreen by remember { mutableStateOf<PayScreenState>(PayScreenState.Options) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(gradientColors)),
        color = Color.Transparent
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            TopBar(onBack = onBack)

            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    if (targetState == PayScreenState.Qr){
                        (slideInHorizontally(initialOffsetX = { it }) + fadeIn(animationSpec = tween(durationMillis = 300)))
                            .togetherWith(slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(durationMillis = 200)))
                    } else if (targetState == PayScreenState.MyQr){
                        (slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(durationMillis = 300)))
                            .togetherWith(slideOutHorizontally(targetOffsetX = { it }) + fadeOut(animationSpec = tween(durationMillis = 200)))
                    }
                    else{
                        fadeIn(animationSpec = tween(durationMillis = 300))
                            .togetherWith(fadeOut(animationSpec = tween(durationMillis = 200)))
                    }
                }, label = "Payment Method"
            ) { targetState ->
                when (targetState) {
                    PayScreenState.Options ->  PaymentOptionScreen(onMyQrClick = {currentScreen = PayScreenState.MyQr}, onScanQrClick = {currentScreen = PayScreenState.Qr})
                    PayScreenState.MyQr -> MyQrScreen()
                    PayScreenState.Qr -> QrCodeScreen(onBack = {currentScreen = PayScreenState.Options})
                }

            }
        }
    }
}
enum class PayScreenState{
    Options,
    Qr,
    MyQr
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
        Text1(
            text = "Pay Screen",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun PaymentOptionScreen(onScanQrClick: () -> Unit, onMyQrClick: () -> Unit){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(modifier = Modifier.fillMaxWidth(0.7f)
            .clickable {
                onScanQrClick()
            }
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), contentAlignment = Alignment.Center){
                Text1(text = "Scan QR", style = MaterialTheme.typography.titleMedium)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(modifier = Modifier.fillMaxWidth(0.7f)
            .clickable {
                onMyQrClick()
            }
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), contentAlignment = Alignment.Center){
                Text1(text = "My QR", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun MyQrScreen(){
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Text1(text = "My QR will be placed here", style = MaterialTheme.typography.headlineMedium)
    }
}
@Composable
fun QrCodeScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var scannedValue by remember { mutableStateOf("") }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
        }
    )

    LaunchedEffect(key1 = Unit) {
        if (!hasCameraPermission) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (hasCameraPermission) {
            CameraPreview(
                lifecycleOwner = lifecycleOwner,
                onQrCodeScanned = { scannedValue = it }
            )
            if (scannedValue.isNotEmpty()) {
                Text1(text = "Scan Result: $scannedValue", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    onBack()
                }) {
                    Text1(text = "Go Back to Options")
                }
            }

        } else {
            Text1(text = "Camera permission denied")
        }
    }
}
@Composable
fun CameraPreview(
    lifecycleOwner: LifecycleOwner,
    onQrCodeScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val cameraPreview = remember { PreviewView(context) }
    AndroidView(
        factory = { cameraPreview },
        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp)),
        update = { previewView ->
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val imageAnalysis = ImageAnalysis.Builder()
                    .build()
                    .also { imageAnalysis ->
                        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context), QrAnalyzer {result ->
                            onQrCodeScanned(result)
                        } )

                    }
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, ContextCompat.getMainExecutor(context))


        }
    )
}


class QrAnalyzer(private val onQrCodeScanned: (String) -> Unit) : ImageAnalysis.Analyzer {

    private val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )
            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.rawValue?.let {
                            onQrCodeScanned(it)
                        }
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}