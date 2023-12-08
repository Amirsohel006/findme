package com.findme.app.modules.frame.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.findme.app.modules.frame.`data`.model.FrameModel
import com.findme.app.modules.frame.`data`.model.ListrectangleThreeRowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class FrameVM : ViewModel(), KoinComponent {
  val frameModel: MutableLiveData<FrameModel> = MutableLiveData(FrameModel())

  var navArguments: Bundle? = null

  val listrectangleThreeList: MutableLiveData<MutableList<ListrectangleThreeRowModel>> =
      MutableLiveData(mutableListOf())
}
