package cc.shinichi.openyoureyesmvp.mvp.v.holder

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.model.bean.home.Item
import cc.shinichi.openyoureyesmvp.model.entity.HomeDataEntity
import cc.shinichi.openyoureyesmvp.mvp.v.ui.adapter.HorRvAdapter
import cc.shinichi.openyoureyesmvp.mvp.v.widget.decoration.HorRvDecoration
import cc.shinichi.openyoureyesmvp.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class VideoCollectionWithBrief : BaseHolder {

    private var context: Context
    private var helper: BaseViewHolder
    private var entity: HomeDataEntity

    private var list: MutableList<Item?> = mutableListOf()
    private lateinit var adapter: HorRvAdapter
    private lateinit var manager: LinearLayoutManager
    private lateinit var snapHelper: PagerSnapHelper

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

        val img_category_icon = helper.getView<SimpleDraweeView>(R.id.img_category_icon)
        val tv_category_name = helper.getView<TextView>(R.id.tv_category_name)
        val tv_category_des = helper.getView<TextView>(R.id.tv_category_des)

        tv_category_name.text = data.header?.title
        tv_category_des.text = data.header?.description
        ImageLoader.load(data.header?.icon, img_category_icon)

        helper.getView<View>(R.id.img_category_icon).setOnClickListener {
            ActionUrlUtil.jump(context, data.header?.actionUrl)
        }

        helper.getView<View>(R.id.tv_category_name).setOnClickListener {
            ActionUrlUtil.jump(context, data.header?.actionUrl)
        }

        helper.getView<View>(R.id.tv_category_des).setOnClickListener {
            ActionUrlUtil.jump(context, data.header?.actionUrl)
        }

        val recyclerView = helper.getView<RecyclerView>(R.id.rv_horizontalScrollCard)
        manager = LinearLayoutManager(recyclerView.context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = manager
        recyclerView.onFlingListener = null
        snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        if (list.size > 0) {
            list.clear()
        }
        list.addAll(entity.getItem()?.data?.itemList!!)
        adapter = HorRvAdapter(context, list)
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