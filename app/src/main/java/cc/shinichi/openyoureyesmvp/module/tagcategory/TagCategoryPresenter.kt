package cc.shinichi.openyoureyesmvp.module.tagcategory

import android.content.Context
import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.bean.TabBean
import cc.shinichi.openyoureyesmvp.module.tagcategory.ITagCategory.View
import cc.shinichi.openyoureyesmvp.task.BaseTask.Companion.getGson
import com.lzy.okgo.model.Response

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.tagcategory
 * create at 2018/9/17  15:50
 * description:
 */
class TagCategoryPresenter : ITagCategory.Presenter {

    private val context: Context
    private val iTagCategoryView: ITagCategory.View

    constructor(context: Context, iTagCategoryView: View) {
        this.context = context
        this.iTagCategoryView = iTagCategoryView
    }

    override fun getData() {
    }

    override fun getData(url: String?) {
        Api.getInstance()
                .getAsync(context, url, object : ApiListener() {

                    override fun start() {
                        super.start()
                        iTagCategoryView.onShowLoading()
                    }

                    override fun success(string: String?) {
                        super.success(string)
                        iTagCategoryView.setData(getGson().fromJson(string, TabBean::class.javaObjectType))
                    }

                    override fun error(response: Response<String>?) {
                        super.error(response)
                        iTagCategoryView.loadFail(response?.message().toString())
                    }

                    override fun noNet() {
                        super.noNet()
                        iTagCategoryView.onHideLoading()
                        iTagCategoryView.onShowNetError()
                    }

                    override fun finish() {
                        super.finish()
                        iTagCategoryView.onHideLoading()
                    }
                })
    }
}