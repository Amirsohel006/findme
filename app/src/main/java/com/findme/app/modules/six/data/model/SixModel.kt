package com.findme.app.modules.six.`data`.model

import com.findme.app.R
import com.findme.app.appcomponents.di.MyApp
import kotlin.String

data class SixModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtYourPhotos: String? = MyApp.getInstance().resources.getString(R.string.lbl_your_photos)

)
