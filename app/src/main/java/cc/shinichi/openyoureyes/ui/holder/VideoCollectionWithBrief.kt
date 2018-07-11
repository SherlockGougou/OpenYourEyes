package cc.shinichi.openyoureyes.ui.holder

import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import com.chad.library.adapter.base.BaseViewHolder

class VideoCollectionWithBrief {

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

  }
}