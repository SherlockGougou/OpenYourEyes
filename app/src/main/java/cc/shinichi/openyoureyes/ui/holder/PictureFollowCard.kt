package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.bean.home.Tag
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import cc.shinichi.openyoureyes.widget.FZLanTingLTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayout

class PictureFollowCard : BaseHolder {

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
    val data = entity.getItem()?.data ?: return

    val img_follow_card_user_icon = helper.getView<SimpleDraweeView>(R.id.img_follow_card_user_icon)
    val tv_follow_card_title = helper.getView<TextView>(R.id.tv_follow_card_title)
    val tv_follow_card_des_right = helper.getView<TextView>(R.id.tv_follow_card_des_right)
    val tv_content_des = helper.getView<TextView>(R.id.tv_content_des)
    val tv_follow_time_length = helper.getView<TextView>(R.id.tv_follow_time_length)
    val flexbox = helper.getView<FlexboxLayout>(R.id.flexbox)
    val img_follow_card_img = helper.getView<SimpleDraweeView>(R.id.img_follow_card_img)

    tv_follow_time_length.visibility = View.GONE
    ImageLoader.load(data.header?.icon, img_follow_card_user_icon)
    ImageLoader.load(data.content?.data?.cover?.feed, img_follow_card_img)
    tv_follow_card_title.text = data.header?.issuerName
    tv_follow_card_des_right.text = data.content?.data?.title
    tv_content_des.text = data.content?.data?.description

    val tags: List<Tag?>? = data.content?.data?.tags
    if (tags == null) {
      flexbox.visibility = View.GONE
    } else {
      flexbox.visibility = View.VISIBLE
      flexbox.flexDirection = FlexDirection.ROW
      flexbox.removeAllViews()
      val lp = FlexboxLayout.LayoutParams(
          ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.WRAP_CONTENT
      )
      for (item in tags) {
        val textView = createTextView(item)
        lp.rightMargin = 10
        textView.layoutParams = lp
        flexbox.addView(textView)
      }
    }

    helper.getView<View>(R.id.img_follow_card_user_icon).setOnClickListener {
      ActionUrlUtil.jump(context, data.header?.actionUrl)
    }

    helper.getView<View>(R.id.rl_follow_author_container).setOnClickListener {
      ActionUrlUtil.jump(context, data.header?.actionUrl)
    }
  }

  private fun createTextView(item: Tag?): TextView {
    return FZLanTingLTextView(context).apply {
      text = item?.name
      setBackgroundResource(R.drawable.tag_item_text_back)
      gravity = Gravity.CENTER
      textSize = 12f
      setTextColor(context.resources.getColor(R.color.blue_2772d0))
      setOnClickListener {
        ActionUrlUtil.jump(context, item?.actionUrl)
      }
    }
  }
}