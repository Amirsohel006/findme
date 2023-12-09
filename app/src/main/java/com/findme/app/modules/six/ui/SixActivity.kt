package com.findme.app.modules.six.ui

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.findme.app.R
import com.findme.app.appcomponents.base.BaseActivity
import com.findme.app.databinding.ActivitySixBinding
import com.findme.app.modules.five.ui.FiveActivity
import com.findme.app.modules.six.`data`.model.SixRowModel
import com.findme.app.modules.six.`data`.viewmodel.SixVM
import com.findme.app.responses.SimilarImage
import com.findme.app.responses.SimilarImages
import retrofit2.Callback
import kotlin.Int
import kotlin.String
import kotlin.Unit

class SixActivity : BaseActivity<ActivitySixBinding>(R.layout.activity_six) {
  private val viewModel: SixVM by viewModels<SixVM>()


//  private lateinit var recyclerView: RecyclerView
//  private lateinit var adapter: ImageAdapter


  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")


    val imageList = intent.getParcelableArrayListExtra<SimilarImages>("imageList")


    val recyclerView: RecyclerView = findViewById(R.id.recyclerSix)
    val adapter = imageList?.let { SixAdapter(it) }
    recyclerView.adapter = adapter
    recyclerView.layoutManager = GridLayoutManager(this,3)


    binding.sixVM = viewModel


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

//    fun getIntent(context: Callback<SimilarImage>, bundle: ArrayList<SimilarImages>): Intent {
//      val destIntent = Intent(context, FiveActivity::class.java)
//      destIntent.putExtra("bundle", bundle)
//      return destIntent
//    }

  }
}
