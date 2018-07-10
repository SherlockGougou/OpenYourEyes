package cc.shinichi.openyoureyes.util.image

import android.content.Context
import android.widget.ImageView
import cc.shinichi.openyoureyes.util.UIUtil
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/24  17:26
 * description:
 */
object ImageLoader {

  fun load(
    context: Context,
    url: String? = "",
    imageView: ImageView
  ) {
    GlideApp
        .with(context)
        .load(url)
        .transform(CenterCrop())
        .into(imageView)
  }

  fun loadRound(
    context: Context,
    url: Any? = null,
    imageView: ImageView,
    radius: Int
  ) {
    GlideApp
        .with(context)
        .load(url)
        .transform(CenterCrop())
        .transform(RoundedCorners(radius))
        .into(imageView)
  }

  fun loadSplash(
    context: Context,
    url: String? = "",
    imageView: ImageView
  ) {
    GlideApp
        .with(context)
        .load(url)
        .override(UIUtil.getPhoneWidth(), UIUtil.getPhoneHeight())
        .transform(CenterCrop())
        .into(imageView)
  }

  fun loadAvatarResource(
    context: Context,
    resId: Int,
    imageView: ImageView
  ) {
    GlideApp
        .with(context)
        .load(resId)
        .transform(CircleCrop())
        .skipMemoryCache(true)
        .into(imageView)
  }

  fun loadResource(
    context: Context,
    resId: Int,
    imageView: ImageView,
    useCenterCrop: Boolean = false
  ) {
    if (useCenterCrop) {
      GlideApp
          .with(context)
          .load(resId)
          .transform(CircleCrop())
          .into(imageView)
    } else {
      GlideApp
          .with(context)
          .load(resId)
          .into(imageView)
    }
  }

  fun clearGlideMemoryCache(context: Context) {
    GlideApp
        .get(context)
        .clearMemory()
  }
}