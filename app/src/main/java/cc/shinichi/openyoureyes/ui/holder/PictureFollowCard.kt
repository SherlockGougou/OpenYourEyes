package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.bean.home.Tag
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.ui.adapter.HorRvAdapter
import cc.shinichi.openyoureyes.util.image.ImageLoader
import cc.shinichi.openyoureyes.widget.decoration.HorRvDecoration
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

class PictureFollowCard {

  private var helper: BaseViewHolder
  private var entity: HomeDataEntity
  private var inflater: LayoutInflater

  constructor(
    context:Context,
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    this.entity = entity
    this.helper = helper
    this.inflater = LayoutInflater.from(context)
    setData()
  }

  private fun setData() {
    val data = entity.getData()?.data ?: return

    val img_follow_card_user_icon = helper.getView<SimpleDraweeView>(R.id.img_follow_card_user_icon)
    val tv_follow_card_title = helper.getView<TextView>(R.id.tv_follow_card_title)
    val tv_follow_card_des_right = helper.getView<TextView>(R.id.tv_follow_card_des_right)
    val tv_content_des = helper.getView<TextView>(R.id.tv_content_des)
    val tv_follow_time_length = helper.getView<TextView>(R.id.tv_follow_time_length)
    val tag_flowlayout = helper.getView<TagFlowLayout>(R.id.tag_flowlayout)
    val img_follow_card_img = helper.getView<SimpleDraweeView>(R.id.img_follow_card_img)

    tv_follow_time_length.visibility = View.GONE
    ImageLoader.load(data.header?.icon, img_follow_card_user_icon)
    ImageLoader.load(data.content?.data?.cover?.feed, img_follow_card_img)
    tv_follow_card_title.text = data.header?.issuerName
    tv_follow_card_des_right.text = data.content?.data?.title
    tv_content_des.text = data.content?.data?.description

    if (data.content?.tag == null) {
      tag_flowlayout.visibility = View.GONE
    } else {
      tag_flowlayout.visibility = View.VISIBLE
      tag_flowlayout.adapter = object : TagAdapter<Tag>(data.content.tag) {
        override fun getView(
          parent: FlowLayout?,
          position: Int,
          t: Tag?
        ): View {
          val tv = inflater.inflate(R.layout.item_tag_item, tag_flowlayout, false) as TextView
          tv.text = t?.name
          return tv
        }
      }
    }
  }
}