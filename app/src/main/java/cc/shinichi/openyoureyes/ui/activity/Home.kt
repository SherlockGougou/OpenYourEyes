package cc.shinichi.openyoureyes.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
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
import cc.shinichi.openyoureyes.model.bean.DiscoveryBean
import cc.shinichi.openyoureyes.model.bean.DiscoveryBean.Item
import cc.shinichi.openyoureyes.model.entity.CategoryEntity
import cc.shinichi.openyoureyes.task.TaskGetConfig
import cc.shinichi.openyoureyes.ui.adapter.CategoryAdapter
import cc.shinichi.openyoureyes.util.CommonUtil
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.log.ALog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_home.drawable_layout_home
import kotlinx.android.synthetic.main.activity_home.recycler_category_list
import kotlinx.android.synthetic.main.activity_home.swipe_refresh
import kotlinx.android.synthetic.main.activity_home.toolbar_home

class Home : BaseActivity(), Handler.Callback, OnClickListener, OnItemClickListener {

  private var handler: HandlerUtil.HandlerHolder? = null

  // view
  private var actionBar: ActionBar? = null
  private var emptyView: View? = null
  private var ll_retry_container: LinearLayout? = null
  private var progress_loading: ProgressBar? = null

  // data
  private var categoryListBean: CategoryListBean? = null
  private var clickTime = 0L
  private var currentCategoryIndex = 1
  private var currentCategoryBean: CategoryListBean.Item? = null
  private var allEntity: MutableList<CategoryEntity> = ArrayList()
  private var categoryAdapter: CategoryAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super
        .onCreate(savedInstanceState)
    setContentView(layout.activity_home)

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
          ?.setHomeAsUpIndicator(R.drawable.ic_action_category)
    }
    categoryAdapter = CategoryAdapter(this, allEntity)
    categoryAdapter
        ?.onItemClickListener = this
    recycler_category_list
        .layoutManager = LinearLayoutManager(this)
    recycler_category_list
        .adapter = categoryAdapter

    emptyView = layoutInflater
        .inflate(R.layout.item_empty_view, null)
    ll_retry_container = emptyView
        ?.findViewById(R.id.ll_retry_container)
    ll_retry_container
        ?.setOnClickListener(this)
    progress_loading = emptyView
        ?.findViewById(R.id.progress_loading)

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
    allEntity
        .clear()
    allEntity
        .add(0, CategoryEntity(CategoryEntity.TYPE_HEADER, null))
    allEntity
        .add(1, CategoryEntity(CategoryEntity.TYPE_ITEM, null))
    allEntity
        .add(2, CategoryEntity(CategoryEntity.TYPE_ITEM, null))
    allEntity
        .add(3, CategoryEntity(CategoryEntity.TYPE_ITEM, null))
    allEntity
        .add(4, CategoryEntity(CategoryEntity.TYPE_ITEM_DIVIDER, null))
    if (tabLists != null) {
      for (value in tabLists) {
        allEntity
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
            handler
                ?.sendEmptyMessageDelayed(Code.RefreshFinish, 500)
            when (itemType) {
              "发现" -> setDiscoveryList(string)
              "推荐" -> setCommentList(string)
              "日报" -> setDailyList(string)
              else -> {
                setDefaultList(string)
              }
            }
          }

          override fun error(response: Response<String>?) {
            handler?.sendEmptyMessage(Code.RefreshFail)
          }
        })
  }

  private fun setDiscoveryList(string: String?) {
    val bean: DiscoveryBean? = getGson().fromJson(string, DiscoveryBean::class.javaObjectType)
    if (bean?.itemList != null) {
      val size = bean.itemList.size
      ALog.log(TAG, "bean.itemList?.size = $size")
      for ((index, item: Item?) in bean.itemList.withIndex()) {
        ALog.log(TAG, "item type in index = $index = " + item?.type)
      }
    }
  }

  private fun setCommentList(string: String?) {
    //        val bean = gson.fromJson(string, DiscoveryBean::class.java)
    //        if (bean != null) {
    //
    //        }
  }

  private fun setDailyList(string: String?) {
    //        val bean = gson.fromJson(string, DiscoveryBean::class.java)
    //        if (bean != null) {
    //
    //        }
  }

  private fun setDefaultList(string: String?) {
    //        val bean = gson.fromJson(string, DiscoveryBean::class.java)
    //        if (bean != null) {
    //
    //        }
  }

  override fun onBackPressed() {
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

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater
        .inflate(R.menu.menu_home, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> {
        drawable_layout_home
            .openDrawer(GravityCompat.START)
      }
      R.id.action_search -> ""
      R.id.action_more -> ""
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
}