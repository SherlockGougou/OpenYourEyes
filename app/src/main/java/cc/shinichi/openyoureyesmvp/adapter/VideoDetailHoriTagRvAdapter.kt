package cc.shinichi.openyoureyesmvp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.adapter.VideoDetailHoriTagRvAdapter.Holder
import cc.shinichi.openyoureyesmvp.model.bean.home.Tag
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import com.facebook.drawee.view.SimpleDraweeView

class VideoDetailHoriTagRvAdapter(
        private var context: Context,
        private var list: List<Tag?>?
) : RecyclerView.Adapter<Holder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): Holder {
        return Holder(
                inflater.inflate(R.layout.item_video_detail_tag_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        if (list == null || list?.size == 0) {
            return 0
        }
        return list!!.size
    }

    override fun onBindViewHolder(
            holder: Holder,
            position: Int
    ) {
        val tag = list?.get(position) ?: return
        holder.rlTagItemContainer.layoutParams.width = (UIUtil.getPhoneWidth() - UIUtil.dp2px(10) * 4) /
                3
        ImageLoader.load(tag.bgPicture, holder.imgTag)
        holder.tvTag.text = "#${tag.name}#"

        holder.rlTagItemContainer.setOnClickListener {
            ActionUrlUtil.jump(context, tag.actionUrl)
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rlTagItemContainer: RelativeLayout = itemView.findViewById(R.id.rlTagItemContainer)
        var imgTag: SimpleDraweeView = itemView.findViewById(R.id.imgTag)
        var tvTag: TextView = itemView.findViewById(R.id.tvTag)
    }
}