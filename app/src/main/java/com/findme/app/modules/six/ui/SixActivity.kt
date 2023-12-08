package com.findme.app.modules.six.ui

import android.view.View
import androidx.activity.viewModels
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivitySixBinding
import com.findme.app.modules.six.`data`.model.SixRowModel
import com.findme.app.modules.six.`data`.viewmodel.SixVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class SixActivity : BaseActivity<ActivitySixBinding>(R.layout.activity_six) {
  private val viewModel: SixVM by viewModels<SixVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val sixAdapter = SixAdapter(viewModel.sixList.value?:mutableListOf())
    binding.recyclerSix.adapter = sixAdapter
    sixAdapter.setOnItemClickListener(
    object : SixAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : SixRowModel) {
        onClickRecyclerSix(view, position, item)
      }
    }
    )
    viewModel.sixList.observe(this) {
      sixAdapter.updateData(it)
    }
    binding.sixVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnScan.setOnClickListener {
      val destIntent = OneActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  fun onClickRecyclerSix(
    view: View,
    position: Int,
    item: SixRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "SIX_ACTIVITY"

  }
}
