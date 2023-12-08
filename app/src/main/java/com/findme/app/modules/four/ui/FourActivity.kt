package com.findme.app.modules.four.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivityFourBinding
import com.findme.app.modules.five.ui.FiveActivity
import com.findme.app.modules.four.`data`.viewmodel.FourVM
import kotlin.String
import kotlin.Unit

class FourActivity : BaseActivity<ActivityFourBinding>(R.layout.activity_four) {
  private val viewModel: FourVM by viewModels<FourVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.fourVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnConfirm.setOnClickListener {
      val destIntent = FiveActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "FOUR_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, FourActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
