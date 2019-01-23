package cc.shinichi.openyoureyesmvp.module.category

import android.content.Context
import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.bean.AllCategoryBean
import cc.shinichi.openyoureyesmvp.constant.ApiConstant
import cc.shinichi.openyoureyesmvp.module.category.IAllCategory.View
import cc.shinichi.openyoureyesmvp.task.BaseTask.Companion.getGson
import com.lzy.okgo.model.Response

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.category
 * create at 2018/9/17  10:33
 * description:
 */
class AllCategoryPresenter : IAllCategory.Presenter {

    private val context: Context
    private val iCategoryView: IAllCategory.View

    constructor(context: Context, iCategoryView: View) {
        this.context = context
        this.iCategoryView = iCategoryView
    }

    override fun getData() {
        Api.getInstance()
                .getAsync(context, ApiConstant.allCategoryUrl, object : ApiListener() {
                    override fun start() {
                        super.start()
                        iCategoryView.onShowLoading()
                    }

                    override fun success(string: String?) {
                        super.success(string)
                        val categoryBean = getGson().fromJson(string, AllCategoryBean::class.javaObjectType)
                        if (categoryBean?.itemList != null) {
                            iCategoryView.setData(categoryBean)
                        } else {
                            iCategoryView.loadFail("没有更多数据")
                        }
                    }

                    override fun error(response: Response<String>?) {
                        super.error(response)
                        iCategoryView.loadFail(response?.message().toString())
                    }

                    override fun noNet() {
                        super.noNet()
                        iCategoryView.onShowNetError()
                    }

                    override fun finish() {
                        super.finish()
                        iCategoryView.onHideLoading()
                    }
                })
    }
}