package cc.shinichi.openyoureyesmvp.app

import android.app.Application
import android.graphics.Typeface
import cc.shinichi.openyoureyesmvp.constant.Config
import com.facebook.drawee.backends.pipeline.Fresco
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.lzy.okgo.interceptor.HttpLoggingInterceptor.Level.BASIC
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit.SECONDS
import java.util.logging.Level

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/23  14:57
 * description: application
 */
class App : Application() {

    private lateinit var cB: Typeface
    private lateinit var cM: Typeface
    private lateinit var fD: Typeface
    private lateinit var fL: Typeface
    private lateinit var lB: Typeface

    companion object {
        lateinit var application: App
    }

    override fun onCreate() {
        super
                .onCreate()
        application = this

        // fresco
        Fresco.initialize(application)

        // okgo
        val builder = OkHttpClient
                .Builder()
        builder
                .connectTimeout(5, SECONDS)
        builder
                .writeTimeout(10, SECONDS)
        builder
                .readTimeout(10, SECONDS)
        val httpLog = HttpLoggingInterceptor("shinichi")
        httpLog.setColorLevel(Level.INFO)
        httpLog.setPrintLevel(BASIC)
        if (Config.isPrintLog) {
            builder.addInterceptor(httpLog)
        }
        OkGo
                .getInstance()
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .setCacheTime(3600000 * 2) // 缓存2小时
                .setRetryCount(2)
                .setOkHttpClient(builder.build())
                .init(application)

        cB = Typeface.createFromAsset(App.application.assets, "fonts/DIN-Condensed-Bold.ttf")
        cM = Typeface.createFromAsset(App.application.assets, "fonts/Futura-CondensedMedium.ttf")
        fD = Typeface.createFromAsset(App.application.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        fL = Typeface.createFromAsset(App.application.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        lB = Typeface.createFromAsset(App.application.assets, "fonts/Lobster-1.4.otf")
    }

    fun getCondensedBoldFont(): Typeface {
        return cB
    }

    fun getCondensedMediumFont(): Typeface {
        return cM
    }

    fun getFZLTDBFont(): Typeface {
        return fD
    }

    fun getFZLTLightFont(): Typeface {
        return fL
    }

    fun getLobsterFont(): Typeface {
        return lB
    }
}