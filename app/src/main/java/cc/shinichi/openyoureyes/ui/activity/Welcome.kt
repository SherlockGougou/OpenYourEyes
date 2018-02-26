package cc.shinichi.openyoureyes.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.api.Constant
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.qmuiteam.qmui.widget.dialog.QMUIDialog.MessageDialogBuilder
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yanzhenjie.permission.Rationale

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/24  15:54
* description: 欢迎界面
*/class Welcome : BaseActivity() {

    private lateinit var context: Context
    private val handler: Handler = Handler()
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        context = this
    }

    override fun onStart() {
        AndPermission.with(context)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .rationale(mRationale)
                .onGranted { permissions ->
                    init()
                }
                .onDenied { permissions ->
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        // 这些权限被用户总是拒绝。
                        // 这里使用一个Dialog询问用户是否继续授权。
                        val dialog: MessageDialogBuilder = MessageDialogBuilder(context)
                        val settingService = AndPermission.permissionSetting(context)
                        dialog.setTitle("注意")
                                .setMessage("您拒绝了存储权限，App将无法正常使用，是否重新授权？")
                                .addAction("取消", QMUIDialogAction.ActionListener { d, index ->
                                    d.dismiss()
                                    // 如果用户不同意去设置：
                                    settingService.cancel();
                                })
                                .addAction("确定", QMUIDialogAction.ActionListener { d, index ->
                                    d.dismiss()
                                    // 如果用户同意去设置：
                                    settingService.execute();
                                })
                                .show()
                    }
                }
                .start()
        super.onStart()
    }

    private val mRationale = Rationale { context, permissions, executor ->
        // 这里使用一个Dialog询问用户是否继续授权。
        val dialog: MessageDialogBuilder = MessageDialogBuilder(context)
        val settingService = AndPermission.permissionSetting(context)
        dialog.setTitle("注意")
                .setMessage("您拒绝了存储权限，App将无法正常使用，是否重新授权？")
                .addAction("取消", QMUIDialogAction.ActionListener { d, index ->
                    d.dismiss()
                    // 如果用户中断：
                    executor.cancel()
                })
                .addAction("确定", QMUIDialogAction.ActionListener { d, index ->
                    d.dismiss()
                    // 如果用户继续：
                    executor.execute()
                })
                .show()
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
}