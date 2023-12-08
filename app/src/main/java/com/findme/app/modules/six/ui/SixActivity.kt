package com.findme.app.modules.six.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivitySixBinding
import com.findme.app.modules.five.ui.FiveActivity
import com.findme.app.modules.six.`data`.model.SixRowModel
import com.findme.app.modules.six.`data`.viewmodel.SixVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class SixActivity : BaseActivity<ActivitySixBinding>(R.layout.activity_six) {
  private val viewModel: SixVM by viewModels<SixVM>()


//  private lateinit var recyclerView: RecyclerView
//  private lateinit var adapter: ImageAdapter


  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
  //  val sixAdapter = SixAdapter(viewModel.sixList.value?:mutableListOf())


//    recyclerView = findViewById(R.id.recyclerView)
//    recyclerView.layoutManager = LinearLayoutManager(this)
//
//    // Get the list of images from the intent
//    val imageList = intent.getSerializableExtra("imageList") as ArrayList<String>
//
//    // Initialize and set up the adapter
//    adapter = ImageAdapter(imageList)
//    recyclerView.adapter = adapter

//    binding.recyclerSix.adapter = sixAdapter
//    sixAdapter.setOnItemClickListener(
//    object : SixAdapter.OnItemClickListener {
//      override fun onItemClick(view:View, position:Int, item : SixRowModel) {
//        onClickRecyclerSix(view, position, item)
//      }
//    }
//    )
//    viewModel.sixList.observe(this) {
//      sixAdapter.updateData(it)
//    }
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

    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, SixActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
