package cc.shinichi.openyoureyesmvp.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.model.entity.HomeDataEntity
import cc.shinichi.openyoureyesmvp.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import cc.shinichi.openyoureyesmvp.util.kt_extend.Gone
import cc.shinichi.openyoureyesmvp.util.kt_extend.Visible
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class BriefCard : BaseHolder {

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
        val data = entity.getItem()?.data ?: return

        val rl_item_root = helper.getView<View>(R.id.rl_item_root)
        val img_category_icon = helper.getView<SimpleDraweeView>(R.id.img_category_icon)
        val img_category_icon_circle = helper.getView<SimpleDraweeView>(R.id.img_category_icon_circle)
        val tv_category_name = helper.getView<TextView>(R.id.tv_category_name)
        val tv_category_des = helper.getView<TextView>(R.id.tv_category_des)

        if (data.iconType.equals("round")) {
            img_category_icon.Gone()
            img_category_icon_circle.Visible()
            ImageLoader.load(data.icon, img_category_icon_circle)
        } else {
            img_category_icon_circle.Gone()
            img_category_icon.Visible()
            ImageLoader.load(data.icon, img_category_icon)
        }

        tv_category_name.text = data.title
        tv_category_des.text = data.description

        rl_item_root.setOnClickListener {
            ActionUrlUtil.jump(context, data.actionUrl)
        }
    }
}