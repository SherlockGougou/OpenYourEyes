package cc.shinichi.openyoureyes.ui.activity

import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.api.Constant
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/24  15:54
* description: 欢迎界面
*/class Welcome : BaseActivity() {

    private val handler: Handler = Handler()
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    override fun onStart() {
        super.onStart()
        AndPermission.with(this)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted {
                    init()
                }
                .onDenied {
                    byeBye()
                }
                .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除所有消息
        handler.removeCallbacksAndMessages(null)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun init() {
        imageView = findViewById(R.id.img_day_picture)
        // 加载每日美图，动画时长2秒
        ImageLoader.load(this, Constant.每日图片, imageView, true)
        // 延时3秒打开主页
        handler.postDelayed({
            runOnUiThread({
                IntentUtil.intent2Home(this)
                finish()
            })
        }, 3000)
    }

    private fun byeBye() {
        toast("您™必须同意！拜拜了您呐~")
        finish()
    }
}