package cc.shinichi.openyoureyesmvp.mvp.v.widget

import android.content.Context
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.widget.TextView
import cc.shinichi.openyoureyesmvp.app.App

class LobsterTextView : TextView {

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

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
//    val fonts = Typeface.createFromAsset(App.application.assets, "fonts/Lobster-1.4.otf")
//    typeface = fonts

        typeface = App.application.getLobsterFont()
    }
}