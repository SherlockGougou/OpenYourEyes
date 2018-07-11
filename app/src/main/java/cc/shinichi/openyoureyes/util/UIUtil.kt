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

  fun px2dp(pxValue: Int): Int {
    val scale = App.application
        .resources
        .displayMetrics
        .density
    return (pxValue / scale + 0.5f).toInt()
  }

  fun dp2px(dipValue: Int): Int {
    val scale = App.application
        .resources
        .displayMetrics
        .density
    return (dipValue * scale + 0.5f).toInt()
  }

  fun isNull(string: String?): Boolean {
    if (TextUtils.isEmpty(string)) {
      return true
    }
    return false
  }

  fun getDurationText(duration: Int?): String {
    if (duration == null) {
      return ""
    }
    var 分钟: String = ""
    var 秒钟: String = ""
    分钟 = (duration / 60).toString()
    秒钟 = (duration % 60).toString()
    return 分钟 + ":" + 秒钟
  }
}