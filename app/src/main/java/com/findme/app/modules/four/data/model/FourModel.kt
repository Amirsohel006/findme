package com.findme.app.modules.four.`data`.model

import com.findme.app.R
import com.findme.app.appcomponents.di.MyApp
import kotlin.String

data class FourModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtVerifyMobileN: String? =
      MyApp.getInstance().resources.getString(R.string.msg_verify_mobile_n)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtPleaseenterth: String? =
      MyApp.getInstance().resources.getString(R.string.msg_please_enter_th)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg_91_71)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEnterOTP: String? = MyApp.getInstance().resources.getString(R.string.lbl_enter_otp)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtResendOTP: String? = MyApp.getInstance().resources.getString(R.string.lbl_resend_otp)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtGoBack: String? = MyApp.getInstance().resources.getString(R.string.lbl_go_back)

)
