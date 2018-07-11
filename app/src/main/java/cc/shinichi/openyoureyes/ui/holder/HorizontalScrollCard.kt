package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.ui.adapter.HorRvAdapter
import cc.shinichi.openyoureyes.widget.decoration.HorRvDecoration
import com.chad.library.adapter.base.BaseViewHolder

class HorizontalScrollCard {

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
    val recyclerView = helper.getView<RecyclerView>(R.id.rv_horizontalScrollCard)
    val manager = LinearLayoutManager(context)
    manager.orientation = LinearLayoutManager.HORIZONTAL
    recyclerView.layoutManager = manager
    recyclerView.onFlingListener = null
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
    val adapter = HorRvAdapter(context, entity.getData()?.data?.itemList)
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