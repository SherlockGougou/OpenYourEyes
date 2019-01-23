package cc.shinichi.openyoureyesmvp.adapter

import android.support.v7.widget.RecyclerView
import android.widget.RelativeLayout
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.app.App
import cc.shinichi.openyoureyesmvp.entity.CategoryEntity
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import cc.shinichi.openyoureyesmvp.util.kt_extend.Visible
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class CategoryAdapter(
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
                val rl_category_header_container = helper
                        ?.getView<RelativeLayout>(R.id.rl_category_header_container)
                val headerParams: RecyclerView.LayoutParams = rl_category_header_container?.layoutParams as RecyclerView.LayoutParams
                headerParams.topMargin = -UIUtil.getPhoneStatusHeight()
                rl_category_header_container.layoutParams
                val img_author = helper
                        .getView<SimpleDraweeView>(R.id.img_author)
                val img_author_back = helper
                        .getView<SimpleDraweeView>(R.id.img_author_back)
                if (img_author != null) {
                    ImageLoader
                            .loadResource(R.drawable.ic_author, img_author)
                }
                if (img_author_back != null) {
                    ImageLoader
                            .loadResource(R.drawable.author_bg, img_author_back)
                }
            }
            CategoryEntity.TYPE_ITEM -> {
                val img_category_icon = helper
                        ?.getView<SimpleDraweeView>(R.id.img_category_icon)
                val tv_category_name = helper
                        ?.getView<TextView>(R.id.tv_category_name)
                val tv_category_des = helper
                        ?.getView<TextView>(R.id.tv_category_des)
                val position = helper
                        ?.adapterPosition
                when (position) {
                    1 -> {
                        if (img_category_icon != null) {
                            ImageLoader
                                    .loadResource(R.drawable.ic_discovery, img_category_icon)
                        }
                        tv_category_name
                                ?.text = "#发现"
                        tv_category_des
                                ?.text = "发现精彩瞬间"
                    }
                    2 -> {
                        if (img_category_icon != null) {
                            ImageLoader
                                    .loadResource(R.drawable.ic_recommend, img_category_icon)
                        }
                        tv_category_name
                                ?.text = "#推荐"
                        tv_category_des
                                ?.text = "编辑推荐"
                    }
                    3 -> {
                        if (img_category_icon != null) {
                            ImageLoader
                                    .loadResource(R.drawable.ic_daily, img_category_icon)
                        }
                        tv_category_name
                                ?.text = "#日报"
                        tv_category_des
                                ?.text = "回顾每日精选"
                    }
                    else -> {
                        tv_category_des
                                ?.Visible()
                        if (img_category_icon != null) {
                            ImageLoader
                                    .load(item.info?.data?.icon, img_category_icon)
                        }
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
                    helper.getView<RelativeLayout>(R.id.rl_item_root)
                            ?.setBackgroundColor(
                                    super.mContext.resources.getColor(R.color.gray_e5, App.application.theme))
                } else {
                    helper?.getView<RelativeLayout>(R.id.rl_item_root)
                            ?.setBackgroundColor(
                                    super.mContext.resources.getColor(R.color.white, App.application.theme))
                }
            }
        }
    }
}