package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.bean.home.Tag
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.ui.adapter.HorRvAdapter
import cc.shinichi.openyoureyes.util.image.ImageLoader
import cc.shinichi.openyoureyes.widget.decoration.HorRvDecoration
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

class AutoPlayFollowCard {

  private var helper: BaseViewHolder
  private var entity: HomeDataEntity
  private lateinit var drawable: Drawable
  private var inflater: LayoutInflater

  constructor(
    context:Context,
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    this.entity = entity
    this.helper = helper
    this.inflater = LayoutInflater.from(context)
    setData()
  }

  private fun setData() {
    val data = entity.getData()?.data ?: return

    val img_follow_card_user_icon = helper.getView<SimpleDraweeView>(R.id.img_follow_card_user_icon)
    val tv_follow_card_title = helper.getView<TextView>(R.id.tv_follow_card_title)
    val tv_follow_card_des_right = helper.getView<TextView>(R.id.tv_follow_card_des_right)
    val tv_content_des = helper.getView<TextView>(R.id.tv_content_des)
    val tag_flowlayout = helper.getView<TagFlowLayout>(R.id.tag_flowlayout)
    val gsy_list_auto_player = helper.getView<StandardGSYVideoPlayer>(R.id.gsy_list_auto_player)

    ImageLoader.load(data.header?.icon, img_follow_card_user_icon)
    tv_follow_card_title.text = data.header?.issuerName
    tv_follow_card_des_right.text = data.content?.data?.title
    tv_content_des.text = data.content?.data?.description

    tag_flowlayout.adapter = object : TagAdapter<Tag>(data.content?.tag) {
      override fun getView(
        parent: FlowLayout?,
        position: Int,
        t: Tag?
      ): View {
        val tv = inflater.inflate(R.layout.item_tag_item, tag_flowlayout, false) as TextView
        tv.text = t?.name
        return tv
      }
    }

    val recyclerView = helper.getView<RecyclerView>(R.id.rv_horizontalScrollCard)
    val manager = LinearLayoutManager(recyclerView.context)
    manager.orientation = LinearLayoutManager.HORIZONTAL
    recyclerView.layoutManager = manager
    recyclerView.onFlingListener = null
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
    val adapter = HorRvAdapter(recyclerView.context, entity.getData()?.data?.itemList)
    recyclerView.adapter = adapter
    val decoration = HorRvDecoration(adapter.itemCount)
    val decorationCount = recyclerView.itemDecorationCount
    if (decorationCount > 0) {
      for (i in 0 until decorationCount) {
        recyclerView.removeItemDecorationAt(i)
      }
    }
    recyclerView.addItemDecoration(decoration)
  }
}