package cc.shinichi.openyoureyes.model.entity

import cc.shinichi.openyoureyes.model.bean.home.Item
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/3/5  13:50
 * description:
 */
class HomeDataEntity : MultiItemEntity {

  companion object {
    var TYPE_horizontalScrollCard = 0
    var TYPE_textCard = 1
    var TYPE_followCard = 2
    var TYPE_videoSmallCard = 3
    var TYPE_briefCard = 4
    var TYPE_squareCardCollection = 5
    var TYPE_videoCollectionWithBrief = 6
    var TYPE_DynamicInfoCard = 7

    var horizontalScrollCard = "horizontalScrollCard"
    var textCard = "textCard"
    var followCard = "followCard"
    var videoSmallCard = "videoSmallCard"
    var briefCard = "briefCard"
    var squareCardCollection = "squareCardCollection"
    var videoCollectionWithBrief = "videoCollectionWithBrief"
    var DynamicInfoCard = "DynamicInfoCard"
  }

  private var itemType: Int = 0
  private var data: Item? = null

  constructor(
    itemType: Int,
    data: Item?
  ) {
    this.itemType = itemType
    this.data = data
  }

  override fun getItemType(): Int {
    return itemType
  }

  fun getData(): Item? {
    return data
  }
}