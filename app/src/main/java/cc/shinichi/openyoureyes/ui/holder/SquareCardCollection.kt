package cc.shinichi.openyoureyes.ui.holder

import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.ui.adapter.HorRvAdapter
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.widget.decoration.HorRvDecoration
import com.chad.library.adapter.base.BaseViewHolder

class SquareCardCollection {

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
    val data = entity.getData()?.data ?: return

    val tv_TextCard = helper.getView<TextView>(R.id.tv_TextCard)
    tv_TextCard.text = data.header?.title
    if (UIUtil.isNull(data.header?.actionUrl)) {
      tv_TextCard.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    } else {
      drawable = App.application.getDrawable(R.drawable.ic_action_choose_right)
      tv_TextCard.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }

    val recyclerView = helper.getView<RecyclerView>(R.id.rv_horizontalScrollCard)
    val manager = LinearLayoutManager(recyclerView.context)
    manager.orientation = LinearLayoutManager.HORIZONTAL
    recyclerView.layoutManager = manager
    recyclerView.onFlingListener = null
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
    val adapter = HorRvAdapter(recyclerView.context, entity.getData()?.data?.itemList)
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