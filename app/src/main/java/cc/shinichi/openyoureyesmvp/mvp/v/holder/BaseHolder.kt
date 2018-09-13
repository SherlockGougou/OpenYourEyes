package cc.shinichi.openyoureyesmvp.mvp.v.holder

import android.text.TextUtils

open class BaseHolder {

    fun isNull(string: String?): Boolean {
        if (TextUtils.isEmpty(string) || "null" == string || " " == string) {
            return true
        }
        return false
    }
}