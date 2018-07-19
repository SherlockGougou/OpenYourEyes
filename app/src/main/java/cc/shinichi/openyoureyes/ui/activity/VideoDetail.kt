package cc.shinichi.openyoureyes.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.view.View
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.datapool.VideoDataPool
import cc.shinichi.openyoureyes.model.bean.home.Data
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.facebook.drawee.view.SimpleDraweeView
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_detail.imageVideoDetailBg

class VideoDetail : GSYBaseActivityDetail<StandardGSYVideoPlayer>(), Callback {

  private lateinit var context: Context
  private var handler: HandlerUtil.HandlerHolder? = null

  // view
  private lateinit var detailVideo: StandardGSYVideoPlayer

  // data
  private lateinit var data: Data

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_video_detail)

    initTool()
    initView()
    initData()

    initVideoBuilderMode()
  }

  companion object {
    fun activityStart(
      context: Context,
      data: Data
    ) {
      VideoDataPool.setDataBean(data)
      val intent = Intent()
      intent.setClass(context, VideoDetail::class.java)
      context.startActivity(intent)
    }
  }

  private fun initTool() {
    context = this
    handler = HandlerUtil.HandlerHolder(this)
  }

  private fun initView() {
    detailVideo = findViewById(R.id.detailVideo)
    detailVideo.titleTextView.visibility = View.VISIBLE
    detailVideo.backButton.visibility = View.VISIBLE
    detailVideo.backButton.setOnClickListener {
      onBackPressed()
    }

    // recyclerview
//    rv_video_detail.
  }

  private fun initData() {
    data = VideoDataPool.getDataBean()

    // video
    detailVideo.startAfterPrepared()

    // title
    detailVideo.titleTextView.text = data.content?.data?.title

    // background
    ImageLoader.load(data.content?.data?.cover?.blurred, imageVideoDetailBg)
  }

  override fun onWindowFocusChanged(hasFocus: Boolean) {
    super.onWindowFocusChanged(hasFocus)
    if (hasFocus && Build.VERSION.SDK_INT >= 19) {
      val decorView = window.decorView
      decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          or View.SYSTEM_UI_FLAG_FULLSCREEN
          or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
  }

  override fun handleMessage(msg: Message?): Boolean {
    return true
  }

  override fun clickForFullScreen() {
  }

  override fun getDetailOrientationRotateAuto(): Boolean {
    return true
  }

  override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
    return detailVideo
  }

  override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
    //内置封面可参考SampleCoverVideo
    val imageView = SimpleDraweeView(this)
    ImageLoader.load(data.content?.data?.cover?.feed, imageView)
    return GSYVideoOptionBuilder()
        .setThumbImageView(imageView)
        .setUrl(data.content?.data?.playUrl)
        .setCacheWithPlay(true)
        .setVideoTitle(" ")
        .setIsTouchWiget(true)
        .setRotateViewAuto(false)
        .setLockLand(false)
        .setShowFullAnimation(false)
        .setNeedLockFull(true)
        .setSeekRatio(1f)
  }
}