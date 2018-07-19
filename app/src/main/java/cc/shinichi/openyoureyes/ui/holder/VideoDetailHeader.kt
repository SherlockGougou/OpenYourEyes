package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class VideoDetailHeader {

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
    val item = entity.getData() ?: return

    val tv_video_detail_title = helper.getView<TextView>(R.id.tv_video_detail_title)
    val tv_video_detail_des = helper.getView<TextView>(R.id.tv_video_detail_des)
    val tv_video_detail_content = helper.getView<TextView>(R.id.tv_video_detail_content)

    val tv_video_action_like = helper.getView<TextView>(R.id.tv_video_action_like)
    val tv_video_action_share = helper.getView<TextView>(R.id.tv_video_action_share)
    val tv_video_action_reply = helper.getView<TextView>(R.id.tv_video_action_reply)
    val tv_video_action_offline = helper.getView<TextView>(R.id.tv_video_action_offline)

    val rl_tag_root = helper.getView<View>(R.id.rl_tag_root)

    val img_tag_1 = helper.getView<SimpleDraweeView>(R.id.img_tag_1)
    val img_tag_2 = helper.getView<SimpleDraweeView>(R.id.img_tag_2)
    val img_tag_3 = helper.getView<SimpleDraweeView>(R.id.img_tag_3)

    val tv_tag_1 = helper.getView<TextView>(R.id.tv_tag_1)
    val tv_tag_2 = helper.getView<TextView>(R.id.tv_tag_2)
    val tv_tag_3 = helper.getView<TextView>(R.id.tv_tag_2)


    val img_user_icon = helper.getView<SimpleDraweeView>(R.id.img_user_icon)
    val tv_user_name = helper.getView<TextView>(R.id.tv_user_name)
    val tv_user_des = helper.getView<TextView>(R.id.tv_user_des)

    tv_video_detail_title.text = item.data?.header?.title
    tv_video_detail_des.text = item.data?.header?.description

    tv_video_detail_content.text = item.data?.content?.data?.description

    tv_video_action_like.text = item.data?.content?.data?.consumption?.collectionCount.toString()
    tv_video_action_share.text = item.data?.content?.data?.consumption?.shareCount.toString()
    tv_video_action_reply.text = item.data?.content?.data?.consumption?.replyCount.toString()
    tv_video_action_offline.text = "缓存"

    val tagCount = item.data?.content?.data?.tags?.size
    if (tagCount != null && tagCount > 0) {
      rl_tag_root.visibility = View.VISIBLE
      when(tagCount) {
        1 -> {
          img_tag_1.visibility = View.VISIBLE
          tv_tag_1.visibility = View.VISIBLE

          ImageLoader.load(item.data.content.data.tags[0]?.bgPicture, img_tag_1)
          tv_tag_1.text = item.data.content.data.tags[0]?.name

          img_tag_2.visibility = View.GONE
          tv_tag_2.visibility = View.GONE

          img_tag_3.visibility = View.GONE
          tv_tag_3.visibility = View.GONE
        }
        2 -> {
          img_tag_1.visibility = View.VISIBLE
          tv_tag_1.visibility = View.VISIBLE

          ImageLoader.load(item.data.content.data.tags[0]?.bgPicture, img_tag_1)
          tv_tag_1.text = item.data.content.data.tags[0]?.name

          img_tag_2.visibility = View.VISIBLE
          tv_tag_2.visibility = View.VISIBLE

          ImageLoader.load(item.data.content.data.tags[1]?.bgPicture, img_tag_2)
          tv_tag_2.text = item.data.content.data.tags[1]?.name

          img_tag_3.visibility = View.GONE
          tv_tag_3.visibility = View.GONE
        }
        3 -> {
          img_tag_1.visibility = View.VISIBLE
          tv_tag_1.visibility = View.VISIBLE

          ImageLoader.load(item.data.content.data.tags[0]?.bgPicture, img_tag_1)
          tv_tag_1.text = item.data.content.data.tags[0]?.name

          img_tag_2.visibility = View.VISIBLE
          tv_tag_2.visibility = View.VISIBLE

          ImageLoader.load(item.data.content.data.tags[1]?.bgPicture, img_tag_2)
          tv_tag_2.text = item.data.content.data.tags[1]?.name

          img_tag_3.visibility = View.VISIBLE
          tv_tag_3.visibility = View.VISIBLE

          ImageLoader.load(item.data.content.data.tags[2]?.bgPicture, img_tag_3)
          tv_tag_3.text = item.data.content.data.tags[2]?.name
        }
      }
    } else {
      rl_tag_root.visibility = View.GONE
    }

    ImageLoader.load(item.data?.content?.data?.author?.icon, img_user_icon)
    tv_user_name.text = item.data?.content?.data?.author?.name
    tv_user_des.text = item.data?.content?.data?.author?.description
  }
}