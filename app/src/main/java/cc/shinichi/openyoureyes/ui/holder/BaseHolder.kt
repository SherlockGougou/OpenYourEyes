package cc.shinichi.openyoureyes.ui.holder

import android.text.TextUtils
import android.view.View

open class BaseHolder {

  fun View.Gone() {
    this.visibility = View.GONE
  }

  fun View.Visible() {
    this.visibility = View.VISIBLE
  }

  fun isNull(string: String?): Boolean {
    if (TextUtils.isEmpty(string) || "null" == string || " " == string) {
      return true
    }
    return false
  }
}