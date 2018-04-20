package cc.shinichi.openyoureyes.ui.adapter

import android.content.Context
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.entity.CategoryListItem
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/3/14  15:34
* description: 
*/
class CategoryListAdapter(context: Context, data: List<CategoryListItem>) :
        BaseMultiItemQuickAdapter<CategoryListItem, BaseViewHolder>(data) {

    init {
        addItemType(CategoryListItem.CATEGORY, R.layout.item_category_list_item)
    }

    override fun convert(helper: BaseViewHolder, item: CategoryListItem) {
        when (helper.itemViewType) {
            CategoryListItem.CATEGORY -> {
                helper.setText(R.id.tv_category_title, item.getData().name)
                helper.setText(R.id.tv_category_subject, item.getData().apiUrl)
            }
        }
    }

}