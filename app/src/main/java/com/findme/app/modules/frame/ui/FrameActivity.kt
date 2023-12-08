package com.findme.app.modules.frame.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivityFrameBinding
import com.findme.app.modules.frame.`data`.model.ListrectangleThreeRowModel
import com.findme.app.modules.frame.`data`.viewmodel.FrameVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class FrameActivity : BaseActivity<ActivityFrameBinding>(R.layout.activity_frame) {
  private val viewModel: FrameVM by viewModels<FrameVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val listrectangleThreeAdapter =
    ListrectangleThreeAdapter(viewModel.listrectangleThreeList.value?:mutableListOf())
    binding.recyclerListrectangleThree.adapter = listrectangleThreeAdapter
    listrectangleThreeAdapter.setOnItemClickListener(
    object : ListrectangleThreeAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : ListrectangleThreeRowModel) {
        onClickRecyclerListrectangleThree(view, position, item)
      }
    }
    )
    viewModel.listrectangleThreeList.observe(this) {
      listrectangleThreeAdapter.updateData(it)
    }
    binding.frameVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  fun onClickRecyclerListrectangleThree(
    view: View,
    position: Int,
    item: ListrectangleThreeRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "FRAME_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, FrameActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
