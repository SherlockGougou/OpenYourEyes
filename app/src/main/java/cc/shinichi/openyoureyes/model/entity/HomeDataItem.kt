package cc.shinichi.openyoureyes.model.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/3/5  13:50
* description: 
*/
class HomeDataItem : MultiItemEntity {

    private var itemType: Int = 0
    private var spanSize: Int = 0

    constructor(itemType: Int, spanSize: Int, content: String) {
        this.itemType = itemType
        this.spanSize = spanSize
    }

    constructor(itemType: Int, spanSize: Int) {
        this.itemType = itemType
        this.spanSize = spanSize
    }

    override fun getItemType(): Int {
        return itemType
    }
}