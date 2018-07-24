package cc.shinichi.openyoureyes.model.entity

import cc.shinichi.openyoureyes.model.bean.CampaignListBean
import com.chad.library.adapter.base.entity.MultiItemEntity

class CampaignListEntity : MultiItemEntity {

  companion object {
    var TYPE_Item = 0
  }

  private var itemType: Int = 0
  var item: CampaignListBean.Item? = null

  constructor(
    itemType: Int,
    item: CampaignListBean.Item?
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