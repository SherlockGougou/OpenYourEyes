package cc.shinichi.openyoureyes.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.api.Api
import cc.shinichi.openyoureyes.api.ApiListener
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.constant.Code
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.constant.SpTag
import cc.shinichi.openyoureyes.datapool.VideoDataPool
import cc.shinichi.openyoureyes.model.bean.home.Data
import cc.shinichi.openyoureyes.model.bean.home.HomeDataBean
import cc.shinichi.openyoureyes.model.bean.home.Item
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.ui.adapter.HomeDataAdapter
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import cc.shinichi.openyoureyes.widget.MyLoadMoreView
import com.facebook.drawee.view.SimpleDraweeView
import com.google.gson.Gson
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_detail.imageVideoDetailBg
import kotlinx.android.synthetic.main.activity_video_detail.rv_video_detail

class VideoDetail : GSYBaseActivityDetail<StandardGSYVideoPlayer>(), Callback {

  private lateinit var context: Context
  private var handler: HandlerUtil.HandlerHolder? = null
  private val TAG: String = javaClass.simpleName
  private var gson: Gson? = null
  private var sp: SharedPreferences? = null

  // view
  private lateinit var detailVideo: StandardGSYVideoPlayer

  // item
  private lateinit var item: Item
  private var allHomeDataEntity: MutableList<HomeDataEntity> = ArrayList()
  private var homeDataAdapter: HomeDataAdapter? = null

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
      item: Item
    ) {
      VideoDataPool.setDataBean(item)
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
    detailVideo.layoutParams.height = (((UIUtil.getPhoneWidth().toFloat()) / 16) * 9).toInt()

    // recyclerview
    homeDataAdapter = HomeDataAdapter(context, allHomeDataEntity)
    homeDataAdapter?.setEnableLoadMore(false)
    homeDataAdapter?.setLoadMoreView(MyLoadMoreView())
    rv_video_detail.layoutManager = LinearLayoutManager(context)
    rv_video_detail.adapter = homeDataAdapter
  }

  private fun initData() {
    item = VideoDataPool.getDataBean()
    // video
    detailVideo.startAfterPrepared()
    // title
    detailVideo.titleTextView.text = item.data?.content?.data?.title
    // background
    ImageLoader.load(item.data?.content?.data?.cover?.blurred, imageVideoDetailBg)

    // 相关视频
    getRelateData()
  }

  private fun getRelateData() {
    Api.getInstance().getAsync(context, Constant.videoDetailRelateUrl + item.data?.content?.data?.id, object : ApiListener() {
      override fun success(string: String?) {
        super.success(string)
        handler?.sendEmptyMessage(Code.RefreshFinish)
        getEntityList(string)
      }
    })
  }
  
  private fun getEntityList(
    string: String?
  ) {
    val bean: HomeDataBean? = getGson().fromJson(string, HomeDataBean::class.javaObjectType)

    if (bean?.itemList != null) {
      allHomeDataEntity.clear()
      allHomeDataEntity.add(HomeDataEntity(HomeDataEntity.TYPE_videoDetailHeader, item))
      for (item: Item? in bean.itemList) {
        when (item?.type) {
          HomeDataEntity.textCard -> {
            allHomeDataEntity.add(HomeDataEntity(HomeDataEntity.TYPE_videoDetailTextCardHeader, item))
          }
          HomeDataEntity.videoSmallCard -> {
            allHomeDataEntity.add(HomeDataEntity(HomeDataEntity.TYPE_videoDetailSmallVideo, item))
          }
        }
      }
      homeDataAdapter?.setNewData(allHomeDataEntity)
    }
  }

  private fun getGson(): Gson {
    if (gson == null) {
      gson = Gson()
    }
    return gson as Gson
  }

  private fun getSp(): SharedPreferences {
    if (sp == null) {
      sp = App.application.getSharedPreferences(SpTag.defaultSpName, Context.MODE_PRIVATE)
    }
    return sp as SharedPreferences
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
    when(msg?.what) {
      Code.RefreshFinish -> {

      }
    }
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
    ImageLoader.load(item.data?.content?.data?.cover?.feed, imageView)
    return GSYVideoOptionBuilder()
        .setThumbImageView(imageView)
        .setUrl(item.data?.content?.data?.playUrl)
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