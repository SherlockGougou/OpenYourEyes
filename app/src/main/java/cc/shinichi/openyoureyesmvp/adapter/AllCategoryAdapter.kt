package cc.shinichi.openyoureyesmvp.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.model.entity.AllCategoryEntity
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import cc.shinichi.openyoureyesmvp.util.log.ALog
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

    private val TAG = "AllCategoryAdapter"
    private var context: Context = context
    private var list: List<AllCategoryEntity> = data

    init {
        addItemType(AllCategoryEntity.TYPE_Item, R.layout.item_all_category_item)
        addItemType(AllCategoryEntity.TYPE_ItemEnd, R.layout.item_video_detail_end)
    }

    override fun convert(
            helper: BaseViewHolder,
            entity: AllCategoryEntity
    ) {
        when (entity.itemType) {
            AllCategoryEntity.TYPE_Item -> {
                val imgAllCategory = helper.getView(R.id.imgAllCategory) as SimpleDraweeView
                imgAllCategory.layoutParams.height = UIUtil.getPhoneWidth() / 2
                ImageLoader.load(entity.item?.data?.image, imgAllCategory)
                helper.setText(R.id.tvAllCategory, entity.item?.data?.title.toString())

                imgAllCategory.setOnClickListener {
                    ActionUrlUtil.jump(context, entity.item?.data?.actionUrl)
                }
            }
            AllCategoryEntity.TYPE_ItemEnd -> {
                val tvEnd = helper.getView<TextView>(R.id.tvEnd)
                tvEnd.setTextColor(context.resources.getColor(R.color.black))
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val type = list[position].item?.type
                    ALog.log(TAG, "type = $type")
                    return when {
                        type.equals("rectangleCard") -> {
                            2
                        }
                        type.equals("squareCard") -> {
                            1
                        }
                        else -> {
                            1
                        }
                    }
                }
            }
        }
    }
}