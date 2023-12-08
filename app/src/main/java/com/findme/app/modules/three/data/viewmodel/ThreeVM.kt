package com.findme.app.modules.three.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.findme.app.modules.three.`data`.model.ThreeModel
import org.koin.core.KoinComponent

class ThreeVM : ViewModel(), KoinComponent {
  val threeModel: MutableLiveData<ThreeModel> = MutableLiveData(ThreeModel())

  var navArguments: Bundle? = null
}
