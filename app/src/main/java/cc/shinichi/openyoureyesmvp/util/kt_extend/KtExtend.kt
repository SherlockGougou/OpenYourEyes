package cc.shinichi.openyoureyesmvp.util.kt_extend

import android.os.Build
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyesmvp.app.App

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyes.util.kt_extend
 * create at 2018/8/11  11:33
 * description:
 */

fun View.Gone() {
    this.visibility = View.GONE
}

fun View.Visible() {
    this.visibility = View.VISIBLE
}

fun TextView.setTextColorCompat(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.setTextColor(App.application.resources.getColor(color, App.application.theme))
    } else {
        this.setTextColor(App.application.resources.getColor(color))
    }
}