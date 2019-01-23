package cc.shinichi.openyoureyesmvp.holder

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.app.App
import cc.shinichi.openyoureyesmvp.bean.home.Data
import cc.shinichi.openyoureyesmvp.entity.HomeDataEntity
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyesmvp.util.kt_extend.Gone
import cc.shinichi.openyoureyesmvp.util.kt_extend.Visible
import com.chad.library.adapter.base.BaseViewHolder

class TextCard : BaseHolder {

    private var context: Context
    private var helper: BaseViewHolder
    private var entity: HomeDataEntity
    private lateinit var drawable: Drawable

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
        val data: Data = entity.getItem()?.data ?: return

        drawable = App.application.resources.getDrawable(R.drawable.ic_action_choose_right)
        val tv_TextCard_header5 = helper.getView<TextView>(R.id.tv_TextCard_header5)
        val tv_TextCard_header4 = helper.getView<TextView>(R.id.tv_TextCard_header4)
        val tv_TextCard_header2 = helper.getView<TextView>(R.id.tv_TextCard_header2)
        val tv_TextCard_footer2 = helper.getView<TextView>(R.id.tv_TextCard_footer2)
        val tv_TextCard_footer1 = helper.getView<TextView>(R.id.tv_TextCard_footer1)

        if ("header5" == data.type) {
            tv_TextCard_footer1.Gone()
            tv_TextCard_footer2.Gone()
            tv_TextCard_header2.Gone()
            tv_TextCard_header4.Gone()
            tv_TextCard_header5.Visible()
            tv_TextCard_header5.text = data.text
            if (UIUtil.isNull(data.actionUrl)) {
                tv_TextCard_header5.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            } else {
                tv_TextCard_header5.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
            }
        } else if ("footer2" == data.type) {
            tv_TextCard_header2.Gone()
            tv_TextCard_header4.Gone()
            tv_TextCard_header5.Gone()
            tv_TextCard_footer1.Gone()
            tv_TextCard_footer2.Visible()
            tv_TextCard_footer2.text = data.text
            if (UIUtil.isNull(data.actionUrl)) {
                tv_TextCard_footer2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            } else {
                tv_TextCard_footer2.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
            }
        } else if ("footer1" == data.type) {
            tv_TextCard_header2.Gone()
            tv_TextCard_header4.Gone()
            tv_TextCard_header5.Gone()
            tv_TextCard_footer2.Gone()
            tv_TextCard_footer1.Visible()
            tv_TextCard_footer1.text = data.text
            if (UIUtil.isNull(data.actionUrl)) {
                tv_TextCard_footer1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            } else {
                tv_TextCard_footer1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
            }
        } else if ("header4" == data.type) {
            tv_TextCard_footer1.Gone()
            tv_TextCard_footer2.Gone()
            tv_TextCard_header2.Gone()
            tv_TextCard_header5.Gone()
            tv_TextCard_header4.Visible()
            tv_TextCard_header4.text = data.text
        } else if ("header2" == data.type) {
            tv_TextCard_footer1.Gone()
            tv_TextCard_footer2.Gone()
            tv_TextCard_header5.Gone()
            tv_TextCard_header4.Gone()
            tv_TextCard_header2.Visible()
            tv_TextCard_header2.text = data.text
        }

        val actionUrl = data.actionUrl
        tv_TextCard_header5.setOnClickListener {
            ActionUrlUtil.jump(context, actionUrl)
        }
        tv_TextCard_footer1.setOnClickListener {
            ActionUrlUtil.jump(context, actionUrl)
        }
        tv_TextCard_footer2.setOnClickListener {
            ActionUrlUtil.jump(context, actionUrl)
        }
    }
}