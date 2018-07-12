package cc.shinichi.openyoureyes.util.image

import android.net.Uri
import cc.shinichi.openyoureyes.app.App
import com.facebook.drawee.backends.pipeline.Fresco
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