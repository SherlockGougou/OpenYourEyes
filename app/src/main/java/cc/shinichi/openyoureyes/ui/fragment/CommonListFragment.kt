package cc.shinichi.openyoureyes.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.api.Api
import cc.shinichi.openyoureyes.api.ApiListener
import cc.shinichi.openyoureyes.base.LazyloadFragment
import cc.shinichi.openyoureyes.constant.Code
import cc.shinichi.openyoureyes.model.bean.home.HomeDataBean
import cc.shinichi.openyoureyes.model.bean.home.Item
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.ui.adapter.HomeDataAdapter
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.log.ALog
import cc.shinichi.openyoureyes.widget.MyLoadMoreView
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_home.drawable_layout_home

class CommonListFragment : LazyloadFragment(), Handler.Callback, OnClickListener,
    RequestLoadMoreListener {

  private var handler: HandlerUtil.HandlerHolder? = null

  // view
  private lateinit var rootView: View
  private lateinit var recycler_data_list_home: RecyclerView
  private lateinit var swipe_refresh: SwipeRefreshLayout
  private lateinit var emptyView: View
  private lateinit var ll_retry_container: LinearLayout
  private lateinit var progress_loading: ProgressBar

  // home data
  private var allHomeDataEntity: MutableList<HomeDataEntity> = ArrayList()
  private var allHomeDataEntityTemp: MutableList<HomeDataEntity> = ArrayList()
  private var homeDataAdapter: HomeDataAdapter? = null
  private var url: String? = ""
  private var nextPageUrl: String? = ""

  companion object {
    fun newInstance(url: String?): CommonListFragment {
      return CommonListFragment().apply {
        val bundle = Bundle()
        bundle.putString("url", url)
        arguments = bundle
      }
    }
  }

  override fun initVariables(bundle: Bundle) {
    url = arguments?.getString("url")
  }

  override fun initTools() {
    handler = HandlerUtil
        .HandlerHolder(this)
  }

  override fun initViews(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    rootView = inflater.inflate(R.layout.fragment_common_list, container, false)
    emptyView = layoutInflater
        .inflate(R.layout.item_empty_view, null)
    ll_retry_container = emptyView
        .findViewById(R.id.ll_retry_container)
    ll_retry_container
        .setOnClickListener(this)
    progress_loading = emptyView
        .findViewById(R.id.progress_loading)

    recycler_data_list_home = rootView.findViewById(R.id.recycler_data_list_home)
    swipe_refresh = rootView.findViewById(R.id.swipe_refresh)
    swipe_refresh.setColorSchemeResources(R.color.colorPrimary)

    homeDataAdapter = HomeDataAdapter(context!!, allHomeDataEntity)
    homeDataAdapter?.setEnableLoadMore(true)
    homeDataAdapter?.setOnLoadMoreListener(this, recycler_data_list_home)
    homeDataAdapter?.setLoadMoreView(MyLoadMoreView())
    recycler_data_list_home.layoutManager = LinearLayoutManager(context)
    recycler_data_list_home.adapter = homeDataAdapter

    swipe_refresh
        .setOnRefreshListener {
          getHomeNewData()
        }
    return rootView
  }

  override fun setDefaultFragmentTitle(title: String?) {
  }

  override fun initData() {
    // 获取默认页数据
    getHomeNewData()
  }

  fun setUrl(url: String) {
    this.url = url
    getHomeNewData()
  }

  fun getRecyclerView(): RecyclerView {
    return recycler_data_list_home
  }

  private fun getHomeNewData() {
    ALog.log(TAG, "getHomeNewData")
    if (isNull(url)) {
      return
    }
    ALog.log(TAG, "load url = $url")
    Api.getInstance()
        .cancelAll()
    Api
        .getInstance()
        .getAsync(context, url, object : ApiListener() {

          override fun start() {
            super.start()
            handler
                ?.sendEmptyMessage(Code.Refreshing)
          }

          override fun noNet() {
            handler?.sendEmptyMessage(Code.RefreshFail)
          }

          override fun success(string: String?) {
            getEntityList(string, true)
          }

          override fun error(response: Response<String>?) {
            handler?.sendEmptyMessage(Code.RefreshFail)
          }
        })
  }

  private fun getEntityList(
    string: String?,
    isRefresh: Boolean = false
  ) {
    val bean: HomeDataBean? = getGson().fromJson(string, HomeDataBean::class.javaObjectType)
    nextPageUrl = bean?.nextPageUrl
    ALog.log(TAG, "getEntityList nextPageUrl = $nextPageUrl")

    if (bean?.itemList != null) {
      allHomeDataEntityTemp.clear()
      for (item: Item? in bean.itemList) {
        when (item?.type) {
          HomeDataEntity.horizontalScrollCard -> {
            allHomeDataEntityTemp.add(
                HomeDataEntity(HomeDataEntity.TYPE_horizontalScrollCard, item)
            )
          }
          HomeDataEntity.textCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_textCard, item))
          }
          HomeDataEntity.followCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_followCard, item))
          }
          HomeDataEntity.videoSmallCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_videoSmallCard, item))
          }
          HomeDataEntity.briefCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_briefCard, item))
          }
          HomeDataEntity.squareCardCollection -> {
            allHomeDataEntityTemp.add(
                HomeDataEntity(HomeDataEntity.TYPE_squareCardCollection, item)
            )
          }
          HomeDataEntity.videoCollectionWithBrief -> {
            allHomeDataEntityTemp.add(
                HomeDataEntity(HomeDataEntity.TYPE_videoCollectionWithBrief, item)
            )
          }
          HomeDataEntity.autoPlayFollowCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_autoPlayFollowCard, item))
          }
          HomeDataEntity.pictureFollowCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_pictureFollowCard, item))
          }
          HomeDataEntity.banner -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_banner, item))
          }
          HomeDataEntity.DynamicInfoCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_DynamicInfoCard, item))
          }
          HomeDataEntity.videoCollectionOfHorizontalScrollCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_videoCollectionOfHorizontalScrollCard, item))
          }
        }
      }
      if (isRefresh) {
        handler?.sendEmptyMessage(Code.ScrollToTop)
        allHomeDataEntity.clear()
        allHomeDataEntity.addAll(allHomeDataEntityTemp)
        homeDataAdapter?.setNewData(allHomeDataEntity)
        handler
            ?.sendEmptyMessageDelayed(Code.RefreshFinish, 500)
      } else {
        homeDataAdapter?.loadMoreComplete()
        handler?.sendEmptyMessage(Code.LoadMoreSuccess)
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> {
        drawable_layout_home
            .openDrawer(GravityCompat.START)
      }
    }
    return true
  }

  override fun handleMessage(msg: Message?): Boolean {
    when (msg?.what) {
      Code.Refreshing -> {
        swipe_refresh
            .isRefreshing = true
      }
      Code.RefreshFail -> {
        handler?.sendEmptyMessage(Code.RefreshFinish)
        toast("加载失败，请重试")
      }
      Code.RefreshFinish -> {
        swipe_refresh
            .isRefreshing = false
        homeDataAdapter?.setEnableLoadMore(true)
      }
      Code.ScrollToTop -> {
        UIUtil.scrollToTop(recycler_data_list_home)
      }
      Code.LoadMoreFail -> {
        homeDataAdapter?.loadMoreFail()
      }
      Code.LoadMoreSuccess -> {
        homeDataAdapter?.addData(allHomeDataEntityTemp)
      }
      Code.LoadMoreEnd -> {
        homeDataAdapter?.loadMoreEnd()
      }
      else -> toast("暂无数据")
    }
    return true
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.ll_retry_container -> {
        ll_retry_container
            .visibility = View.GONE
        progress_loading
            .visibility = View.VISIBLE
        getHomeNewData()
      }
    }
  }

  override fun onLoadMoreRequested() {
    if (isNull(nextPageUrl)) {
      handler?.sendEmptyMessage(Code.LoadMoreEnd)
      return
    }
    recycler_data_list_home.stopScroll()
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