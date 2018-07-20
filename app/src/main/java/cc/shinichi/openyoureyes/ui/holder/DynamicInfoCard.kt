package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class DynamicInfoCard {

  private var context: Context
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

    val img_user_icon = helper.getView<SimpleDraweeView>(R.id.img_user_icon)
    val img_action_next_grey = helper.getView<ImageView>(R.id.img_action_next_grey)
    val img_videoSmallCard_img = helper.getView<SimpleDraweeView>(R.id.img_videoSmallCard_img)
    val tv_user_name = helper.getView<TextView>(R.id.tv_user_name)
    val tv_user_des = helper.getView<TextView>(R.id.tv_user_des)
    val tv_dynamic_info_content = helper.getView<TextView>(R.id.tv_dynamic_info_content)
    val tv_videosmallcard_time_length = helper.getView<TextView>(R.id.tv_videosmallcard_time_length)
    val tv_videosmallcard_title = helper.getView<TextView>(R.id.tv_videosmallcard_title)
    val tv_videosmallcard_des = helper.getView<TextView>(R.id.tv_videosmallcard_des)
    val tv_reply_button = helper.getView<TextView>(R.id.tv_reply_button)
    val tv_reply_time = helper.getView<TextView>(R.id.tv_reply_time)
    val tv_like_count = helper.getView<TextView>(R.id.tv_like_count)

    tv_user_des.text = data.text
    tv_user_name.text = data.user?.nickname
    ImageLoader.load(data.user?.avatar, img_user_icon)
    ImageLoader.load(data.simpleVideo?.cover?.feed, img_videoSmallCard_img)

    tv_dynamic_info_content.text = data.reply?.message
    tv_videosmallcard_title.text = data.simpleVideo?.title
    tv_videosmallcard_des.text = "#" + data.simpleVideo?.category

    tv_videosmallcard_time_length.text = UIUtil.getDurationText(data.simpleVideo?.duration)
    tv_reply_time.text = UIUtil.formatDate(data.createDate)
    tv_like_count.text = "" + data.reply?.likeCount

    img_videoSmallCard_img.setOnClickListener {
      IntentUtil.intent2VideoDetail(
          context, entity.getItem()?.data?.simpleVideo?.playUrl,
          entity.getItem()?.data?.simpleVideo?.id.toString(),
          data.simpleVideo?.cover?.feed
      )
    }
  }
}