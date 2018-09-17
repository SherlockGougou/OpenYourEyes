package cc.shinichi.openyoureyesmvp.module.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.v7.app.ActionBar
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.adapter.AllCategoryAdapter
import cc.shinichi.openyoureyesmvp.constant.Code
import cc.shinichi.openyoureyesmvp.model.bean.AllCategoryBean
import cc.shinichi.openyoureyesmvp.model.bean.AllCategoryBean.Item
import cc.shinichi.openyoureyesmvp.model.entity.AllCategoryEntity
import cc.shinichi.openyoureyesmvp.module.base.BaseActivity
import cc.shinichi.openyoureyesmvp.util.ToastUtil
import cc.shinichi.openyoureyesmvp.util.handler.HandlerUtil
import cc.shinichi.openyoureyesmvp.util.kt_extend.Gone
import cc.shinichi.openyoureyesmvp.util.kt_extend.Visible
import kotlinx.android.synthetic.main.activity_all_category.progress_loading
import kotlinx.android.synthetic.main.activity_all_category.rvAllCategory
import kotlinx.android.synthetic.main.activity_all_category.swipe_refresh
import kotlinx.android.synthetic.main.activity_rank_list.toolbar

class AllCategoryView : BaseActivity(), Callback, IAllCategory.View {

    private lateinit var context: Context
    private lateinit var iAllCategoryPresenter: IAllCategory.Presenter
    private var handler: HandlerUtil.HandlerHolder? = null

    // view
    private var actionBar: ActionBar? = null

    // data
    private var allEntity: MutableList<AllCategoryEntity> = ArrayList()
    private lateinit var adapter: AllCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_category)

        initUtil()
        initView()
        initData()
    }

    companion object {
        fun activityStart(context: Context) {
            val intent = Intent()
            intent.setClass(context, AllCategoryView::class.java)
            context.startActivity(intent)
        }
    }

    override fun initUtil() {
        context = this
        handler = HandlerUtil.HandlerHolder(this)
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        if (actionBar != null) {
            actionBar
                    ?.setDisplayHomeAsUpEnabled(true)
            actionBar
                    ?.setHomeAsUpIndicator(R.drawable.ic_action_back_white)
            actionBar?.title = ""
        }
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh.setOnRefreshListener {
            initData()
        }
    }

    fun initData() {
        iAllCategoryPresenter = AllCategoryPresenter(this, this)
        iAllCategoryPresenter.getData()
    }

    override fun setData(categoryBean: AllCategoryBean) {
        allEntity.clear()
        if (categoryBean.itemList != null) {
            for (item in categoryBean.itemList!!) {
                allEntity.add(AllCategoryEntity(AllCategoryEntity.TYPE_Item, item))
            }
            allEntity.add(AllCategoryEntity(AllCategoryEntity.TYPE_ItemEnd, Item().apply {
                type = "rectangleCard"
            }))

            val gridLayoutManager = GridLayoutManager(context, 2)
            rvAllCategory.layoutManager = gridLayoutManager
            adapter = AllCategoryAdapter(context, allEntity)
            adapter.setEnableLoadMore(false)
            rvAllCategory.adapter = adapter
        }
    }

    override fun loadFail(msg: String) {
        handler?.sendEmptyMessage(Code.RefreshFinish)
        ToastUtil._long(msg)
    }

    override fun onShowLoading() {
        handler?.sendEmptyMessage(Code.Refreshing)
    }

    override fun onHideLoading() {
        handler?.sendEmptyMessage(Code.RefreshFinish)
    }

    override fun onShowNetError() {
        ToastUtil._long("网络异常，请检查网络")
        handler?.sendEmptyMessage(Code.RefreshFinish)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun handleMessage(msg: Message?): Boolean {
        when (msg?.what) {
            Code.Refreshing -> {
                progress_loading.Visible()
            }
            Code.RefreshFinish -> {
                swipe_refresh.isRefreshing = false
                progress_loading.Gone()
            }
        }
        return true
    }
}