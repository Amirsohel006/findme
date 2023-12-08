import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivityOneBinding
import com.findme.app.modules.one.data.viewmodel.OneVM


class OneActivity : BaseActivity<ActivityOneBinding>(R.layout.activity_one) {
  private val viewModel: OneVM by viewModels()

  override fun onInitialized() {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.oneVM = viewModel

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

  private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
    this, Manifest.permission.CAMERA
  ) == PackageManager.PERMISSION_GRANTED


  private fun startCameraForQR() {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

    cameraProviderFuture.addListener({
      val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

      val preview = Preview.Builder().build().also {
        it.setSurfaceProvider(binding.previewView.surfaceProvider)
      }

      val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

      try {
        cameraProvider.unbindAll()

//        val imageAnalyzer = ImageAnalysis.Builder()
//          .setTargetAspectRatio(AspectRatio.RATIO_16_9)
//          .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//          .build()
//
//        imageAnalyzer.setAnalyzer(ContextCompat.getMainExecutor(this), BarcodeAnalyzer(this))

      //  cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
      } catch (exc: Exception) {
        // Handle exceptions
      }
    }, ContextCompat.getMainExecutor(this))
  }

  override fun setUpClicks() {

  }



  companion object {
    const val TAG: String = "ONE_ACTIVITY"
    private const val REQUEST_CODE_PERMISSIONS = 10

    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, OneActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
