package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.ui.adapter.VideoDetailHoriTagRvAdapter
import cc.shinichi.openyoureyes.util.image.ImageLoader
import cc.shinichi.openyoureyes.util.log.ALog
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class VideoDetailHeader {

  private val TAG = "VideoDetailHeader"
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

    val tv_video_detail_title = helper.getView<TextView>(R.id.tv_video_detail_title)
    val tv_video_detail_des = helper.getView<TextView>(R.id.tv_video_detail_des)
    val tv_video_detail_content = helper.getView<TextView>(R.id.tv_video_detail_content)

    val tv_video_action_like = helper.getView<TextView>(R.id.tv_video_action_like)
    val tv_video_action_share = helper.getView<TextView>(R.id.tv_video_action_share)
    val tv_video_action_reply = helper.getView<TextView>(R.id.tv_video_action_reply)
    val tv_video_action_offline = helper.getView<TextView>(R.id.tv_video_action_offline)

    val img_user_icon = helper.getView<SimpleDraweeView>(R.id.img_user_icon)
    val tv_user_name = helper.getView<TextView>(R.id.tv_user_name)
    val tv_user_des = helper.getView<TextView>(R.id.tv_user_des)

    tv_video_detail_title.text = data.title
    tv_video_detail_des.text = "#${data.category} / ${data.author?.name}"

    tv_video_detail_content.text = data.description

    tv_video_action_like.text = data.consumption?.collectionCount.toString()
    tv_video_action_share.text = data.consumption?.shareCount.toString()
    tv_video_action_reply.text = data.consumption?.replyCount.toString()
    tv_video_action_offline.text = "缓存"

    val tagCount: Int? = data.tags?.size
    ALog.log(TAG, "tagCount = $tagCount")
    if (tagCount != null && tagCount > 0) {
      helper.setVisible(R.id.tvDividerAboveTagList, true)
      helper.setVisible(R.id.llTagListContainer, true)
      val rvVideoTagList = helper.getView<RecyclerView>(R.id.rvVideoTagList)
      val linearLayoutManager = LinearLayoutManager(context)
      linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
      rvVideoTagList.layoutManager = linearLayoutManager
      rvVideoTagList.adapter = VideoDetailHoriTagRvAdapter(context, data.tags)
    } else {
      helper.setGone(R.id.tvDividerAboveTagList, false)
      helper.setGone(R.id.llTagListContainer, false)

    }

    ImageLoader.load(data.author?.icon, img_user_icon)
    tv_user_name.text = data.author?.name
    tv_user_des.text = data.author?.description
  }
}