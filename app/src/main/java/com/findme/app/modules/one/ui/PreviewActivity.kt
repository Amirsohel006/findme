package com.findme.app.modules.one.ui

import BarcodeAnalyzer
import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.findme.app.QRCodeScanCallback
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivityPreviewBinding
import com.findme.app.modules.five.ui.FiveActivity
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PreviewActivity : BaseActivity<ActivityPreviewBinding>(R.layout.activity_preview) , QRCodeScanCallback {


    private var cameraExecutor: ExecutorService? = null
    private lateinit var preview: PreviewView

    private lateinit var scanner: BarcodeScanner
//    @OptIn(ExperimentalGetImage::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_preview)
//
//
//        val options = BarcodeScannerOptions.Builder()
//            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
//            .build()
//
//        scanner = BarcodeScanning.getClient(options)
//        preview=findViewById(R.id.previewView)
//
//
//        if (allPermissionsGranted()) {
//            startCameraForQR()
//        } else {
//            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
//        }
//        cameraExecutor = Executors.newSingleThreadExecutor()
//
//
//
//
//
//    }

   @OptIn(ExperimentalGetImage::class) override fun onInitialized() {
        super.onInitialized()
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        scanner = BarcodeScanning.getClient(options)
       preview=findViewById(R.id.previewView)

        // Request camera permission
        if (allPermissionsGranted()) {
            startCameraForQR()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_PERMISSIONS
            )
        }

    }





    @ExperimentalGetImage
    private fun startCameraForQR() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(preview.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                val imageAnalyzer = ImageAnalysis.Builder()
                    .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalyzer.setAnalyzer(ContextCompat.getMainExecutor(this), BarcodeAnalyzer(this))

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
            } catch (exc: Exception) {
                // Handle exceptions
            }
        }, ContextCompat.getMainExecutor(this))
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onQRCodeScanned(qrCodeData: String) {
        runOnUiThread {
            showQRCodeDataDialog(qrCodeData)

            moveToValidActivity()
        }
    }


    override fun onBackPressed() {

        showExitConfirmationDialog()
    }

    override fun setUpClicks() {
       binding.previewView.setOnClickListener{

       }
    }

    fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                finishAffinity()
            }
            .setNegativeButton("No", null)
            .show()
    }



    private fun showQRCodeDataDialog(qrCodeData: String) {
        // Create an AlertDialog to show the QR code data
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("QR Code Scanned")
            .setMessage("Scanned QR Code Data: $qrCodeData")
            .setPositiveButton("OK") { dialog, _ ->
                // Close the dialog if the user clicks "OK"
                dialog.dismiss()
            }
            .create()

        // Show the dialog
        alertDialog.show()
    }



    private fun moveToValidActivity() {
        val i=Intent(this,FiveActivity::class.java)
        startActivity(i)
        finish()
    }

    companion object {
        private const val TAG = "PreviewActivity"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}