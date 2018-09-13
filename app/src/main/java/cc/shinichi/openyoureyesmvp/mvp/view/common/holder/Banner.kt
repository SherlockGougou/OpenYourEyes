package cc.shinichi.openyoureyesmvp.mvp.view.common.holder

import android.content.Context
import android.view.View
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.model.bean.home.Data
import cc.shinichi.openyoureyesmvp.model.entity.HomeDataEntity
import cc.shinichi.openyoureyesmvp.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class Banner : BaseHolder {

    private var context: Context
    private var helper: BaseViewHolder
    private var entity: HomeDataEntity

    constructor(
            context: Context,
            helper: BaseViewHolder,
            entity: HomeDataEntity
    ) {
        this.context = context
        this.entity = entity
        this.helper = helper
        setData()
    }

    private fun setData() {
        val data: Data? = entity.getItem()?.data ?: return
        val img_banner: SimpleDraweeView = helper.getView(R.id.img_banner)
        ImageLoader.load(data?.image, img_banner)

        helper.getView<View>(R.id.layout_root_container)
                .setOnClickListener {
                    ActionUrlUtil.jump(context, data?.actionUrl)
                }
    }
}