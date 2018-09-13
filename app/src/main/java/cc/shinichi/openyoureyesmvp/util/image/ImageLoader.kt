package cc.shinichi.openyoureyesmvp.util.image

import android.net.Uri
import cc.shinichi.openyoureyesmvp.app.App
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor
import com.facebook.imagepipeline.request.ImageRequestBuilder

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

    fun loadBlur(
            url: String? = "",
            imageView: SimpleDraweeView,
            iterations: Int = 100,
            blurRadius: Int = 100
    ) {
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setPostprocessor(IterativeBoxBlurPostProcessor(iterations, blurRadius))
                .build()
        val controller = Fresco.newDraweeControllerBuilder()
                .setOldController(imageView.controller)
                .setImageRequest(imageRequest)
                .build()
        imageView.controller = controller
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