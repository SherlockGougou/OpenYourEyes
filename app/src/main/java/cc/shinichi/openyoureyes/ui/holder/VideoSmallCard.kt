package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class VideoSmallCard : BaseHolder {

  private lateinit var context: Context
  private var helper: BaseViewHolder
  private var entity: HomeDataEntity

  constructor(
    context: Context,
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    this.context = context
    this.entity = entity
    this.helper = helper
    setData()
  }

  private fun setData() {
    val data = entity.getItem()?.data ?: return

    val layout_root_container = helper.getView<View>(R.id.layout_root_container)
    val img_videoSmallCard_img = helper.getView<SimpleDraweeView>(R.id.img_videoSmallCard_img)
    val tv_videosmallcard_time_length = helper.getView<TextView>(R.id.tv_videosmallcard_time_length)

    val tv_videosmallcard_title = helper.getView<TextView>(R.id.tv_videosmallcard_title)
    val tv_videosmallcard_des = helper.getView<TextView>(R.id.tv_videosmallcard_des)

    ImageLoader.load(data.cover?.feed, img_videoSmallCard_img)
    tv_videosmallcard_time_length.text = UIUtil.getDurationText(data.duration)
    tv_videosmallcard_title.text = data.title
    tv_videosmallcard_des.text = "#" + data.category + " / " + data.author?.name

    layout_root_container.setOnClickListener {
      IntentUtil.intent2VideoDetail(
          context, entity.getItem()?.data?.playUrl, entity.getItem()?.data?.id.toString(),
          data.cover?.feed
      )
    }
  }
}