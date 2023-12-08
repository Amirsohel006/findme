package com.findme.app.modules.three.ui

import androidx.activity.viewModels
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivityThreeBinding
import com.findme.app.modules.four.ui.FourActivity
import com.findme.app.modules.three.`data`.viewmodel.ThreeVM
import kotlin.String
import kotlin.Unit

class ThreeActivity : BaseActivity<ActivityThreeBinding>(R.layout.activity_three) {
  private val viewModel: ThreeVM by viewModels<ThreeVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.threeVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.linearColumnregister.setOnClickListener {
      val destIntent = FourActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "THREE_ACTIVITY"

  }
}
