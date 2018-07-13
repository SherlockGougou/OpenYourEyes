package cc.shinichi.openyoureyes.util.image

import android.net.Uri
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.util.UIUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils.ScaleType
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/24  17:26
 * description:
 */
object ImageLoader {

  fun load(
    url: String? = "",
    imageView: SimpleDraweeView
  ) {
    imageView.setImageURI(url)
  }

  fun loadVideoCover(
    url: String? = "",
    imageView: SimpleDraweeView
  ) {
    val hierarchy = imageView.hierarchy
    hierarchy.actualImageScaleType = ScaleType.CENTER_CROP
    hierarchy.fadeDuration = 300
    hierarchy.roundingParams = RoundingParams().setCornersRadius(UIUtil.dp2px(6).toFloat())
    imageView.setImageURI(url)
  }

  fun loadResource(
    resId: Int,
    imageView: SimpleDraweeView
  ) {
    val uri = Uri.parse("res://" + App.application.packageName + "/" + resId)
    imageView.setImageURI(uri, null)
  }

  fun pause() {
    Fresco.getImagePipeline()
        .pause()
  }

  fun resume() {
    Fresco.getImagePipeline()
        .resume()
  }

  fun clearImageMemoryCache() {
    Fresco.getImagePipeline()
        .clearMemoryCaches()
  }

  fun clearImageDiskCache() {
    Fresco.getImagePipeline()
        .clearDiskCaches()
  }
}