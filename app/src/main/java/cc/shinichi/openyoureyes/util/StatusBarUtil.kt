package cc.shinichi.openyoureyes.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import cc.shinichi.openyoureyes.util.log.ALog

/*
* @author 工藤
* @emil gougou@16fan.com
* cc.shinichi.openyoureyes.util
* create at 2018/4/25  11:45
* description: 
*/
open class StatusBarUtil {
    companion object {
        fun setDarkStatusBar(activity: Activity, needDarkText: Boolean) {
            if (RomUtil.isFlyme()) {
                FlymeStatusLight(activity, needDarkText)
            } else if (RomUtil.isMIUI()) {
                MIUIStatusLight(activity, needDarkText)
            }
        }

        private fun FlymeStatusLight(activity: Activity, needDarkText: Boolean) {
            try {
                val window = activity.window
                val layoutParams: WindowManager.LayoutParams = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(layoutParams)
                value = if (needDarkText) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(bit, value)
                window.attributes = layoutParams
            } catch (e: Exception) {
                ALog.Log("FlymeStatusLight", e.toString())
            }
        }

        private fun MIUIStatusLight(activity: Activity, needDarkText: Boolean) {
            try {
                val window = activity.window
                var darkModeFlag = 0
                val clz = window::class.java
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFieldMethod = clz.getMethod("setExtraFlags", Int::class.java, Int::class.java)
                if (needDarkText) {
                    extraFieldMethod.invoke(window, darkModeFlag, darkModeFlag)// 状态栏透明且黑色字体
                } else {
                    extraFieldMethod.invoke(window, 0, darkModeFlag)// 清除黑色字体
                }
                // MIUI 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                if (Build.VERSION.SDK_INT >= 23) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                }
            } catch (e: Exception) {
                ALog.Log("MIUIStatusLight", e.toString())
            }
        }
    }

}