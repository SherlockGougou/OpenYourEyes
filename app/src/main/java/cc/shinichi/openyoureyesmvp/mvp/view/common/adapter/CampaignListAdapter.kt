package cc.shinichi.openyoureyesmvp.mvp.view.common.adapter

import android.content.Context
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.model.bean.CampaignListBean.Item
import cc.shinichi.openyoureyesmvp.model.entity.CampaignListEntity
import cc.shinichi.openyoureyesmvp.mvp.view.common.holder.CampaignBanner
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/3/5  12:21
 * description:
 */
class CampaignListAdapter(
        context: Context,
        data: List<CampaignListEntity>
) : BaseMultiItemQuickAdapter<CampaignListEntity, BaseViewHolder>(data) {

    init {
        addItemType(CampaignListEntity.TYPE_Item, R.layout.item_home_banner)
    }

    override fun convert(
            helper: BaseViewHolder,
            entity: CampaignListEntity
    ) {
        val item: Item = entity.item ?: return
        when (entity.itemType) {
            CampaignListEntity.TYPE_Item -> {
                CampaignBanner(super.mContext, helper, entity)
            }
        }
    }
}