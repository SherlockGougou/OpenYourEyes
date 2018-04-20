package cc.shinichi.openyoureyes.model.entity

import cc.shinichi.openyoureyes.model.data.EyeCategoryListBean.TabInfoBean.TabListBean
import com.chad.library.adapter.base.entity.MultiItemEntity

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/3/14  15:36
* description: 
*/
class CategoryListItem : MultiItemEntity {

    companion object {
        var CATEGORY: Int = 1;
    }

    private var itemType: Int = 0
    private var categoryBean: TabListBean

    constructor(itemType: Int, categoryListBean: TabListBean) {
        this.itemType = itemType
        this.categoryBean = categoryListBean
    }

    fun getData(): TabListBean {
        return categoryBean
    }

    override fun getItemType(): Int {
        return itemType
    }
}