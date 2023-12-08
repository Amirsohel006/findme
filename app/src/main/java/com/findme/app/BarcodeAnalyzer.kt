import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.findme.app.QRCodeScanCallback
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage



@ExperimentalGetImage
class BarcodeAnalyzer(private val callback: QRCodeScanCallback) : ImageAnalysis.Analyzer {
    private val barcodeScanner = BarcodeScanning.getClient()

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        if (barcode.valueType == Barcode.TYPE_TEXT) {
                            val qrCodeData = barcode.displayValue ?: ""
                            // Pass the QR code data to the callback
                            callback.onQRCodeScanned(qrCodeData)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    // Handle the error
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}
