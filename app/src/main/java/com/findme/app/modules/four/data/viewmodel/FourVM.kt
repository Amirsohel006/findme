package com.findme.app.modules.four.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.findme.app.modules.four.`data`.model.FourModel
import org.koin.core.KoinComponent

class FourVM : ViewModel(), KoinComponent {
  val fourModel: MutableLiveData<FourModel> = MutableLiveData(FourModel())

  var navArguments: Bundle? = null
}
