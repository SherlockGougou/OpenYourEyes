package cc.shinichi.openyoureyes.ui.holder

import android.support.v7.widget.RecyclerView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import com.chad.library.adapter.base.BaseViewHolder

class HorizontalScrollCard {

  private var helper: BaseViewHolder
  private var entity: HomeDataEntity

  constructor(
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    this.entity = entity
    this.helper = helper
    setData()
  }

  private fun setData() {
    val recyclerView = helper.getView<RecyclerView>(R.id.rv_horizontalScrollCard)

  }
}