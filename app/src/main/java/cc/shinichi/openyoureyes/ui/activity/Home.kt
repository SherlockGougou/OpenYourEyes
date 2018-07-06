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
import cc.shinichi.openyoureyes.model.bean.Category
import cc.shinichi.openyoureyes.model.bean.CategoryData
import cc.shinichi.openyoureyes.model.entity.CategoryEntity
import cc.shinichi.openyoureyes.task.TaskGetConfig
import cc.shinichi.openyoureyes.ui.adapter.CategoryAdapter
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.google.gson.Gson
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_home.drawable_layout_home
import kotlinx.android.synthetic.main.activity_home.recycler_category_list
import kotlinx.android.synthetic.main.activity_home.swipe_refresh
import kotlinx.android.synthetic.main.activity_home.toolbar_home

class Home : BaseActivity(), Handler.Callback, OnClickListener, OnItemClickListener {

  private val TAG = "Home"
  private var handler: HandlerUtil.HandlerHolder? = null
  private var gson: Gson? = null

  // view
  private var emptyView: View? = null
  private var ll_retry_container: LinearLayout? = null
  private var progress_loading: ProgressBar? = null

  // data
  private var categoryBean: Category? = null
  private var clickTime = 0L
  private var categoryItemBean: CategoryData? = null
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

  override fun onStart() {
    super.onStart()
    TaskGetConfig.getConfig()
  }

  private fun initView() {
    setSupportActionBar(toolbar_home)
    val actionBar: ActionBar? = supportActionBar
    if (actionBar != null) {
      actionBar
          .setDisplayHomeAsUpEnabled(true)
      actionBar
          .setHomeAsUpIndicator(R.drawable.ic_action_category)
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
          getHomeData()
        }
  }

  private fun initUtil() {
    gson = Gson()
    handler = HandlerUtil
        .HandlerHolder(this)
  }

  private fun initData() {
    handler
        ?.sendEmptyMessage(Code.Refreshing)
    Api
        .getInstance()
        .GetAsync(this, Constant.索引列表, object : ApiListener {
          override fun success(string: String?) {
            categoryBean = gson
                ?.fromJson(string, Category::class.javaObjectType)
            if (categoryBean != null) {
              handler
                  ?.sendEmptyMessage(Code.Success)
            } else {
              handler
                  ?.sendEmptyMessage(Code.Fail)
            }
          }

          override fun error(response: Response<String>?) {
            handler
                ?.sendEmptyMessage(Code.Fail)
          }
        })

    // 加载默认页数据
    getHomeData()
  }

  private fun setCategoryList() {
    val tabLists = categoryBean
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
            .add(CategoryEntity(CategoryEntity.TYPE_ITEM, value.data))
      }
    }
    categoryAdapter
        ?.notifyDataSetChanged()
  }

  private fun getHomeData() {
    val itemType = categoryItemBean
        ?.follow
        ?.itemType
    val itemId = categoryItemBean
        ?.follow
        ?.itemId
    if (TextUtils.isEmpty(itemType)) return
    val url = Constant.接口前缀 + itemType + "/" + itemId
    Api
        .getInstance()
        .GetAsync(this, url, object : ApiListener {
          override fun success(string: String?) {
            handler
                ?.sendEmptyMessageDelayed(Code.RefreshFinish, 500)
            when (categoryItemBean?.follow?.itemType) {
              "发现" -> setDiscoveryList(string)
              "推荐" -> setCommentList(string)
              "日报" -> setDailyList(string)
              else -> {
                setDefaultList(string)
              }
            }
          }

          override fun error(response: Response<String>?) {
            toast(response?.body().toString())
          }
        })
  }

  private fun setDiscoveryList(string: String?) {
    //        val bean = gson?.fromJson(string, DiscoveryBean::class.java)
    //        if (bean != null) {
    //            bean.itemList[0].data.
    //        }
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
      Code.Success -> setCategoryList()
      Code.Fail -> {
        toast("加载失败，点击重试")
        categoryAdapter
            ?.setEmptyView(R.layout.item_empty_view)
      }
      Code.Refreshing -> swipe_refresh
          .isRefreshing = true
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
        getHomeData()
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
      1 -> ""
      2 -> ""
      3 -> ""
      else -> ""
    }
  }
}