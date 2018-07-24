package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.view.View
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.bean.CampaignListBean.Item.Data
import cc.shinichi.openyoureyes.model.entity.CampaignListEntity
import cc.shinichi.openyoureyes.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class CampaignBanner {

  private var context: Context
  private var helper: BaseViewHolder
  private var campaignEntity: CampaignListEntity

  constructor(
    context: Context,
    helper: BaseViewHolder,
    entity: CampaignListEntity
  ) {
    this.context = context
    this.campaignEntity = entity
    this.helper = helper
    setData()
  }

  private fun setData() {
    val data: Data? = campaignEntity.item?.data ?: return
    val img_banner: SimpleDraweeView = helper.getView(R.id.img_banner)
    ImageLoader.load(data?.image, img_banner)

    helper.getView<View>(R.id.layout_root_container)
        .setOnClickListener {
          ActionUrlUtil.jump(context, data?.actionUrl)
        }
  }
}