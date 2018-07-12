package cc.shinichi.openyoureyes.ui.holder

import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.bean.home.Data
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class FollowCard {

  private var helper: BaseViewHolder
  private var entity: HomeDataEntity

  constructor(
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    this.entity = entity
    this.helper = helper
    setData()
  }

  private fun setData() {
    val data: Data? = entity.getData()?.data ?: return

    val img_follow_card_img: SimpleDraweeView = helper.getView(R.id.img_follow_card_img)
    val tv_follow_time_length: TextView = helper.getView(R.id.tv_follow_time_length)
    val img_follow_card_user_icon: SimpleDraweeView = helper.getView(R.id.img_follow_card_user_icon)
    val tv_follow_card_title: TextView = helper.getView(R.id.tv_follow_card_title)
    val tv_follow_card_des: TextView = helper.getView(R.id.tv_follow_card_des)

    ImageLoader.load(data?.content?.data?.cover?.feed, img_follow_card_img)
    tv_follow_time_length.text = UIUtil.getDurationText(data?.content?.data?.duration)

    ImageLoader.load(data?.header?.icon, img_follow_card_user_icon)
    tv_follow_card_title.text = data?.header?.title
    tv_follow_card_des.text = data?.header?.description
  }
}