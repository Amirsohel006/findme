package com.findme.app.modules.five.`data`.model

import com.findme.app.R
import com.findme.app.appcomponents.di.MyApp
import kotlin.String

data class FiveModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtTakeSelfie: String? = MyApp.getInstance().resources.getString(R.string.lbl_take_selfie)

)
