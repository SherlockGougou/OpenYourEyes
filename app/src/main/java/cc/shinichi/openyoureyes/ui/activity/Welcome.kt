package cc.shinichi.openyoureyes.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.constant.ConstantForSp
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import cc.shinichi.openyoureyes.util.log.ALog
import com.qmuiteam.qmui.widget.dialog.QMUIDialog.MessageDialogBuilder
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yanzhenjie.permission.Rationale
import kotlinx.android.synthetic.main.activity_welcome.img_day_picture
import java.util.Random

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/24  15:54
* description: 欢迎界面
*/class Welcome : BaseActivity() {

    private val TAG = "Welcome"
    private lateinit var context: Context
    private val handler: Handler = Handler()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        context = this
        sharedPreferences = getSharedPreferences(ConstantForSp.文件名, Context.MODE_PRIVATE)
    }

    override fun onStart() {
        loadImage()
        AndPermission.with(context)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .rationale(mRationale)
                .onGranted {
                    intentToHome()
                }
                .onDenied { permissions ->
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        // 这些权限被用户总是拒绝。
                        // 这里使用一个Dialog询问用户是否继续授权。
                        val dialog: MessageDialogBuilder = MessageDialogBuilder(context)
                        val settingService = AndPermission.permissionSetting(context)
                        dialog.setTitle("注意")
                                .setMessage("您拒绝了存储权限，App将无法正常使用，是否重新授权？")
                                .addAction("取消", QMUIDialogAction.ActionListener { d, _ ->
                                    d.dismiss()
                                    // 如果用户不同意去设置：
                                    settingService.cancel();
                                })
                                .addAction("确定", QMUIDialogAction.ActionListener { d, _ ->
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

    private val mRationale = Rationale { context, _, executor ->
        // 这里使用一个Dialog询问用户是否继续授权。
        val dialog: MessageDialogBuilder = MessageDialogBuilder(context)
        val settingService = AndPermission.permissionSetting(context)
        dialog.setTitle("注意")
                .setMessage("您拒绝了存储权限，App将无法正常使用，是否重新授权？")
                .addAction("取消", QMUIDialogAction.ActionListener { d, _ ->
                    d.dismiss()
                    // 如果用户中断：
                    executor.cancel()
                })
                .addAction("确定", QMUIDialogAction.ActionListener { d, _ ->
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

    private fun loadImage() {
        // 加载每日美图，动画时长2秒
        val index = Random().nextInt(6)
        val imagePath = Constant.酷安图片列表[index]
        ALog.Log(TAG, imagePath)
        ImageLoader.load(this, imagePath, img_day_picture, true)
    }

    private fun intentToHome() {
        // 延时3秒打开主页
        handler.postDelayed({
            runOnUiThread({
                IntentUtil.intent2Home(this)
                finish()
            })
        }, 3000)
    }
}