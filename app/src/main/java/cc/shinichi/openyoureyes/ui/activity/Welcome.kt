package cc.shinichi.openyoureyes.ui.activity

import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.constant.SpTag
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import kotlinx.android.synthetic.main.activity_welcome.img_day_picture

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/24  15:54
 * description: 欢迎界面
 */
class Welcome : BaseActivity(), Callback {

  private var handler: HandlerUtil.HandlerHolder? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)
    initUtil()
  }

  override fun initData() {}

  override fun initView() {}

  override fun initUtil() {
    handler = HandlerUtil
        .HandlerHolder(this)
  }

  override fun onStart() {
    super
        .onStart()
    loadImage()
  }

  override fun onDestroy() {
    super
        .onDestroy()
    // 移除所有消息
    handler
        ?.removeCallbacksAndMessages(null)
    handler = null
  }

  override fun onBackPressed() {
    super
        .onBackPressed()
    finish()
  }

  private fun loadImage() {
    // 加载每日美图，动画时长2秒
    val imagePath = getSp().getString(SpTag.splashNextPageUrl, "")
    if (!isNull(imagePath)) {
      ImageLoader
          .load(imagePath, img_day_picture)
    } else {
      ImageLoader
          .loadResource(R.raw.welcome_bg, img_day_picture)
    }
    val scaleAnimation = ScaleAnimation(
        1.0f, 1.1f, 1.0f, 1.1f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    )
    scaleAnimation.duration = 3000
    scaleAnimation.fillAfter = true
    img_day_picture.startAnimation(scaleAnimation)
    // 延时3秒打开主页
    handler
        ?.sendEmptyMessageDelayed(1, 3500)
  }

  override fun handleMessage(msg: Message?): Boolean {
    if (msg?.what == 1) {
      IntentUtil
          .intent2Home(this)
      finish()
    }
    return true
  }
}