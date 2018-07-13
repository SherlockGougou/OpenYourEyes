package cc.shinichi.openyoureyes.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.ProgressBar
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.R.layout
import cc.shinichi.openyoureyes.api.Api
import cc.shinichi.openyoureyes.api.ApiListener
import cc.shinichi.openyoureyes.app.AppManager
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.constant.Code
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.model.bean.CategoryListBean
import cc.shinichi.openyoureyes.model.bean.home.HomeDataBean
import cc.shinichi.openyoureyes.model.bean.home.Item
import cc.shinichi.openyoureyes.model.entity.CategoryEntity
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.autoPlayFollowCard
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.banner
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.briefCard
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.followCard
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.horizontalScrollCard
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.pictureFollowCard
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.squareCardCollection
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.textCard
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.videoCollectionWithBrief
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.videoSmallCard
import cc.shinichi.openyoureyes.task.TaskGetConfig
import cc.shinichi.openyoureyes.ui.adapter.CategoryAdapter
import cc.shinichi.openyoureyes.ui.adapter.HomeDataAdapter
import cc.shinichi.openyoureyes.ui.holder.AutoPlayFollowCard
import cc.shinichi.openyoureyes.util.CommonUtil
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.log.ALog
import cc.shinichi.openyoureyes.widget.MyLoadMoreView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.lzy.okgo.model.Response
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.activity_home.drawable_layout_home
import kotlinx.android.synthetic.main.activity_home.recycler_category_list
import kotlinx.android.synthetic.main.activity_home.recycler_data_list_home
import kotlinx.android.synthetic.main.activity_home.swipe_refresh
import kotlinx.android.synthetic.main.activity_home.toolbar_home

