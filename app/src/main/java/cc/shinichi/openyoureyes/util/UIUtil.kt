package cc.shinichi.openyoureyes.util

import android.content.Context
import android.graphics.Point
import android.text.TextUtils
import android.view.WindowManager
import cc.shinichi.openyoureyes.app.App

object UIUtil {

  fun getAppContext(): Context {
    return App
        .application
  }

  fun getPhoneWidth(): Int {
    val wm: WindowManager =
      getAppContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outSize = Point()
    wm
        .defaultDisplay
        .getSize(outSize)
    return outSize
        .x
  }

  fun getPhoneHeight(): Int {
    val wm: WindowManager =
      getAppContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outSize = Point()
    wm
        .defaultDisplay
        .getSize(outSize)
    return outSize
        .y
  }

  fun dp2px(): Float {
    return 1f
  }

  fun px2dp(): Float {
    return 1f
  }

  fun isNull(string: String?): Boolean {
    if (TextUtils.isEmpty(string)) {
      return true
    }
    return false
  }
}