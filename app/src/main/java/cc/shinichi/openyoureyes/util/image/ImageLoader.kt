package cc.shinichi.openyoureyes.util.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/24  17:26
* description: 
*/
open class ImageLoader() {

    companion object {
        fun load(context: Context, url: String = "", imageView: ImageView, isWithCrossFade: Boolean = false) {
            if (isWithCrossFade) {
                GlideApp.with(context).load(url).transition(withCrossFade().crossFade(2000)).into(imageView)
            } else {
                GlideApp.with(context).load(url).into(imageView)
            }
        }

        fun loadAvatarResource(context: Context, resId: Int, imageView: ImageView) {
            GlideApp.with(context).load(resId).transform(CircleCrop()).into(imageView)
        }

        fun clearGlideMemoryCache(context: Context) {
            GlideApp.get(context).clearMemory()
        }
    }
}