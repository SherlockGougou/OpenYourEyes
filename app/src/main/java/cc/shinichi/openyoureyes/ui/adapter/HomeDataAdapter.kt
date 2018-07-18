package cc.shinichi.openyoureyes.ui.adapter

import android.content.Context
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.bean.home.Item
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.ui.holder.AutoPlayFollowCard
import cc.shinichi.openyoureyes.ui.holder.Banner
import cc.shinichi.openyoureyes.ui.holder.BriefCard
import cc.shinichi.openyoureyes.ui.holder.DynamicInfoCard
import cc.shinichi.openyoureyes.ui.holder.FollowCard
import cc.shinichi.openyoureyes.ui.holder.HorizontalScrollCard
import cc.shinichi.openyoureyes.ui.holder.PictureFollowCard
import cc.shinichi.openyoureyes.ui.holder.SquareCardCollection
import cc.shinichi.openyoureyes.ui.holder.TextCard
import cc.shinichi.openyoureyes.ui.holder.VideoCollectionWithBrief
import cc.shinichi.openyoureyes.ui.holder.VideoSmallCard
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
    addItemType(HomeDataEntity.TYPE_videoCollectionWithBrief, R.layout.item_home_videocollectionwithbriefcard)
    addItemType(HomeDataEntity.TYPE_autoPlayFollowCard, R.layout.item_home_autoplay_followcard)
    addItemType(HomeDataEntity.TYPE_pictureFollowCard, R.layout.item_home_picturefollowcard)
    addItemType(HomeDataEntity.TYPE_DynamicInfoCard, R.layout.item_home_dynamicinfocard)
    addItemType(HomeDataEntity.TYPE_banner, R.layout.item_home_banner)
  }

  override fun convert(
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    val item: Item = entity.getData() ?: return
    when (item.type) {
      HomeDataEntity.horizontalScrollCard -> {
        HorizontalScrollCard(super.mContext, helper, entity)
      }
      HomeDataEntity.textCard -> {
        TextCard(super.mContext, helper, entity)
      }
      HomeDataEntity.followCard -> {
        FollowCard(helper, entity)
      }
      HomeDataEntity.videoSmallCard -> {
        VideoSmallCard(helper, entity)
      }
      HomeDataEntity.briefCard -> {
        BriefCard(helper, entity)
      }
      HomeDataEntity.squareCardCollection -> {
        SquareCardCollection(super.mContext, helper, entity)
      }
      HomeDataEntity.videoCollectionWithBrief -> {
        VideoCollectionWithBrief(super.mContext, helper, entity)
      }
      HomeDataEntity.DynamicInfoCard -> {
        DynamicInfoCard(super.mContext, helper, entity)
      }
      HomeDataEntity.autoPlayFollowCard -> {
        AutoPlayFollowCard(super.mContext, helper, entity)
      }
      HomeDataEntity.pictureFollowCard -> {
        PictureFollowCard(super.mContext, helper, entity)
      }
      HomeDataEntity.banner -> {
        Banner(super.mContext, helper, entity)
      }
    }
  }
}