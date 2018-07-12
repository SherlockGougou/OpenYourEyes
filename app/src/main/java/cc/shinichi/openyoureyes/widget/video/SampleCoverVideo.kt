package cc.shinichi.openyoureyes.widget.video

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.Surface
import android.view.View
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.facebook.drawee.view.SimpleDraweeView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView

class SampleCoverVideo : StandardGSYVideoPlayer {

  lateinit var mCoverImage: SimpleDraweeView
  var mCoverOriginUrl: String? = ""

  constructor(
    context: Context,
    fullFlag: Boolean?
  ) : super(context, fullFlag)

  constructor(context: Context) : super(context)

  constructor(
    context: Context,
    attrs: AttributeSet?
  ) : super(context, attrs)

  override fun init(context: Context) {
    super.init(context)

    mCoverImage = findViewById(R.id.img_follow_card_img)
    if (mThumbImageViewLayout != null && (mCurrentState == -1 || mCurrentState == GSYVideoView.CURRENT_STATE_NORMAL || mCurrentState == GSYVideoView.CURRENT_STATE_ERROR)) {
      mThumbImageViewLayout.visibility = View.VISIBLE
    }
  }

  override fun getLayoutId(): Int {
    return R.layout.video_layout_cover
  }

  fun loadCoverImage(
    url: String?
  ) {
    mCoverOriginUrl = url
    ImageLoader.load(mCoverOriginUrl, mCoverImage)
  }

  override fun startWindowFullscreen(
    context: Context,
    actionBar: Boolean,
    statusBar: Boolean
  ): GSYBaseVideoPlayer {
    val gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar)
    val sampleCoverVideo = gsyBaseVideoPlayer as SampleCoverVideo
    sampleCoverVideo.loadCoverImage(mCoverOriginUrl)
    return gsyBaseVideoPlayer
  }

  override fun showSmallVideo(
    size: Point,
    actionBar: Boolean,
    statusBar: Boolean
  ): GSYBaseVideoPlayer {
    //下面这里替换成你自己的强制转化
    val sampleCoverVideo = super.showSmallVideo(size, actionBar, statusBar) as SampleCoverVideo
    sampleCoverVideo.mStartButton.visibility = View.GONE
    sampleCoverVideo.mStartButton = null
    return sampleCoverVideo
  }

  /**下方两个重载方法，在播放开始前不屏蔽封面 */
  override fun onSurfaceUpdated(surface: Surface) {
    super.onSurfaceUpdated(surface)
    if (mThumbImageViewLayout != null && mThumbImageViewLayout.visibility == View.VISIBLE) {
      mThumbImageViewLayout.visibility = View.INVISIBLE
    }
  }

  override fun setViewShowState(
    view: View,
    visibility: Int
  ) {
    if (view === mThumbImageViewLayout && visibility != View.VISIBLE) {
      return
    }
    super.setViewShowState(view, visibility)
  }

  /**下方两个重载方法，在播放开始不显示底部进度 */
  override fun changeUiToPreparingShow() {
    super.changeUiToPreparingShow()
    setViewShowState(mBottomContainer, View.INVISIBLE)
  }

  override fun startAfterPrepared() {
    super.startAfterPrepared()
    setViewShowState(mBottomContainer, View.INVISIBLE)
  }
}