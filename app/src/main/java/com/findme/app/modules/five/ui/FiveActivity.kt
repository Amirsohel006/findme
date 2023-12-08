package com.findme.app.modules.five.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.findme.app.ImageResponse
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivityFiveBinding
import com.findme.app.modules.five.data.viewmodel.FiveVM
import com.findme.app.modules.six.ui.SixActivity
import com.findme.app.services.ApiInterface
import com.findme.app.services.ApiManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FiveActivity : BaseActivity<ActivityFiveBinding>(R.layout.activity_five) {

  private val viewModel: FiveVM by viewModels<FiveVM>()
 // private var cameraExecutor: ExecutorService? = null
  private var imageCapture: ImageCapture? = null
    private lateinit var apiService: ApiInterface



  private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()


  override fun onInitialized() {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.fiveVM = viewModel


      apiService=ApiManager.apiInterface


    if (requestCameraPermission()) {
        dispatchTakePictureIntent()
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

    }

    binding.btnArrowleft.setOnClickListener {
      finish()
    }
  }




    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Image capture successful
           // imageUri=data?.data!!
            val photoFile = createTempFile()


            //file = getFile(this, photoFile.toUri())

            showConfirmationDialog(photoFile)

           // Log.d("Profile_Photo", file.toString())
        } else {
            // Image capture failed or canceled
        }
    }



    private fun showConfirmationDialog(photoFile: File) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Image Confirmation")
        alertDialogBuilder.setMessage("Do you want to use this image?")
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            // User clicked OK, perform API call with the image
            uploadImageToApi(photoFile)
        }
        alertDialogBuilder.setNegativeButton("Retry") { dialog, which ->
            // User clicked Retry, reopen the camera to capture a new image
            dispatchTakePictureIntent()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun uploadImageToApi(photoFile: File) {
        // TODO: Implement your API call to upload the image here
        // Example using Retrofit
        val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), photoFile)
        val imagePart = MultipartBody.Part.createFormData("query_image", photoFile.name, requestBody)
        val eventName=intent.getStringExtra("event_name")
        val call =  apiService.signUp("Ewwvent1",imagePart)
        call.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {

                    val responseBody = response.body()
                    if (responseBody != null) {
                        Toast.makeText(this@FiveActivity, "Images Retrieved", Toast.LENGTH_SHORT).show()
                        Log.d("response_data", responseBody.toString())

                        // Pass the list of images to the next activity
                       // val imageList = responseBody.getImages() // Replace this with the actual method to get images from the response

                        // Start the next activity and pass the image list
//                        val intent = SixActivity.getIntent(this@FiveActivity,imageList)
//                        startActivity(intent)
//
//                        finishAffinity()
                    } else {
                        Toast.makeText(this@FiveActivity, "Images Retreived Failed", Toast.LENGTH_SHORT).show()

                    }
                }
                else {
                    Toast.makeText(this@FiveActivity, "Registration failed", Toast.LENGTH_SHORT).show()

                }
            }
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Toast.makeText(this@FiveActivity, "Registration failed: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d(t.message,"This fails in signup response")
            }
        })
        // Replace with your actual API call
        // val call: Call<YourResponseModel> = yourApiService.uploadImage(imagePart)
        // call.enqueue(object : Callback<YourResponseModel> { ... })
    }



    @Throws(IOException::class)
    fun getFile(context: Context, uri: Uri): File {
        val destinationFilename =
            File(context.filesDir.path + File.separatorChar + queryName(context, uri))
        try {
            context.contentResolver.openInputStream(uri).use { ins ->
                createFileFromStream(
                    ins!!,
                    destinationFilename
                )
            }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
        return destinationFilename
    }

    fun createFileFromStream(ins: InputStream, destination: File?) {
        try {
            FileOutputStream(destination).use { os ->
                val buffer = ByteArray(4096)
                var length: Int
                while (ins.read(buffer).also { length = it } > 0) {
                    os.write(buffer, 0, length)
                }
                os.flush()
            }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
    }

    private fun queryName(context: Context, uri: Uri): String {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
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

        // Create a file in the external files directory
        val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)

        // Get the URI using FileProvider
        val imageUri = FileProvider.getUriForFile(
            this,
            "com.ameersapplication.app.fileprovider",
            imageFile
        )


        return imageFile
    }


    override fun onPause() {
    super.onPause()
    cameraExecutor.shutdown()
    cameraExecutor = Executors.newSingleThreadExecutor() // Reinitialize if needed
  }
  companion object {
    const val TAG: String = "FIVE_ACTIVITY"
    private const val REQUEST_IMAGE_CAPTURE=10
      private const val CAMERA_PERMISSION_REQUEST_CODE=100
    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, FiveActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}

