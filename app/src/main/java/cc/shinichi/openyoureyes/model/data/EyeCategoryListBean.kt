package cc.shinichi.openyoureyes.model.data

import java.io.Serializable

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/3/1  10:50
* description: 开眼分类列表
*/
class EyeCategoryListBean : Serializable {
    private var tabInfo: TabInfoBean? = null

    fun getTabInfo(): TabInfoBean? {
        return tabInfo
    }

    fun setTabInfo(tabInfo: TabInfoBean) {
        this.tabInfo = tabInfo
    }

    class TabInfoBean {
        var defaultIdx: Int = 0
        var tabList: List<TabListBean>? = null

        class TabListBean {
            var id: Int = 0
            var name: String? = null
            var apiUrl: String? = null
        }
    }
}