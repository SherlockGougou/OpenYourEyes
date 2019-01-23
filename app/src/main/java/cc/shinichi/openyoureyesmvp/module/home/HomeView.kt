package cc.shinichi.openyoureyesmvp.module.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.adapter.CategoryAdapter
import cc.shinichi.openyoureyesmvp.app.AppManager
import cc.shinichi.openyoureyesmvp.bean.CategoryListBean
import cc.shinichi.openyoureyesmvp.constant.ApiConstant
import cc.shinichi.openyoureyesmvp.constant.Code
import cc.shinichi.openyoureyesmvp.entity.CategoryEntity
import cc.shinichi.openyoureyesmvp.module.base.BaseActivity
import cc.shinichi.openyoureyesmvp.module.commonfragment.CommonListFragment
import cc.shinichi.openyoureyesmvp.util.IntentUtil
import cc.shinichi.openyoureyesmvp.util.StatusBarUtil
import cc.shinichi.openyoureyesmvp.util.ToastUtil
import cc.shinichi.openyoureyesmvp.util.handler.HandlerUtil
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import kotlinx.android.synthetic.main.activity_home.drawable_layout_home
import kotlinx.android.synthetic.main.activity_home.navigationView
import kotlinx.android.synthetic.main.activity_home.toolbar_home
import kotlinx.android.synthetic.main.activity_home.tvTitle

class HomeView : BaseActivity(), Handler.Callback, IHome.View {

    private var handler: HandlerUtil.HandlerHolder? = null

    private lateinit var homePresenter: IHome.Presenter

    // view
    private var actionBar: ActionBar? = null

    // data
    private var clickTime = 0L
    private lateinit var context: Context

    // category data
    private var categoryListBean: CategoryListBean? = null
    private var currentCategoryBean: CategoryListBean.Item? = null
    private var allCategoryEntity: MutableList<CategoryEntity> = ArrayList()
    private var categoryAdapter: CategoryAdapter? = null

    // fragment
    private lateinit var commonListFragment: CommonListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        StatusBarUtil.setStatusBarColor(this, R.color.transparent, R.color.colorPrimary)
        context = this

        initView()
        initUtil()
        initData()
    }

    override fun initView() {
        setSupportActionBar(toolbar_home)
        val toggle = ActionBarDrawerToggle(
                this, drawable_layout_home, toolbar_home, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawable_layout_home.addDrawerListener(toggle)
        toggle.syncState()

        actionBar = supportActionBar
        if (actionBar != null) {
            actionBar?.title = ""
        }
        tvTitle.text = "#发现"

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        commonListFragment = CommonListFragment.newInstance(ApiConstant.discoveryUrl)
        commonListFragment.setUrl(ApiConstant.discoveryUrl)
        fragmentTransaction.replace(R.id.fm_container, commonListFragment)
        fragmentTransaction.commit()

        // category view
        categoryAdapter = CategoryAdapter(allCategoryEntity)
        categoryAdapter
                ?.onItemClickListener = OnItemClickListener { adapter, _, position ->
            when (position) {
                0 -> IntentUtil.intent2Browser(this, ApiConstant.authorGithub)
                else -> {
                    if (adapter?.getItemViewType(position) == CategoryEntity.TYPE_ITEM) {
                        if (drawable_layout_home.isDrawerOpen(GravityCompat.START)) {
                            drawable_layout_home
                                    .closeDrawer(GravityCompat.START)
                        }
                        categoryAdapter?.setSelected(position)

                        var itemType: String? = null
                        var itemTitle: String? = "#发现"
                        var itemId: Int? = 0
                        lateinit var url: String
                        if (position < 5) {
                            when (position) {
                                1 -> {
                                    itemType = "发现"
                                    itemTitle = "#发现"
                                    itemId = 0
                                    url = ApiConstant.discoveryUrl
                                }
                                2 -> {
                                    itemType = "推荐"
                                    itemTitle = "#推荐"
                                    itemId = 0
                                    url = ApiConstant.allRecUrl
                                }
                                3 -> {
                                    itemType = "日报"
                                    itemTitle = "#日报"
                                    itemId = 0
                                    url = ApiConstant.feedUrl
                                }
                            }
                        } else {
                            currentCategoryBean = categoryListBean?.itemList?.get(position - 5)
                            itemType = currentCategoryBean?.data?.follow?.itemType
                            itemId = currentCategoryBean?.data?.follow?.itemId
                            itemTitle = currentCategoryBean?.data?.title
                            if (itemType.equals("category")) {
                                url = ApiConstant.apiPrefix + itemType + "/" + itemId
                            }
                        }
                        tvTitle.text = itemTitle
                        commonListFragment.setUrl(url)
                    }
                }
            }
        }
        val recycler_category_list = navigationView.findViewById<RecyclerView>(R.id.design_navigation_view)
        recycler_category_list.layoutManager = LinearLayoutManager(context)
        recycler_category_list
                .adapter = categoryAdapter
    }

    override fun initUtil() {
        handler = HandlerUtil
                .HandlerHolder(this)
    }

    fun initData() {
        homePresenter = HomePresenter(this, this)
        homePresenter.getData()
        homePresenter.getConfig()
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

    override fun setData(categoryListBean: CategoryListBean) {
        this.categoryListBean = categoryListBean
        handler?.sendEmptyMessage(Code.Success)
    }

    override fun loadFail(msg: String) {
        handler?.sendEmptyMessage(Code.Fail)
    }

    private fun setCategoryList() {
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

    override fun onBackPressed() {
        if (drawable_layout_home.isDrawerOpen(GravityCompat.START)) {
            drawable_layout_home
                    .closeDrawer(GravityCompat.START)
        } else {
            if (System.currentTimeMillis() - clickTime > 1000) {
                ToastUtil._short("再按一次退出")
                clickTime = System.currentTimeMillis()
            } else {
                AppManager
                        .getInstance()
                        .exit()
            }
        }
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
            R.id.menu_theme -> ""
            R.id.menu_about -> ""
        }
        return true
    }

    override fun handleMessage(msg: Message?): Boolean {
        when (msg?.what) {
            Code.Success -> {
                setCategoryList()
            }
            Code.Fail -> {
                ToastUtil._short("加载失败，请重试")
            }
        }
        return true
    }
}