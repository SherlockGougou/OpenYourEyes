package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class BriefCard {

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

    val img_category_icon = helper.getView<SimpleDraweeView>(R.id.img_category_icon)
    val tv_category_name = helper.getView<TextView>(R.id.tv_category_name)
    val tv_category_des = helper.getView<TextView>(R.id.tv_category_des)

    ImageLoader.load(data.icon, img_category_icon)

    tv_category_name.text = data.title
    tv_category_des.text = data.description
  }
}