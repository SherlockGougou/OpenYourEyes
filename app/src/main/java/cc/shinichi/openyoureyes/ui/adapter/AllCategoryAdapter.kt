package cc.shinichi.openyoureyes.ui.adapter

import android.content.Context
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.AllCategoryEntity
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/3/5  12:21
 * description:
 */
class AllCategoryAdapter(
  context: Context,
  data: List<AllCategoryEntity>
) : BaseMultiItemQuickAdapter<AllCategoryEntity, BaseViewHolder>(data) {

  init {
    addItemType(AllCategoryEntity.TYPE_Item, R.layout.item_all_category_item)
  }

  override fun convert(
    helper: BaseViewHolder,
    entity: AllCategoryEntity
  ) {
    ImageLoader.load(entity.item?.data?.image, helper.getView(R.id.imgAllCategory))

  }
}