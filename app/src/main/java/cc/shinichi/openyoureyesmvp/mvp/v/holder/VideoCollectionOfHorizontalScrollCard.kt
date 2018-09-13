package cc.shinichi.openyoureyesmvp.mvp.v.holder

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.app.App
import cc.shinichi.openyoureyesmvp.model.bean.home.Item
import cc.shinichi.openyoureyesmvp.model.entity.HomeDataEntity
import cc.shinichi.openyoureyesmvp.mvp.v.ui.adapter.HorRvAdapter
import cc.shinichi.openyoureyesmvp.mvp.v.widget.decoration.HorRvDecoration
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.eye.ActionUrlUtil
import com.chad.library.adapter.base.BaseViewHolder

class VideoCollectionOfHorizontalScrollCard : BaseHolder {

    private var context: Context
    private var helper: BaseViewHolder
    private var entity: HomeDataEntity
    private lateinit var drawable: Drawable

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

        val tv_TextCard = helper.getView<TextView>(R.id.tv_TextCard)
        tv_TextCard.text = data.header?.title

        if (UIUtil.isNull(data.header?.actionUrl)) {
            tv_TextCard.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        } else {
            drawable = App.application.resources.getDrawable(R.drawable.ic_action_choose_right)
            tv_TextCard.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
            tv_TextCard.setOnClickListener {
                ActionUrlUtil.jump(context, data.header?.actionUrl)
            }
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