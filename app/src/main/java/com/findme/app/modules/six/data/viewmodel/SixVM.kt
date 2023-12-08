package com.findme.app.modules.six.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.findme.app.modules.six.`data`.model.SixModel
import com.findme.app.modules.six.`data`.model.SixRowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class SixVM : ViewModel(), KoinComponent {
  val sixModel: MutableLiveData<SixModel> = MutableLiveData(SixModel())

  var navArguments: Bundle? = null

  val sixList: MutableLiveData<MutableList<SixRowModel>> = MutableLiveData(mutableListOf())
}
