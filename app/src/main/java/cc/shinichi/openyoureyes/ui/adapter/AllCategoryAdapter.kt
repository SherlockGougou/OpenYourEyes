package cc.shinichi.openyoureyes.ui.adapter

import android.content.Context
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.AllCategoryEntity
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

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

  private var context: Context = context

  init {
    addItemType(AllCategoryEntity.TYPE_Item, R.layout.item_all_category_item)
    addItemType(AllCategoryEntity.TYPE_ItemEnd, R.layout.item_video_detail_end)
  }

  override fun convert(
    helper: BaseViewHolder,
    entity: AllCategoryEntity
  ) {
    when(entity.itemType) {
      AllCategoryEntity.TYPE_Item -> {
        val imgAllCategory = helper.getView(R.id.imgAllCategory) as SimpleDraweeView
        imgAllCategory.layoutParams.height = UIUtil.getPhoneWidth() / 2
        ImageLoader.load(entity.item?.data?.image, imgAllCategory)
        helper.setText(R.id.tvAllCategory, entity.item?.data?.title.toString())
      }
      AllCategoryEntity.TYPE_ItemEnd -> {
        val tvEnd = helper.getView<TextView>(R.id.tvEnd)
        tvEnd.setTextColor(context.resources.getColor(R.color.black))
      }
    }
  }
}