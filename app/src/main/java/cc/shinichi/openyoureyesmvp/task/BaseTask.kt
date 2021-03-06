package cc.shinichi.openyoureyesmvp.task

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import cc.shinichi.openyoureyesmvp.app.App
import cc.shinichi.openyoureyesmvp.constant.SpTag
import com.google.gson.Gson

open class BaseTask {

    companion object {

        val TAG: String = "TaskLog"

        private var gson: Gson? = null
        private var sp: SharedPreferences? = null

        fun isNull(string: String?): Boolean {
            if (TextUtils.isEmpty(string)) {
                return true
            }
            return false
        }

        fun getGson(): Gson {
            if (gson == null) {
                gson = Gson()
            }
            return gson as Gson
        }

        fun getSp(): SharedPreferences {
            if (sp == null) {
                sp = App.application.getSharedPreferences(SpTag.defaultSpName, Context.MODE_PRIVATE)
            }
            return sp as SharedPreferences
        }
    }
}