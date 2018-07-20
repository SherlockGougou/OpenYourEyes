package cc.shinichi.openyoureyes.model.entity

import cc.shinichi.openyoureyes.model.bean.allCategory.AllCategoryBean
import com.chad.library.adapter.base.entity.MultiItemEntity

class AllCategoryEntity : MultiItemEntity {

  companion object {
    var TYPE_Item = 0
  }

  private var itemType: Int = 0
  var item: AllCategoryBean.Item? = null

  constructor(
    itemType: Int,
    item: AllCategoryBean.Item?
  ) {
    this
        .itemType = itemType
    this
        .item = item
  }

  override fun getItemType(): Int {
    return itemType
  }
}