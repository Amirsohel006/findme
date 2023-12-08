package com.findme.app.modules.three.`data`.model

import com.findme.app.R
import com.findme.app.appcomponents.di.MyApp
import kotlin.String

data class ThreeModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtRegister: String? = MyApp.getInstance().resources.getString(R.string.lbl_register)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etGroup550Value: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroup551Value: String? = null
)
