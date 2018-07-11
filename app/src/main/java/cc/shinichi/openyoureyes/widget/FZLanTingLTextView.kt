package cc.shinichi.openyoureyes.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import cc.shinichi.openyoureyes.app.App

class FZLanTingLTextView : TextView {

  constructor(context: Context?) : super(context) {
    init()
  }

  constructor(
    context: Context?,
    attrs: AttributeSet?
  ) : super(context, attrs) {
    init()
  }

  constructor(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr) {
    init()
  }

  constructor(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
  ) : super(context, attrs, defStyleAttr, defStyleRes) {
    init()
  }

  private fun init() {
    val fonts =
      Typeface.createFromAsset(App.application.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    typeface = fonts
  }
}