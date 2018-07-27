package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.model.bean.home.Data
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.eye.ActionUrlUtil
import com.chad.library.adapter.base.BaseViewHolder

class TextCard : BaseHolder {

  private var context: Context
  private var helper: BaseViewHolder
  private var entity: HomeDataEntity
  private lateinit var drawable: Drawable

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
    val data: Data = entity.getItem()?.data ?: return

    drawable = App.application.resources.getDrawable(R.drawable.ic_action_choose_right)
    val tv_TextCard_header5 = helper.getView<TextView>(R.id.tv_TextCard_header5)
    val tv_TextCard_header4 = helper.getView<TextView>(R.id.tv_TextCard_header4)
    val tv_TextCard_header2 = helper.getView<TextView>(R.id.tv_TextCard_header2)
    val tv_TextCard_footer2 = helper.getView<TextView>(R.id.tv_TextCard_footer2)
    val tv_TextCard_footer1 = helper.getView<TextView>(R.id.tv_TextCard_footer1)

    if ("header5" == data.type) {
      tv_TextCard_footer1.visibility = View.GONE
      tv_TextCard_footer2.visibility = View.GONE
      tv_TextCard_header2.visibility = View.GONE
      tv_TextCard_header4.visibility = View.GONE
      tv_TextCard_header5.visibility = View.VISIBLE
      tv_TextCard_header5.text = data.text
      if (UIUtil.isNull(data.actionUrl)) {
        tv_TextCard_header5.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
      } else {
        tv_TextCard_header5.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
      }
    } else if ("footer2" == data.type) {
      tv_TextCard_header2.visibility = View.GONE
      tv_TextCard_header4.visibility = View.GONE
      tv_TextCard_header5.visibility = View.GONE
      tv_TextCard_footer1.visibility = View.GONE
      tv_TextCard_footer2.visibility = View.VISIBLE
      tv_TextCard_footer2.text = data.text
      if (UIUtil.isNull(data.actionUrl)) {
        tv_TextCard_footer2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
      } else {
        tv_TextCard_footer2.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
      }
    } else if ("footer1" == data.type) {
      tv_TextCard_header2.visibility = View.GONE
      tv_TextCard_header4.visibility = View.GONE
      tv_TextCard_header5.visibility = View.GONE
      tv_TextCard_footer2.visibility = View.GONE
      tv_TextCard_footer1.visibility = View.VISIBLE
      tv_TextCard_footer1.text = data.text
      if (UIUtil.isNull(data.actionUrl)) {
        tv_TextCard_footer1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
      } else {
        tv_TextCard_footer1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
      }
    } else if ("header4" == data.type) {
      tv_TextCard_footer1.visibility = View.GONE
      tv_TextCard_footer2.visibility = View.GONE
      tv_TextCard_header2.visibility = View.GONE
      tv_TextCard_header5.visibility = View.GONE
      tv_TextCard_header4.visibility = View.VISIBLE
      tv_TextCard_header4.text = data.text
    } else if ("header2" == data.type) {
      tv_TextCard_footer1.visibility = View.GONE
      tv_TextCard_footer2.visibility = View.GONE
      tv_TextCard_header5.visibility = View.GONE
      tv_TextCard_header4.visibility = View.GONE
      tv_TextCard_header2.visibility = View.VISIBLE
      tv_TextCard_header2.text = data.text
    }

    val actionUrl = data.actionUrl
    tv_TextCard_header5.setOnClickListener {
      ActionUrlUtil.jump(context, actionUrl)
    }
    tv_TextCard_footer1.setOnClickListener {
      ActionUrlUtil.jump(context, actionUrl)
    }
    tv_TextCard_footer2.setOnClickListener {
      ActionUrlUtil.jump(context, actionUrl)
    }
  }
}