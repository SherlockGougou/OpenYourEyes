package cc.shinichi.openyoureyesmvp.module.campaignlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.adapter.CampaignListAdapter
import cc.shinichi.openyoureyesmvp.constant.Code
import cc.shinichi.openyoureyesmvp.model.bean.CampaignListBean
import cc.shinichi.openyoureyesmvp.model.entity.CampaignListEntity
import cc.shinichi.openyoureyesmvp.module.base.BaseActivity
import cc.shinichi.openyoureyesmvp.util.ToastUtil
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.handler.HandlerUtil
import cc.shinichi.openyoureyesmvp.widget.MyLoadMoreView
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import kotlinx.android.synthetic.main.activity_campaign_list.rvCampaignList
import kotlinx.android.synthetic.main.activity_campaign_list.swipeRefresh
import kotlinx.android.synthetic.main.activity_campaign_list.toolbar

class CampaignList : BaseActivity(), Callback, RequestLoadMoreListener, ICampaignList.View {

    private lateinit var context: Context
    private lateinit var iCampaignListPresenter: ICampaignList.Presenter
    private var handler: HandlerUtil.HandlerHolder? = null

    // view
    private var actionBar: ActionBar? = null

    // data
    private var allEntity: MutableList<CampaignListEntity> = ArrayList()
    private var allEntityTemp: MutableList<CampaignListEntity> = ArrayList()
    private lateinit var adapter: CampaignListAdapter
    private var nextPageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_list)

        initUtil()
        initView()
        initData()
    }

    companion object {
        fun activityStart(context: Context) {
            val intent = Intent()
            intent.setClass(context, CampaignList::class.java)
            context.startActivity(intent)
        }
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
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            initData()
        }
        adapter = CampaignListAdapter(context, allEntity)
        adapter.setEnableLoadMore(true)
        adapter.setOnLoadMoreListener(this, rvCampaignList)
        adapter.setLoadMoreView(MyLoadMoreView())
        rvCampaignList.layoutManager = LinearLayoutManager(context)
        rvCampaignList.adapter = adapter
    }

    override fun initUtil() {
        context = this
        handler = HandlerUtil.HandlerHolder(this)
        iCampaignListPresenter = CampaignListPresenter(this, this)
    }

    fun initData() {
        iCampaignListPresenter.getData()
    }

    override fun loadFail(msg: String) {
        ToastUtil._long(msg)
    }

    override fun loadMoreFail(msg: String) {
        handler?.sendEmptyMessage(Code.LoadMoreFail)
        if (isNull(msg)) {
            return
        }
        ToastUtil._long(msg)
    }

    override fun onShowLoading() {
        handler?.sendEmptyMessage(Code.Refreshing)
    }

    override fun onHideLoading() {
        handler?.sendEmptyMessage(Code.RefreshFinish)
    }

    override fun onShowNetError() {
        ToastUtil._short("网络异常，请检查网络")
        handler?.sendEmptyMessage(Code.RefreshFinish)
    }

    override fun getEntityList(
            string: String?,
            isRefresh: Boolean
    ) {
        val bean = getGson().fromJson(string, CampaignListBean::class.javaObjectType)
        if (bean?.itemList != null) {
            nextPageUrl = bean.nextPageUrl
            allEntityTemp.clear()
            for (item in bean.itemList) {
                allEntityTemp.add(CampaignListEntity(CampaignListEntity.TYPE_Item, item))
            }
        }
        if (isRefresh) {
            handler?.sendEmptyMessage(Code.ScrollToTop)
            allEntity.clear()
            allEntity.addAll(allEntityTemp)
            adapter.setNewData(allEntity)
            handler?.sendEmptyMessage(Code.Success)
        } else {
            adapter.addData(allEntityTemp)
            handler?.sendEmptyMessage(Code.LoadMoreSuccess)
        }
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
            Code.Success -> {

            }
            Code.Fail -> {
                ToastUtil._short("加载失败，请重试")
            }
            Code.Refreshing -> {
                swipeRefresh.isRefreshing = true
            }
            Code.RefreshFinish -> {
                swipeRefresh.isRefreshing = false
            }
            Code.ScrollToTop -> {
                UIUtil.scrollToTop(rvCampaignList)
            }
            Code.LoadMoreFail -> {
                adapter.loadMoreFail()
            }
            Code.LoadMoreSuccess -> {
                adapter.loadMoreComplete()
            }
            Code.LoadMoreEnd -> {
                adapter.loadMoreEnd()
            }
        }
        return true
    }

    override fun onLoadMoreRequested() {
        if (isNull(nextPageUrl)) {
            handler?.sendEmptyMessage(Code.LoadMoreEnd)
            return
        }
        rvCampaignList.stopScroll()
        iCampaignListPresenter.getNextPageData(nextPageUrl)
    }
}
