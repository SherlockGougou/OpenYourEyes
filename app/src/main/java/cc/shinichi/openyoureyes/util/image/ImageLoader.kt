package cc.shinichi.openyoureyes.util.image

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/24  17:26
* description: 
*/
open class ImageLoader() {

    companion object {
        fun load(activity: Activity, url: String = "", imageView: ImageView, isWithCrossFade: Boolean = false) {
            if (isWithCrossFade) {
                GlideApp.with(activity).load(url).transition(withCrossFade().crossFade(2000)).into(imageView)
            } else {
                GlideApp.with(activity).load(url).into(imageView)
            }
        }

        fun clearGlideMemoryCache(context: Context) {
            GlideApp.get(context).clearMemory()
        }
    }
}