package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class BriefCard {

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

    val img_category_icon = helper.getView<SimpleDraweeView>(R.id.img_category_icon)
    val img_category_icon_circle = helper.getView<SimpleDraweeView>(R.id.img_category_icon_circle)
    val tv_category_name = helper.getView<TextView>(R.id.tv_category_name)
    val tv_category_des = helper.getView<TextView>(R.id.tv_category_des)

    if (data.iconType.equals("round")) {
      img_category_icon.visibility = View.GONE
      img_category_icon_circle.visibility = View.VISIBLE
      ImageLoader.load(data.icon, img_category_icon_circle)
    } else {
      img_category_icon_circle.visibility = View.GONE
      img_category_icon.visibility = View.VISIBLE
      ImageLoader.load(data.icon, img_category_icon)
    }

    tv_category_name.text = data.title
    tv_category_des.text = data.description
  }
}