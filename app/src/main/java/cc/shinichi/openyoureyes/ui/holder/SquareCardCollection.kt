package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.model.bean.home.Item
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.ui.adapter.HorRvAdapter
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.widget.decoration.HorRvDecoration
import com.chad.library.adapter.base.BaseViewHolder

class SquareCardCollection {

  private var context: Context
  private var helper: BaseViewHolder
  private var entity: HomeDataEntity
  private lateinit var drawable: Drawable

  private var list: MutableList<Item?> = mutableListOf()
  private lateinit var adapter: HorRvAdapter
  private lateinit var manager: LinearLayoutManager
  private lateinit var snapHelper: PagerSnapHelper

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
    val data = entity.getData()?.data ?: return

    val tv_TextCard = helper.getView<TextView>(R.id.tv_TextCard)
    val tv_square_subtitle = helper.getView<TextView>(R.id.tv_square_subtitle)
    tv_TextCard.text = data.header?.title
    tv_square_subtitle.text = data.header?.subTitle

    if (UIUtil.isNull(data.header?.actionUrl)) {
      tv_TextCard.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    } else {
      drawable = App.application.resources.getDrawable(R.drawable.ic_action_choose_right)
      tv_TextCard.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }

    val recyclerView = helper.getView<RecyclerView>(R.id.rv_horizontalScrollCard)
    manager = LinearLayoutManager(recyclerView.context)
    manager.orientation = LinearLayoutManager.HORIZONTAL
    recyclerView.layoutManager = manager
    recyclerView.onFlingListener = null
    snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
    if (list.size > 0) {
      list.clear()
    }
    list.addAll(entity.getData()?.data?.itemList!!)
    adapter = HorRvAdapter(context, list)
    recyclerView.adapter = adapter
    val decoration = HorRvDecoration(adapter.itemCount)
    val decorationCount = recyclerView.itemDecorationCount
    if (decorationCount > 0) {
      for (i in 0 until decorationCount) {
        recyclerView.removeItemDecorationAt(i)
      }
    }
    recyclerView.addItemDecoration(decoration)
  }
}