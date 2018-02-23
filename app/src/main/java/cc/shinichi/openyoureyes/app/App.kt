package cc.shinichi.openyoureyes.app

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/23  14:57
* description: application
*/
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CrashReport.initCrashReport(applicationContext, "7d0d52dec2", false);
    }
}