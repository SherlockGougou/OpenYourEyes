package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.bean.home.Tag
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import cc.shinichi.openyoureyes.widget.video.SampleCoverVideo
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

open class AutoPlayFollowCard {

  companion object {
    val TAG = "AutoPlayFollowCard"
  }

  private var context: Context
  private var helper: BaseViewHolder
  private var entity: HomeDataEntity
  private var inflater: LayoutInflater

  constructor(
    context: Context,
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    this.context = context
    this.entity = entity
    this.helper = helper
    this.inflater = LayoutInflater.from(context)
    setData()
  }

  private fun setData() {
    val data = entity.getData()?.data ?: return

    val position = helper.adapterPosition

    val img_follow_card_user_icon = helper.getView<SimpleDraweeView>(R.id.img_follow_card_user_icon)
    val tv_follow_card_title = helper.getView<TextView>(R.id.tv_follow_card_title)
    val tv_follow_card_des_right = helper.getView<TextView>(R.id.tv_follow_card_des_right)
    val tv_follow_time_length = helper.getView<TextView>(R.id.tv_follow_time_length)
    val tv_content_des = helper.getView<TextView>(R.id.tv_content_des)
    val tag_flowlayout = helper.getView<TagFlowLayout>(R.id.tag_flowlayout)
    val gsy_list_auto_player = helper.getView<SampleCoverVideo>(R.id.gsy_list_auto_player)

    ImageLoader.load(data.header?.icon, img_follow_card_user_icon)
    tv_follow_card_title.text = data.header?.issuerName
    tv_follow_card_des_right.text = data.content?.data?.title
    tv_content_des.text = data.content?.data?.description
    tv_follow_time_length.text = UIUtil.getDurationText(data.content?.data?.duration)

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

    gsy_list_auto_player.loadCoverImage(data.content?.data?.cover?.feed, R.color.gray_e5)
    gsy_list_auto_player.setUpLazy(data.content?.data?.playUrl, true, null, null, "")
//    gsy_list_auto_player.backButton.visibility = View.GONE
//    gsy_list_auto_player.titleTextView.visibility = View.GONE
    gsy_list_auto_player.playTag = TAG
    gsy_list_auto_player.playPosition = position
    gsy_list_auto_player.isShowPauseCover = true
    gsy_list_auto_player.setIsTouchWiget(true)
  }
}