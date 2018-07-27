package cc.shinichi.openyoureyes.ui.holder

import android.view.View

open class BaseHolder {

  fun View.Gone() {
    this.visibility = View.GONE
  }

  fun View.Visible() {
    this.visibility = View.VISIBLE
  }
}