package cc.shinichi.openyoureyesmvp.mvp.view.common.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.constant.ApiConstant
import cc.shinichi.openyoureyesmvp.constant.Code
import cc.shinichi.openyoureyesmvp.model.bean.CampaignListBean
import cc.shinichi.openyoureyesmvp.model.entity.CampaignListEntity
import cc.shinichi.openyoureyesmvp.mvp.view.common.adapter.CampaignListAdapter
import cc.shinichi.openyoureyesmvp.mvp.view.common.base.BaseActivity
import cc.shinichi.openyoureyesmvp.mvp.view.common.widget.MyLoadMoreView
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.handler.HandlerUtil
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_campaign_list.rvCampaignList
import kotlinx.android.synthetic.main.activity_campaign_list.swipeRefresh
import kotlinx.android.synthetic.main.activity_campaign_list.toolbar

class CampaignList : BaseActivity(), Callback, RequestLoadMoreListener {

    private lateinit var context: Context
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
    }

    fun initData() {
        Api.getInstance()
                .getAsync(context, ApiConstant.campaignListUrl, object : ApiListener() {
                    override fun success(string: String?) {
                        super.success(string)
                        getEntityList(string, true)
                    }

                    override fun error(response: Response<String>?) {
                        super.error(response)
                        handler?.sendEmptyMessage(Code.Fail)
                    }

                    override fun start() {
                        super.start()
                        handler?.sendEmptyMessage(Code.Refreshing)
                    }

                    override fun finish() {
                        super.finish()
                        handler?.sendEmptyMessage(Code.RefreshFinish)
                    }
                })
    }

    private fun getEntityList(
            string: String?,
            isRefresh: Boolean = false
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
                toast("加载失败，请重试")
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
        Api.getInstance()
                .getAsync(context, nextPageUrl, object : ApiListener() {

                    override fun noNet() {
                        handler?.sendEmptyMessage(Code.LoadMoreFail)
                    }

                    override fun success(string: String?) {
                        getEntityList(string, false)
                    }

                    override fun error(response: Response<String>?) {
                        handler?.sendEmptyMessage(Code.LoadMoreFail)
                    }
                })
    }
}