class Home : BaseActivity(), Handler.Callback, OnClickListener, OnItemClickListener,
    RequestLoadMoreListener {

  private var handler: HandlerUtil.HandlerHolder? = null

  // view
  private var actionBar: ActionBar? = null
  private var emptyView: View? = null
  private var ll_retry_container: LinearLayout? = null
  private var progress_loading: ProgressBar? = null

  // data
  private var clickTime = 0L
  private lateinit var context: Context

  // category data
  private var categoryListBean: CategoryListBean? = null
  private var currentCategoryIndex = 1
  private var currentCategoryBean: CategoryListBean.Item? = null
  private var allCategoryEntity: MutableList<CategoryEntity> = ArrayList()
  private var categoryAdapter: CategoryAdapter? = null

  // home data
  private var allHomeDataEntity: MutableList<HomeDataEntity> = ArrayList()
  private var allHomeDataEntityTemp: MutableList<HomeDataEntity> = ArrayList()
  private var homeDataAdapter: HomeDataAdapter? = null
  private var nextPageUrl: String? = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super
        .onCreate(savedInstanceState)
    setContentView(layout.activity_home)
    context = this

    initView()
    initUtil()
    initData()
  }

  private fun initView() {
    setSupportActionBar(toolbar_home)
    actionBar = supportActionBar
    if (actionBar != null) {
      actionBar
          ?.setDisplayHomeAsUpEnabled(true)
      actionBar
          ?.setHomeAsUpIndicator(R.drawable.all_category_img)
    }

    // category view
    categoryAdapter = CategoryAdapter(context, allCategoryEntity)
    categoryAdapter
        ?.onItemClickListener = this
    recycler_category_list
        .layoutManager = LinearLayoutManager(context)
    recycler_category_list
        .adapter = categoryAdapter

    // home view
    emptyView = layoutInflater
        .inflate(R.layout.item_empty_view, null)
    ll_retry_container = emptyView
        ?.findViewById(R.id.ll_retry_container)
    ll_retry_container
        ?.setOnClickListener(this)
    progress_loading = emptyView
        ?.findViewById(R.id.progress_loading)
    homeDataAdapter = HomeDataAdapter(context, allHomeDataEntity)
    homeDataAdapter?.setEnableLoadMore(true)
    homeDataAdapter?.setOnLoadMoreListener(this, recycler_data_list_home)
    homeDataAdapter?.setLoadMoreView(MyLoadMoreView())
    recycler_data_list_home.layoutManager = LinearLayoutManager(context)
    recycler_data_list_home.adapter = homeDataAdapter
    recycler_data_list_home.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(
        recyclerView: RecyclerView?,
        dx: Int,
        dy: Int
      ) {
        super.onScrolled(recyclerView, dx, dy)
        var firstVisibleItem = 0
        var lastVisibleItem = 0
        if (recyclerView?.layoutManager is LinearLayoutManager) {
          firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
          lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

          //大于0说明有播放
          if (GSYVideoManager.instance().playPosition >= 0) {
            //当前播放的位置
            val position = GSYVideoManager.instance()
                .playPosition
            //对应的播放列表TAG
            if (GSYVideoManager.instance().playTag == AutoPlayFollowCard.TAG && (position < firstVisibleItem || position > lastVisibleItem)) {
              if (GSYVideoManager.isFullState(context as Activity)) {
                return
              }
              //如果滑出去了上面和下面就是否，和今日头条一样
              GSYVideoManager.releaseAllVideos()
              recyclerView.adapter.notifyDataSetChanged()
            }
          }
        }
      }

      override fun onScrollStateChanged(
        recyclerView: RecyclerView?,
        newState: Int
      ) {
        super.onScrollStateChanged(recyclerView, newState)
      }
    })

    swipe_refresh
        .setOnRefreshListener {
          getHomeNewData()
        }
  }

  private fun initUtil() {
    handler = HandlerUtil
        .HandlerHolder(this)
  }

  private fun initData() {
    // 获取分类数据
    getCategoryData()
    // 获取默认页数据
    getHomeNewData()
    // 获取配置文件
    TaskGetConfig.getConfig()
  }

  private fun getCategoryData() {
    ALog.log(TAG, "getCategoryData")
    Api
        .getInstance()
        .getAsync(this, Constant.索引列表, object : ApiListener() {

          override fun noNet() {
            super.noNet()
            getCategoryDataFromAssets()
          }

          override fun success(string: String?) {
            categoryListBean = getGson().fromJson(string, CategoryListBean::class.javaObjectType)
            if (categoryListBean != null) {
              handler
                  ?.sendEmptyMessage(Code.Success)
            } else {
              getCategoryDataFromAssets()
            }
          }

          override fun error(response: Response<String>?) {
            getCategoryDataFromAssets()
          }
        })
  }

  private fun getCategoryDataFromAssets() {
    ALog.log(TAG, "getCategoryDataFromAssets")
    categoryListBean = getGson().fromJson(
        CommonUtil.getStringFromAssets("data", "defaultConfig"),
        CategoryListBean::class.javaObjectType
    )
    if (categoryListBean != null) {
      handler
          ?.sendEmptyMessage(Code.Success)
    }
  }

  private fun setCategoryList() {
    ALog.log(TAG, "setCategoryList")
    val tabLists = categoryListBean
        ?.itemList
    allCategoryEntity
        .clear()
    allCategoryEntity
        .add(0, CategoryEntity(CategoryEntity.TYPE_HEADER, null))
    allCategoryEntity
        .add(1, CategoryEntity(CategoryEntity.TYPE_ITEM, null))
    allCategoryEntity
        .add(2, CategoryEntity(CategoryEntity.TYPE_ITEM, null))
    allCategoryEntity
        .add(3, CategoryEntity(CategoryEntity.TYPE_ITEM, null))
    allCategoryEntity
        .add(4, CategoryEntity(CategoryEntity.TYPE_ITEM_DIVIDER, null))
    if (tabLists != null) {
      for (value in tabLists) {
        allCategoryEntity
            .add(CategoryEntity(CategoryEntity.TYPE_ITEM, value))
      }
    }
    categoryAdapter
        ?.notifyDataSetChanged()
  }

  private fun getHomeNewData() {
    ALog.log(TAG, "getHomeNewData")
    var itemType: String? = null
    var itemTitle: String? = "#发现"
    var itemId: Int? = 0
    lateinit var url: String
    if (currentCategoryIndex < 5) {
      when (currentCategoryIndex) {
        1 -> {
          itemType = "发现"
          itemTitle = "#发现"
          itemId = 0
          url = Constant.发现
        }
        2 -> {
          itemType = "推荐"
          itemTitle = "#推荐"
          itemId = 0
          url = Constant.推荐
        }
        3 -> {
          itemType = "日报"
          itemTitle = "#日报"
          itemId = 0
          url = Constant.日报
        }
      }
    } else {
      currentCategoryBean = categoryListBean?.itemList?.get(currentCategoryIndex - 5)
      itemType = currentCategoryBean?.data?.follow?.itemType
      itemId = currentCategoryBean?.data?.follow?.itemId
      itemTitle = currentCategoryBean?.data?.title
      if (TextUtils.isEmpty(itemType)) {
        return
      }
      if (itemType.equals("category")) {
        url = Constant.接口前缀 + itemType + "/" + itemId
      }
    }
    if (isNull(url)) {
      return
    }
    ALog.log(TAG, "load url = $url")
    Api.getInstance().cancelAll()
    Api
        .getInstance()
        .getAsync(this, url, object : ApiListener() {

          override fun start() {
            super.start()
            actionBar?.title = itemTitle
            handler
                ?.sendEmptyMessage(Code.Refreshing)
          }

          override fun noNet() {
            handler?.sendEmptyMessage(Code.RefreshFail)
          }

          override fun success(string: String?) {
            getEntityList(string, true)
            handler
                ?.sendEmptyMessageDelayed(Code.RefreshFinish, 500)
          }

          override fun error(response: Response<String>?) {
            handler?.sendEmptyMessage(Code.RefreshFail)
          }
        })
  }

  private fun getEntityList(string: String?, isRefresh: Boolean = false) {
    val bean: HomeDataBean? = getGson().fromJson(string, HomeDataBean::class.javaObjectType)
    nextPageUrl = bean?.nextPageUrl

    if (bean?.itemList != null) {
      allHomeDataEntityTemp.clear()
      for (item: Item? in bean.itemList) {
        when (item?.type) {
          horizontalScrollCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_horizontalScrollCard, item))
          }
          textCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_textCard, item))
          }
          followCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_followCard, item))
          }
          videoSmallCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_videoSmallCard, item))
          }
          briefCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_briefCard, item))
          }
          squareCardCollection -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_squareCardCollection, item))
          }
          videoCollectionWithBrief -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_videoCollectionWithBrief, item))
          }
          autoPlayFollowCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_autoPlayFollowCard, item))
          }
          pictureFollowCard -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_pictureFollowCard, item))
          }
          banner -> {
            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_banner, item))
          }
