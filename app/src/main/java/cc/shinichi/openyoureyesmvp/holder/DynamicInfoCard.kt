package cc.shinichi.openyoureyesmvp.holder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.entity.HomeDataEntity
import cc.shinichi.openyoureyesmvp.util.IntentUtil
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import cc.shinichi.openyoureyesmvp.util.kt_extend.Gone
import cc.shinichi.openyoureyesmvp.util.kt_extend.Visible
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class DynamicInfoCard : BaseHolder {

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

        val img_user_icon = helper.getView<SimpleDraweeView>(R.id.img_user_icon)
        val img_videoSmallCard_img = helper.getView<SimpleDraweeView>(R.id.img_videoSmallCard_img)
        val tv_user_name = helper.getView<TextView>(R.id.tv_user_name)
        val tv_user_des = helper.getView<TextView>(R.id.tv_user_des)
        val tv_dynamic_info_content = helper.getView<TextView>(R.id.tv_dynamic_info_content)
        val tv_videosmallcard_time_length = helper.getView<TextView>(R.id.tv_videosmallcard_time_length)
        val tv_videosmallcard_title = helper.getView<TextView>(R.id.tv_videosmallcard_title)
        val tv_videosmallcard_des = helper.getView<TextView>(R.id.tv_videosmallcard_des)
        val tv_reply_button = helper.getView<TextView>(R.id.tv_reply_button)
        val tv_reply_time = helper.getView<TextView>(R.id.tv_reply_time)
        val tv_like_count = helper.getView<TextView>(R.id.tv_like_count)
        val rl_relate_video_info = helper.getView<View>(R.id.rl_relate_video_info)

        val rlDynamicFollowCard = helper.getView<RelativeLayout>(R.id.rlDynamicFollowCard)
        val img_follow_user_icon = helper.getView<SimpleDraweeView>(R.id.img_follow_user_icon)
        val tv_follow_user_name = helper.getView<TextView>(R.id.tv_follow_user_name)
        val tv_follow_user_des = helper.getView<TextView>(R.id.tv_follow_user_des)

        val img_like = helper.getView<ImageView>(R.id.img_like)

        val tvHotReply = helper.getView<TextView>(R.id.tvHotReply)

        tv_user_des.text = data.text
        tv_user_name.text = data.user?.nickname
        ImageLoader.load(data.user?.avatar, img_user_icon)

        val dynamicType = data.dataType
        if ("DynamicFollowCard".equals(dynamicType, true)) {
            rl_relate_video_info.Gone()
            tv_dynamic_info_content.Gone()
            tvHotReply.Gone()
            tv_reply_button.Gone()
            tv_like_count.Gone()
            img_like.Gone()

            rlDynamicFollowCard.Visible()

            ImageLoader.load(data.briefCard?.icon, img_follow_user_icon)
            tv_follow_user_name.text = data.briefCard?.title
            tv_follow_user_des.text = data.briefCard?.description

            rlDynamicFollowCard.setOnClickListener {
                ActionUrlUtil.jump(context, data.briefCard?.actionUrl)
            }

            img_user_icon.setOnClickListener {
                ActionUrlUtil.jump(context, data.user?.actionUrl)
            }

            tv_user_name.setOnClickListener {
                ActionUrlUtil.jump(context, data.user?.actionUrl)
            }

            tv_user_des.setOnClickListener {
                ActionUrlUtil.jump(context, data.user?.actionUrl)
            }

        } else if ("DynamicReplyCard".equals(dynamicType, true)) {
            rlDynamicFollowCard.Gone()

            rl_relate_video_info.Visible()
            tvHotReply.Visible()
            tv_reply_button.Visible()
            tv_like_count.Visible()
            img_like.Visible()
            tv_videosmallcard_time_length.Visible()
            tv_videosmallcard_des.Visible()

            if (isNull(data.reply?.message)) {
                tv_dynamic_info_content.Gone()
            } else {
                tv_dynamic_info_content.Visible()
                tv_dynamic_info_content.text = data.reply?.message
            }

            ImageLoader.load(data.simpleVideo?.cover?.feed, img_videoSmallCard_img)
            tv_videosmallcard_title.text = data.simpleVideo?.title
            tv_videosmallcard_des.text = "#" + data.simpleVideo?.category

            tv_videosmallcard_time_length.text = UIUtil.getDurationText(data.simpleVideo?.duration)
            tv_reply_time.text = UIUtil.formatDate(data.createDate)
            tv_like_count.text = data.reply?.likeCount.toString()

            rl_relate_video_info.setOnClickListener {
                IntentUtil.intent2VideoDetail(
                        context, data.simpleVideo?.playUrl,
                        data.simpleVideo?.id.toString(),
                        data.simpleVideo?.cover?.feed
                )
            }

            tvHotReply.setOnClickListener {

            }

            img_user_icon.setOnClickListener {
                ActionUrlUtil.jump(context, data.user?.actionUrl)
            }

            tv_user_name.setOnClickListener {
                ActionUrlUtil.jump(context, data.user?.actionUrl)
            }

            tv_user_des.setOnClickListener {
                ActionUrlUtil.jump(context, data.user?.actionUrl)
            }
        } else if ("DynamicVideoCard".equals(dynamicType, true)) {
            val resourceType = data.simpleVideo?.resourceType
            if ("ugc_picture".equals(resourceType, true)) {
                rlDynamicFollowCard.Gone()
                tv_reply_button.Gone()

                tvHotReply.Gone()
                tv_like_count.Gone()
                img_like.Gone()
                tv_videosmallcard_time_length.Gone()
                tv_videosmallcard_des.Gone()

                rl_relate_video_info.Visible()

                if (isNull(data.reply?.message)) {
                    tv_dynamic_info_content.Gone()
                } else {
                    tv_dynamic_info_content.Visible()
                    tv_dynamic_info_content.text = data.reply?.message
                }

                ImageLoader.load(data.simpleVideo?.cover?.feed, img_videoSmallCard_img)
                tv_videosmallcard_title.text = data.simpleVideo?.title

                tv_reply_time.text = UIUtil.formatDate(data.createDate)

                rl_relate_video_info.setOnClickListener {

                }

                img_user_icon.setOnClickListener {
                    ActionUrlUtil.jump(context, data.user?.actionUrl)
                }

                tv_user_name.setOnClickListener {
                    ActionUrlUtil.jump(context, data.user?.actionUrl)
                }

                tv_user_des.setOnClickListener {
                    ActionUrlUtil.jump(context, data.user?.actionUrl)
                }
            } else if ("ugc_video".equals(resourceType, true)) {
                rlDynamicFollowCard.Gone()
                tv_reply_button.Gone()

                tvHotReply.Gone()
                tv_like_count.Gone()
                img_like.Gone()
                tv_videosmallcard_des.Gone()

                rl_relate_video_info.Visible()
                tv_videosmallcard_time_length.Visible()

                if (isNull(data.reply?.message)) {
                    tv_dynamic_info_content.Gone()
                } else {
                    tv_dynamic_info_content.Visible()
                    tv_dynamic_info_content.text = data.reply?.message
                }

                ImageLoader.load(data.simpleVideo?.cover?.feed, img_videoSmallCard_img)
                tv_videosmallcard_title.text = data.simpleVideo?.title
                tv_videosmallcard_time_length.text = UIUtil.getDurationText(data.simpleVideo?.duration)

                tv_reply_time.text = UIUtil.formatDate(data.createDate)

                rl_relate_video_info.setOnClickListener {
                    IntentUtil.intent2VideoDetail(
                            context, data.simpleVideo?.playUrl,
                            data.simpleVideo?.id.toString(),
                            data.simpleVideo?.cover?.feed
                    )
                }

                img_user_icon.setOnClickListener {
                    ActionUrlUtil.jump(context, data.user?.actionUrl)
                }

                tv_user_name.setOnClickListener {
                    ActionUrlUtil.jump(context, data.user?.actionUrl)
                }

                tv_user_des.setOnClickListener {
                    ActionUrlUtil.jump(context, data.user?.actionUrl)
                }
            } else if ("video".equals(resourceType, true)) {
                rlDynamicFollowCard.Gone()
                tv_reply_button.Gone()

                tvHotReply.Gone()
                tv_like_count.Gone()
                img_like.Gone()

                rl_relate_video_info.Visible()
                tv_videosmallcard_time_length.Visible()
                tv_videosmallcard_des.Visible()

                if (isNull(data.reply?.message)) {
                    tv_dynamic_info_content.Gone()
                } else {
                    tv_dynamic_info_content.Visible()
                    tv_dynamic_info_content.text = data.reply?.message
                }

                ImageLoader.load(data.simpleVideo?.cover?.feed, img_videoSmallCard_img)

                tv_videosmallcard_title.text = data.simpleVideo?.title
                tv_videosmallcard_des.text = "#" + data.simpleVideo?.category

                tv_videosmallcard_time_length.text = UIUtil.getDurationText(data.simpleVideo?.duration)
                tv_reply_time.text = UIUtil.formatDate(data.createDate)

                rl_relate_video_info.setOnClickListener {
                    IntentUtil.intent2VideoDetail(
                            context, data.simpleVideo?.playUrl,
                            data.simpleVideo?.id.toString(),
                            data.simpleVideo?.cover?.feed
                    )
                }

                tvHotReply.setOnClickListener {

                }

                img_user_icon.setOnClickListener {
                    ActionUrlUtil.jump(context, data.user?.actionUrl)
                }

                tv_user_name.setOnClickListener {
                    ActionUrlUtil.jump(context, data.user?.actionUrl)
                }

                tv_user_des.setOnClickListener {
                    ActionUrlUtil.jump(context, data.user?.actionUrl)
                }
            }
        }
    }
}