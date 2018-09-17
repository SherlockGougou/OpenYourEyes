package cc.shinichi.openyoureyesmvp.module.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.app.App
import cc.shinichi.openyoureyesmvp.app.AppManager
import cc.shinichi.openyoureyesmvp.constant.SpTag
import cc.shinichi.openyoureyesmvp.util.StatusBarUtil
import com.google.gson.Gson

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/24  15:31
 * description: 基activity
 */
abstract class BaseActivity : AppCompatActivity() {

    open val TAG: String = javaClass.simpleName
    private var gson: Gson? = null
    private var sp: SharedPreferences? = null

    abstract fun initView()
    abstract fun initUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)
        StatusBarUtil.setStatusBarColor(this, R.color.colorPrimary, R.color.colorPrimary)
        AppManager
                .getInstance()
                .addActivity(this)
    }

    override fun onDestroy() {
        super
                .onDestroy()
        AppManager
                .getInstance()
                .killActivity(this)
    }

    open fun isNull(string: String?): Boolean {
        if (TextUtils.isEmpty(string) || "null" == string || " " == string) {
            return true
        }
        return false
    }

    open fun getGson(): Gson {
        if (gson == null) {
            gson = Gson()
        }
        return gson as Gson
    }

    open fun getSp(): SharedPreferences {
        if (sp == null) {
            sp = App.application.getSharedPreferences(SpTag.defaultSpName, Context.MODE_PRIVATE)
        }
        return sp as SharedPreferences
    }
}