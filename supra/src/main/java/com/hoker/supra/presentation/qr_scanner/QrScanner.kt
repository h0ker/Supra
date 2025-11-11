package com.hoker.supra.presentation.qr_scanner

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QrScanner(
    modifier: Modifier = Modifier,
    codeFoundAction: (String) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val selector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }

    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()

    var isDebouncing = false
    fun startDebounce() {
        isDebouncing = true
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            isDebouncing = false
        }
    }

    val qrCodeAnalyzer = QrCodeAnalyzer { result ->
        result?.let {
            if (!isDebouncing) {
                codeFoundAction(it)
                startDebounce()
            }
        }
    }

    imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context), qrCodeAnalyzer)

    DisposableEffect(lensFacing) {
        var cameraProvider: ProcessCameraProvider? = null
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
                imageAnalysis
            )

            preview.surfaceProvider = previewView.surfaceProvider
        }, ContextCompat.getMainExecutor(context))

        onDispose {
            cameraProvider?.unbindAll()
        }
    }

    AndroidView({ previewView }, modifier = modifier)
}