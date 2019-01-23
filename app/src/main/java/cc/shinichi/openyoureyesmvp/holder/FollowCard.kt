package cc.shinichi.openyoureyesmvp.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.bean.home.Data
import cc.shinichi.openyoureyesmvp.entity.HomeDataEntity
import cc.shinichi.openyoureyesmvp.util.IntentUtil
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class FollowCard : BaseHolder {

    private lateinit var context: Context
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

        val rl_hor_root = helper.getView<View>(R.id.rl_hor_root)
        val img_follow_card_img: SimpleDraweeView = helper.getView(R.id.img_follow_card_img)
        val tv_follow_time_length: TextView = helper.getView(R.id.tv_follow_time_length)
        val img_follow_card_user_icon: SimpleDraweeView = helper.getView(R.id.img_follow_card_user_icon)
        val tv_follow_card_title: TextView = helper.getView(R.id.tv_follow_card_title)
        val tv_follow_card_des: TextView = helper.getView(R.id.tv_follow_card_des)

        ImageLoader.load(data?.content?.data?.cover?.feed, img_follow_card_img)
        tv_follow_time_length.text = UIUtil.getDurationText(data?.content?.data?.duration)

        ImageLoader.load(data?.header?.icon, img_follow_card_user_icon)
        tv_follow_card_title.text = data?.header?.title
        tv_follow_card_des.text = data?.header?.description

        img_follow_card_img.setOnClickListener {
            IntentUtil.intent2VideoDetail(
                    context, entity.getItem()?.data?.content?.data?.playUrl,
                    entity.getItem()?.data?.content?.data?.id.toString(),
                    data?.content?.data?.cover?.feed
            )
        }

        helper.getView<View>(R.id.img_follow_card_user_icon).setOnClickListener {
            ActionUrlUtil.jump(context, data?.header?.actionUrl)
        }

        helper.getView<View>(R.id.rl_follow_author_container).setOnClickListener {
            ActionUrlUtil.jump(context, data?.header?.actionUrl)
        }
    }
}