//          DynamicInfoCard -> {
//            allHomeDataEntityTemp.add(HomeDataEntity(HomeDataEntity.TYPE_DynamicInfoCard, item))
//          }
        }
      }
      if (isRefresh) {
        handler?.sendEmptyMessage(Code.ScrollToTop)
        allHomeDataEntity.clear()
        allHomeDataEntity.addAll(allHomeDataEntityTemp)
        homeDataAdapter?.setNewData(allHomeDataEntity)
      } else {
        handler?.sendEmptyMessage(Code.LoadMoreSuccess)
      }
    }
  }

  override fun onBackPressed() {
    if (GSYVideoManager.backFromWindowFull(this)) {
      return
    }
    if (drawable_layout_home.isDrawerOpen(GravityCompat.START)) {
      drawable_layout_home
          .closeDrawer(GravityCompat.START)
    } else {
      if (System.currentTimeMillis() - clickTime > 1000) {
        toast("再按一次退出")
        clickTime = System
            .currentTimeMillis()
      } else {
        AppManager
            .getInstance()
            .exit()
      }
    }
  }

  override fun onPause() {
    super.onPause()
    GSYVideoManager.onPause()
  }

  override fun onResume() {
    super.onResume()
    GSYVideoManager.onResume()
  }

  override fun onDestroy() {
    super.onDestroy()
    GSYVideoManager.releaseAllVideos()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater
        .inflate(R.menu.menu_home_toolbar, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> {
        drawable_layout_home
            .openDrawer(GravityCompat.START)
      }
      R.id.menu_search -> ""
      R.id.menu_more -> ""
      else -> ""
    }
    return true
  }

  override fun handleMessage(msg: Message?): Boolean {
    when (msg?.what) {
      Code.Success -> {
        setCategoryList()
      }
      Code.Fail -> {
        toast("加载失败，请重试")
      }
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
        homeDataAdapter?.loadMoreComplete()
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
            ?.visibility = GONE
        progress_loading
            ?.visibility = VISIBLE
        getHomeNewData()
      }
    }
  }

  override fun onItemClick(
    adapter: BaseQuickAdapter<*, *>?,
    view: View?,
    position: Int
  ) {
    when (position) {
      0 -> IntentUtil.intent2Browser(this, Constant.作者主页)
      else -> {
        if (adapter?.getItemViewType(position) == CategoryEntity.TYPE_ITEM) {
          currentCategoryIndex = position
          if (drawable_layout_home.isDrawerOpen(GravityCompat.START)) {
            drawable_layout_home
                .closeDrawer(GravityCompat.START)
          }
          categoryAdapter?.setSelected(currentCategoryIndex)
          getHomeNewData()
        }
      }
    }
  }

  override fun onLoadMoreRequested() {
    if (isNull(nextPageUrl)) {
      handler?.sendEmptyMessage(Code.LoadMoreEnd)
      return
    }
    Api.getInstance().getAsync(this, nextPageUrl, object : ApiListener() {

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