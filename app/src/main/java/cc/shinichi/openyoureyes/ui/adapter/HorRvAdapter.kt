package cc.shinichi.openyoureyes.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.bean.home.ItemX
import cc.shinichi.openyoureyes.ui.adapter.HorRvAdapter.Holder
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader

class HorRvAdapter : RecyclerView.Adapter<Holder> {

  private var context: Context
  private var list: List<ItemX?>? = null
  private var inflater: LayoutInflater

  constructor(
    context: Context,
    list: List<ItemX?>?
  ) : super() {
    this.context = context
    this.list = list
    this.inflater = LayoutInflater.from(context)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): Holder {
    return Holder(
        inflater.inflate(R.layout.item_home_followcard, parent, false)
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
    val item = list?.get(position) ?: return
    val type = item.type
    if (itemCount > 1) {
      holder.rl_hor_root.layoutParams.width = UIUtil.getPhoneWidth() - UIUtil.dp2px(10) * 3
    } else {
      holder.rl_hor_root.layoutParams.width = RecyclerView.LayoutParams.MATCH_PARENT
    }
    holder.rl_hor_root.setPadding(0, UIUtil.dp2px(10), 0, UIUtil.dp2px(10))
    when (type) {
      "banner2" -> {
        holder.tv_follow_time_length.visibility = View.GONE
        holder.tv_follow_card_des.visibility = View.GONE
        holder.tv_follow_card_title.visibility = View.GONE
        holder.img_follow_card_user_icon.visibility = View.GONE
        ImageLoader.loadRound(context, item.data?.image, holder.img_follow_card_img, 10)
      }
      "video" -> {
        holder.tv_follow_time_length.visibility = View.VISIBLE
        holder.tv_follow_card_des.visibility = View.VISIBLE
        holder.tv_follow_card_title.visibility = View.VISIBLE
        holder.img_follow_card_user_icon.visibility = View.GONE
        ImageLoader.loadRound(context, item.data?.image, holder.img_follow_card_img, 10)
        holder.tv_follow_card_title.text = item.data?.title
        holder.tv_follow_card_des.text = "#" + item.data?.category
        holder.tv_follow_time_length.text = UIUtil.getDurationText(item.data?.duration)
      }
    }
  }

  class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var rl_hor_root: RelativeLayout = itemView.findViewById(R.id.rl_hor_root)
    var img_follow_card_img: ImageView = itemView.findViewById(R.id.img_follow_card_img)
    var tv_follow_time_length: TextView = itemView.findViewById(R.id.tv_follow_time_length)
    var img_follow_card_user_icon: ImageView = itemView.findViewById(R.id.img_follow_card_user_icon)
    var tv_follow_card_title: TextView = itemView.findViewById(R.id.tv_follow_card_title)
    var tv_follow_card_des: TextView = itemView.findViewById(R.id.tv_follow_card_des)
  }
}