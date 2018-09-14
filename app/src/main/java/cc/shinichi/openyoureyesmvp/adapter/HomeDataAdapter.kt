package cc.shinichi.openyoureyesmvp.adapter

import android.content.Context
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.model.bean.home.Item
import cc.shinichi.openyoureyesmvp.model.entity.HomeDataEntity
import cc.shinichi.openyoureyesmvp.holder.AutoPlayFollowCard
import cc.shinichi.openyoureyesmvp.holder.Banner
import cc.shinichi.openyoureyesmvp.holder.BriefCard
import cc.shinichi.openyoureyesmvp.holder.DynamicInfoCard
import cc.shinichi.openyoureyesmvp.holder.FollowCard
import cc.shinichi.openyoureyesmvp.holder.HorizontalScrollCard
import cc.shinichi.openyoureyesmvp.holder.PictureFollowCard
import cc.shinichi.openyoureyesmvp.holder.SquareCardCollection
import cc.shinichi.openyoureyesmvp.holder.TextCard
import cc.shinichi.openyoureyesmvp.holder.VideoCollectionOfHorizontalScrollCard
import cc.shinichi.openyoureyesmvp.holder.VideoCollectionWithBrief
import cc.shinichi.openyoureyesmvp.holder.VideoDetailHeader
import cc.shinichi.openyoureyesmvp.holder.VideoSmallCard
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/3/5  12:21
 * description:
 */
class HomeDataAdapter(
        context: Context,
        data: List<HomeDataEntity>
) : BaseMultiItemQuickAdapter<HomeDataEntity, BaseViewHolder>(data) {

    init {
        addItemType(HomeDataEntity.TYPE_horizontalScrollCard, R.layout.item_home_horizontalscrollcard)
        addItemType(HomeDataEntity.TYPE_textCard, R.layout.item_home_textcard)
        addItemType(HomeDataEntity.TYPE_followCard, R.layout.item_home_followcard)
        addItemType(HomeDataEntity.TYPE_videoSmallCard, R.layout.item_home_videosmallcard)
        addItemType(HomeDataEntity.TYPE_briefCard, R.layout.item_home_briefcard)
        addItemType(HomeDataEntity.TYPE_squareCardCollection, R.layout.item_home_squarecardcollection)
        addItemType(
                HomeDataEntity.TYPE_videoCollectionWithBrief,
                R.layout.item_home_videocollectionwithbriefcard
        )
        addItemType(HomeDataEntity.TYPE_autoPlayFollowCard, R.layout.item_home_autoplay_followcard)
        addItemType(HomeDataEntity.TYPE_pictureFollowCard, R.layout.item_home_picturefollowcard)
        addItemType(HomeDataEntity.TYPE_DynamicInfoCard, R.layout.item_home_dynamicinfocard)
        addItemType(HomeDataEntity.TYPE_banner, R.layout.item_home_banner)

        // video detail
        addItemType(HomeDataEntity.TYPE_videoDetailHeader, R.layout.item_video_detail_header)
        addItemType(HomeDataEntity.TYPE_videoDetailTextCardHeader, R.layout.item_video_detail_textcard)
        addItemType(
                HomeDataEntity.TYPE_videoDetailSmallVideo, R.layout.item_video_detail_videosmallcard
        )
        addItemType(HomeDataEntity.TYPE_videoDetailEnd, R.layout.item_video_detail_end)
        addItemType(HomeDataEntity.TYPE_videoCollectionOfHorizontalScrollCard,
                R.layout.item_home_videocollectionofhorizontalscrollcard)
    }

    override fun convert(
            helper: BaseViewHolder,
            entity: HomeDataEntity
    ) {
        val item: Item = entity.getItem() ?: return
        when (entity.itemType) {
            HomeDataEntity.TYPE_horizontalScrollCard -> {
                HorizontalScrollCard(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_textCard -> {
                TextCard(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_followCard -> {
                FollowCard(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_videoSmallCard, HomeDataEntity.TYPE_videoDetailSmallVideo -> {
                VideoSmallCard(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_briefCard -> {
                BriefCard(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_squareCardCollection -> {
                SquareCardCollection(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_videoCollectionWithBrief -> {
                VideoCollectionWithBrief(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_DynamicInfoCard -> {
                DynamicInfoCard(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_autoPlayFollowCard -> {
                AutoPlayFollowCard(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_pictureFollowCard -> {
                PictureFollowCard(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_banner -> {
                Banner(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_videoDetailHeader -> {
                VideoDetailHeader(super.mContext, helper, entity)
            }
            HomeDataEntity.TYPE_videoDetailTextCardHeader -> {
                helper.getView<TextView>(R.id.tv_TextCard_video_header)
                        .text = item.data?.text
            }
            HomeDataEntity.TYPE_videoDetailEnd -> {

            }
            HomeDataEntity.TYPE_videoCollectionOfHorizontalScrollCard -> {
                VideoCollectionOfHorizontalScrollCard(super.mContext, helper, entity)
            }
        }
    }
}