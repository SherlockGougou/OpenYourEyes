package cc.shinichi.openyoureyes.ui.adapter

import android.content.Context
import cc.shinichi.openyoureyes.model.entity.HomeDataItem
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/3/5  12:21
* description: 
*/
class HomeDataAdapter(context: Context, data: List<HomeDataItem>) :
        BaseMultiItemQuickAdapter<HomeDataItem, BaseViewHolder>(data) {

    init {

    }

    override fun convert(helper: BaseViewHolder, item: HomeDataItem) {

    }
}