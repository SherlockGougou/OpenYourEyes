package cc.shinichi.openyoureyesmvp.model.entity

import cc.shinichi.openyoureyesmvp.model.bean.AllCategoryBean
import com.chad.library.adapter.base.entity.MultiItemEntity

class AllCategoryEntity : MultiItemEntity {

    companion object {
        var TYPE_Item = 0
        var TYPE_ItemEnd = 1
    }

    private var itemType: Int = 0
    var item: AllCategoryBean.Item? = null

    constructor(
            itemType: Int,
            item: AllCategoryBean.Item?
    ) {
        this
                .itemType = itemType
        this
                .item = item
    }

    override fun getItemType(): Int {
        return itemType
    }
}