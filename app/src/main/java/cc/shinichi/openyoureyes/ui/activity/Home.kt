package cc.shinichi.openyoureyes.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout.DrawerListener
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.R.layout
import cc.shinichi.openyoureyes.api.Api
import cc.shinichi.openyoureyes.api.ApiListener
import cc.shinichi.openyoureyes.app.AppManager
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.constant.Code
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.model.data.EyeCategoryListBean
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil.HandlerHolder
import cc.shinichi.openyoureyes.util.log.ALog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.design_navigation_view
import kotlinx.android.synthetic.main.activity_home.drawable_layout_home
import kotlinx.android.synthetic.main.activity_home.toolbar_home

class Home : BaseActivity(), Handler.Callback, OnNavigationItemSelectedListener, OnClickListener, DrawerListener {

    private val TAG = "Home"
    private val gson: Gson = Gson()
    private var categoryBean: EyeCategoryListBean? = null
    private val handler: HandlerUtil.HandlerHolder = HandlerHolder(this)
    private var clickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)
        initView()
        getCategoryListData()
    }

    private fun initView() {
        setSupportActionBar(toolbar_home)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_category)
        }
        drawable_layout_home.addDrawerListener(this)
        design_navigation_view.setNavigationItemSelectedListener(this)
//        val headerView = design_navigation_view.inflateHeaderView(R.layout.nav_header_layout_home)
//        val avatar = headerView.findViewById<ImageView>(R.id.img_author)
//        val avatarBack = headerView.findViewById<ImageView>(R.id.img_author_back)
//        ImageLoader.loadAvatarResource(this, R.drawable.ic_author, avatar)
//        ImageLoader.load(this, Constant.刀剑神域壁纸1, avatarBack)
//        avatar.setOnClickListener(this)
    }

    private fun getCategoryListData() {
        Api.getInstance().GetAsync(this, Constant.类别列表, object : ApiListener {
            override fun success(string: String?) {
                ALog.Log(TAG, (string ?: ""))
                categoryBean = gson.fromJson(string, EyeCategoryListBean::class.java)
                handler.sendEmptyMessage(Code.Success)
            }

            override fun error(response: com.lzy.okgo.model.Response<String>?) {

            }
        })
    }

    private fun setMenuList() {
        val tabLists = categoryBean?.getTabInfo()?.tabList
        if (tabLists != null) {
            for ((index, value) in tabLists.withIndex()) {
                design_navigation_view.menu.add(1, index, 1, value.name)
            }
        } else {
            ALog.Log(TAG, "tabLists == null")
        }
    }

    override fun onBackPressed() {
        if (drawable_layout_home.isDrawerOpen(GravityCompat.START)) {
            drawable_layout_home.closeDrawer(GravityCompat.START)
        } else {
            if (System.currentTimeMillis() - clickTime > 500) {
                toast("再按一次退出")
                clickTime = System.currentTimeMillis()
            } else {
                AppManager.getInstance().exit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                drawable_layout_home.openDrawer(GravityCompat.START)
            }
            R.id.action_search -> ""
            R.id.action_more -> ""
            else -> ""
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        toast(item.title.toString())
        drawable_layout_home.closeDrawer(GravityCompat.START)
        return true
    }

    override fun handleMessage(msg: Message?): Boolean {
        when (msg?.what) {
            Code.Success -> setMenuList()
            else -> toast("暂无数据")
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_author -> IntentUtil.intent2Browser(this, Constant.作者主页)
        }
    }

    override fun onDrawerStateChanged(newState: Int) {
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
    }

    override fun onDrawerClosed(drawerView: View) {
//        immersionBar.statusBarColor(R.color.white).statusBarDarkFont(true).init()
    }

    override fun onDrawerOpened(drawerView: View) {
//        immersionBar.transparentStatusBar().statusBarDarkFont(false).init()
    }
}