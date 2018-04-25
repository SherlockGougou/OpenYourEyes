package cc.shinichi.openyoureyes.util

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import cc.shinichi.openyoureyes.app.AppManager
import cc.shinichi.openyoureyes.util.log.ALog

/*
* @author 工藤
* @emil gougou@16fan.com
* cc.shinichi.openyoureyes.util
* create at 2018/4/25  12:25
* description: 
*/
open class RomUtil {
    companion object {
        fun isMIUI(): Boolean {
            if (!TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name", ""))) {
                ALog.Log("RomUtil", "MIUI ROM")
                return true
            }
            return false
        }

        fun isFlyme(): Boolean {
            if (isInstalledByPkgName("com.meizu.flyme.update")) ALog.Log("RomUtil", "Flyme ROM")
            return isInstalledByPkgName("com.meizu.flyme.update")
        }

        private fun isInstalledByPkgName(pkgName: String): Boolean {
            var packageInfo: PackageInfo?
            try {
                packageInfo = AppManager.getInstance().currentActivity()!!.packageManager.getPackageInfo(pkgName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                packageInfo = null
            }
            return packageInfo != null
        }

        private fun getSystemProperty(key: String, defaultValue: String): String {
            try {
                val clz = Class.forName("android.os.SystemProperties")
                val get = clz.getMethod("get", String::class.java, String::class.java)
                return get.invoke(clz, key, defaultValue) as String
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return defaultValue
        }
    }
}