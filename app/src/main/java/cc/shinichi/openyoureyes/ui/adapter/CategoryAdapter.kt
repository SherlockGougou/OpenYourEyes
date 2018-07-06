package cc.shinichi.openyoureyes.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.model.entity.CategoryEntity
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class CategoryAdapter(
  context: Context,
  data: List<CategoryEntity>
) : BaseMultiItemQuickAdapter<CategoryEntity, BaseViewHolder>(data) {
  init {
    addItemType(CategoryEntity.TYPE_ITEM, R.layout.item_category_item)
    addItemType(CategoryEntity.TYPE_HEADER, R.layout.item_category_header)
    addItemType(CategoryEntity.TYPE_ITEM_DIVIDER, R.layout.item_category_divider)
  }

  override fun convert(
    helper: BaseViewHolder?,
    item: CategoryEntity?
  ) {
    when (item?.itemType) {
      CategoryEntity.TYPE_HEADER -> {
        val img_author = helper
            ?.getView<ImageView>(R.id.img_author)
        val img_author_back = helper
            ?.getView<ImageView>(R.id.img_author_back)
        if (img_author != null) {
          ImageLoader
              .loadAvatarResource(super.mContext, R.drawable.ic_author, img_author)
        }
        if (img_author_back != null) {
          ImageLoader
              .load(super.mContext, Constant.刀剑神域壁纸1, img_author_back)
        }
      }
      CategoryEntity.TYPE_ITEM -> {
        var img_category_icon = helper
            ?.getView<ImageView>(R.id.img_category_icon)
        var tv_category_name = helper
            ?.getView<TextView>(R.id.tv_category_name)
        var tv_category_des = helper
            ?.getView<TextView>(R.id.tv_category_des)
        var position = helper
            ?.adapterPosition
        when (position) {
          1 -> {
            img_category_icon
                ?.visibility = View
                .GONE
            tv_category_des
                ?.visibility = View
                .GONE
            tv_category_name
                ?.text = "发现"
          }
          2 -> {
            img_category_icon
                ?.visibility = View
                .GONE
            tv_category_des
                ?.visibility = View
                .GONE
            tv_category_name
                ?.text = "推荐"
          }
          3 -> {
            img_category_icon
                ?.visibility = View
                .GONE
            tv_category_des
                ?.visibility = View
                .GONE
            tv_category_name
                ?.text = "日报"
          }
          else -> {
            img_category_icon
                ?.visibility = View
                .VISIBLE
            tv_category_des
                ?.visibility = View
                .VISIBLE
            ImageLoader
                .loadRound(super.mContext, item.info?.icon, img_category_icon!!, 8)
            tv_category_name
                ?.text = item
                .info
                ?.title
            tv_category_des
                ?.text = item
                .info
                ?.description
          }
        }
      }
    }
  }
}