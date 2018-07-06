package cc.shinichi.openyoureyes.model.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/3/5  13:50
 * description:
 */
class HomeDataEntity : MultiItemEntity {

  companion object {
    var TYPE_HorizontalScrollCard = 0
    var TYPE_textCard = 1
    var TYPE_followCard = 2
    var TYPE_videoSmallCard = 3
    var TYPE_banner = 4
    var TYPE_briefCard = 5
    var TYPE_squareCardCollection = 6
    var TYPE_videoCollectionWithBrief = 7
    var TYPE_DynamicInfoCard = 8
  }
  private var itemType: Int = 0
//  private var Home

  override fun getItemType(): Int {
    return itemType
  }
}