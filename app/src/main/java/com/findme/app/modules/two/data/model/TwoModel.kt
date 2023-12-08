package com.findme.app.modules.two.`data`.model

import com.findme.app.R
import com.findme.app.appcomponents.di.MyApp
import kotlin.String

data class TwoModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.lbl_rohit)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtWeds: String? = MyApp.getInstance().resources.getString(R.string.lbl_weds)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtRashmi: String? = MyApp.getInstance().resources.getString(R.string.lbl_rashmi)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtContinue: String? = MyApp.getInstance().resources.getString(R.string.lbl_continue)

)
