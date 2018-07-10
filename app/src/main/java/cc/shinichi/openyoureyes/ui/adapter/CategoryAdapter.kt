package cc.shinichi.openyoureyes.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
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

  private var selestedIndex = 1

  init {
    addItemType(CategoryEntity.TYPE_ITEM, R.layout.item_category_item)
    addItemType(CategoryEntity.TYPE_HEADER, R.layout.item_category_header)
    addItemType(CategoryEntity.TYPE_ITEM_DIVIDER, R.layout.item_category_divider)
  }

  fun setSelected(index: Int = 1) {
    this.selestedIndex = index
    notifyDataSetChanged()
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
              .load(super.mContext, Constant.作者信息背景图, img_author_back)
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
            ImageLoader
                .loadRound(super.mContext, R.mipmap.ic_launcher, img_category_icon!!, 10)
            tv_category_name
                ?.text = "#发现"
            tv_category_des
                ?.text = "发现精彩瞬间"
          }
          2 -> {
            ImageLoader
                .loadRound(super.mContext, R.mipmap.ic_launcher, img_category_icon!!, 10)
            tv_category_name
                ?.text = "#推荐"
            tv_category_des
                ?.text = "编辑推荐"
          }
          3 -> {
            ImageLoader
                .loadRound(super.mContext, R.mipmap.ic_launcher, img_category_icon!!, 10)
            tv_category_name
                ?.text = "#日报"
            tv_category_des
                ?.text = "回顾每日精选"
          }
          else -> {
            tv_category_des
                ?.visibility = View
                .VISIBLE
            ImageLoader
                .loadRound(super.mContext, item.info?.data?.icon, img_category_icon!!, 10)
            tv_category_name
                ?.text = item
                .info
                ?.data?.title
            tv_category_des
                ?.text = item
                .info
                ?.data?.description
          }
        }
        if (selestedIndex == position) {
          helper?.getView<RelativeLayout>(R.id.rl_item_root)
              ?.setBackgroundColor(super.mContext.getColor(R.color.gray_e5))
        } else {
          helper?.getView<RelativeLayout>(R.id.rl_item_root)
              ?.setBackgroundColor(super.mContext.getColor(R.color.white))
        }
      }
    }
  }
}