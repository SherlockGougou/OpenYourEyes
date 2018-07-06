package cc.shinichi.openyoureyes.model.entity

import cc.shinichi.openyoureyes.model.bean.CategoryData
import com.chad.library.adapter.base.entity.MultiItemEntity

public class CategoryEntity : MultiItemEntity {

  companion object {
    var TYPE_HEADER = 0
    var TYPE_ITEM = 1
    var TYPE_ITEM_DIVIDER = 2
  }

  private var itemType: Int = 0
  var info: CategoryData? = null

  constructor(
    itemType: Int,
    info: CategoryData?
  ) {
    this
        .itemType = itemType
    this
        .info = info
  }

  override fun getItemType(): Int {
    return itemType
  }
}