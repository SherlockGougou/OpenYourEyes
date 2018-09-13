package cc.shinichi.openyoureyesmvp.model.entity

import cc.shinichi.openyoureyesmvp.model.bean.home.Item
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
        var TYPE_autoPlayFollowCard = 8
        var TYPE_pictureFollowCard = 9
        var TYPE_banner = 10
        var TYPE_videoDetailHeader = 11
        var TYPE_videoDetailTextCardHeader = 12
        var TYPE_videoDetailSmallVideo = 13
        var TYPE_videoDetailEnd = 14
        var TYPE_videoCollectionOfHorizontalScrollCard = 15

        var horizontalScrollCard = "horizontalScrollCard"
        var textCard = "textCard"
        var followCard = "followCard"
        var videoSmallCard = "videoSmallCard"
        var briefCard = "briefCard"
        var squareCardCollection = "squareCardCollection"
        var videoCollectionWithBrief = "videoCollectionWithBrief"
        var DynamicInfoCard = "DynamicInfoCard"
        var autoPlayFollowCard = "autoPlayFollowCard"
        var pictureFollowCard = "pictureFollowCard"
        var banner = "banner"
        var videoCollectionOfHorizontalScrollCard = "videoCollectionOfHorizontalScrollCard"

        // 自定义类型
        var videoDetailHeader = "videoDetailHeader"
        var videoDetailTextCardHeader = "videoDetailTextCardHeader"
        var videoDetailSmallVideo = "videoDetailSmallVideo"
        var videoDetailEnd = "videoDetailEnd"
    }

    private var itemType: Int = 0
    private var item: Item? = null

    constructor(
            itemType: Int,
            data: Item?
    ) {
        this.itemType = itemType
        this.item = data
    }

    override fun getItemType(): Int {
        return itemType
    }

    fun getItem(): Item? {
        return item
    }
}