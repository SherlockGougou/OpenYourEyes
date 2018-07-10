package cc.shinichi.openyoureyes.model.entity

import cc.shinichi.openyoureyes.model.bean.CategoryListBean
import com.chad.library.adapter.base.entity.MultiItemEntity

class CategoryEntity : MultiItemEntity {

  companion object {
    var TYPE_HEADER = 0
    var TYPE_ITEM = 1
    var TYPE_ITEM_DIVIDER = 2
  }

  private var itemType: Int = 0
  var info: CategoryListBean.Item? = null

  constructor(
    itemType: Int,
    info: CategoryListBean.Item?
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