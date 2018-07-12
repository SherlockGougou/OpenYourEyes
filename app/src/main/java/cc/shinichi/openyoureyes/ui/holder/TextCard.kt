package cc.shinichi.openyoureyes.ui.holder

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.model.bean.home.Data
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.UIUtil
import com.chad.library.adapter.base.BaseViewHolder

class TextCard {

  private var helper: BaseViewHolder
  private var entity: HomeDataEntity
  private lateinit var drawable: Drawable

  constructor(
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    this.entity = entity
    this.helper = helper
    setData()
  }

  private fun setData() {
    val data: Data = entity.getData()?.data ?: return

    drawable = App.application.getDrawable(R.drawable.ic_action_choose_right)
    val tv_TextCard_header5 = helper.getView<TextView>(R.id.tv_TextCard_header5)
    val tv_TextCard_footer2 = helper.getView<TextView>(R.id.tv_TextCard_footer2)

    if ("header5" == data.type) {
      tv_TextCard_footer2.visibility = View.GONE
      tv_TextCard_header5.visibility = View.VISIBLE
      tv_TextCard_header5.text = data.text
      if (UIUtil.isNull(data.actionUrl)) {
        tv_TextCard_header5.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
      } else {
        tv_TextCard_header5.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
      }
    } else if ("footer2" == data.type) {
      tv_TextCard_header5.visibility = View.GONE
      tv_TextCard_footer2.visibility = View.VISIBLE
      tv_TextCard_footer2.text = data.text
      if (UIUtil.isNull(data.actionUrl)) {
        tv_TextCard_footer2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
      } else {
        tv_TextCard_footer2.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
      }
    }
  }
}