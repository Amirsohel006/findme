package com.findme.app.modules.five.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivityFiveBinding
import com.findme.app.modules.five.`data`.viewmodel.FiveVM
import com.findme.app.modules.frame.ui.FrameActivity
import com.findme.app.modules.one.ui.PreviewActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FiveActivity : BaseActivity<ActivityFiveBinding>(R.layout.activity_five) {

  private val viewModel: FiveVM by viewModels<FiveVM>()
 // private var cameraExecutor: ExecutorService? = null
  private var imageCapture: ImageCapture? = null


  private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()


  override fun onInitialized() {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.fiveVM = viewModel


    if (requestCameraPermission()) {
      startCameraPreview()
    } else {
      ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.CAMERA),
        FiveActivity.CAMERA_PERMISSION_REQUEST_CODE
      )
    }
  }

  override fun setUpClicks() {
    binding.viewEllipseNine.setOnClickListener {
      // Capture a picture when the button is clicked
      capturePicture()
    }

    binding.btnArrowleft.setOnClickListener {
      finish()
    }
  }


  private fun startCameraPreview() {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

    cameraProviderFuture.addListener({
      val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

      // Set up the camera preview and image capture use cases
      val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
      val preview = Preview.Builder().build().also {
        it.setSurfaceProvider(binding.viewRectangleEleven.surfaceProvider)
      }
        imageCapture = ImageCapture.Builder()
            .setFlashMode(ImageCapture.FLASH_MODE_AUTO) // Set the desired flash mode
            .build()


        try {
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
      } catch (exc: Exception) {
        // Handle exceptions
      }
    }, ContextCompat.getMainExecutor(this))
  }

  private fun capturePicture() {
    val photoFile = createTempFile()

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture?.takePicture(
      outputOptions,
      ContextCompat.getMainExecutor(this),
      object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
          // Handle the saved image file if needed
        }

        override fun onError(exception: ImageCaptureException) {
          // Handle the error
        }
      }
    )
  }



  private fun requestCameraPermission(): Boolean {
    if (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.CAMERA),
        CAMERA_PERMISSION_REQUEST_CODE
      )
      return false
    } else {
      // Permission already granted
      return true
    }
  }



  private fun createTempFile(): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
  }

  override fun onPause() {
    super.onPause()
    cameraExecutor.shutdown()
    cameraExecutor = Executors.newSingleThreadExecutor() // Reinitialize if needed
  }
  companion object {
    const val TAG: String = "FIVE_ACTIVITY"
    private const val CAMERA_PERMISSION_REQUEST_CODE=10
    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, FiveActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}

