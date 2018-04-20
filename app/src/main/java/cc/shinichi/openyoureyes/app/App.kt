package cc.shinichi.openyoureyes.app

import android.app.Application
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.tencent.bugly.crashreport.CrashReport
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit.SECONDS

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/23  14:57
* description: application
*/
class App : Application() {

    companion object {
        lateinit var application: App
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        // bugly
        CrashReport.initCrashReport(application, "7d0d52dec2", false);
        // okgo
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(5, SECONDS)
        builder.writeTimeout(10, SECONDS)
        builder.readTimeout(10, SECONDS)
        OkGo.getInstance()
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .setRetryCount(2)
                .setOkHttpClient(builder.build())
                .init(application)
    }
}