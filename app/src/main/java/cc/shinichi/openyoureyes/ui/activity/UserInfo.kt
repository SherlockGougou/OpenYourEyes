package cc.shinichi.openyoureyes.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.ActionBar
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import android.widget.ProgressBar
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.api.Api
import cc.shinichi.openyoureyes.api.ApiListener
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.constant.ApiConstant
import cc.shinichi.openyoureyes.constant.Code
import cc.shinichi.openyoureyes.model.bean.TabBean
import cc.shinichi.openyoureyes.ui.fragment.CommonListFragment
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_user_info.appbarLayout
import kotlinx.android.synthetic.main.activity_user_info.collapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_user_info.imgHeaderBg
import kotlinx.android.synthetic.main.activity_user_info.imgUserInfoIcon
import kotlinx.android.synthetic.main.activity_user_info.llConcern
import kotlinx.android.synthetic.main.activity_user_info.llFans
import kotlinx.android.synthetic.main.activity_user_info.tabLayout
import kotlinx.android.synthetic.main.activity_user_info.toolbar
import kotlinx.android.synthetic.main.activity_user_info.tvConcernCount
import kotlinx.android.synthetic.main.activity_user_info.tvFansCount
import kotlinx.android.synthetic.main.activity_user_info.tvProductionCount
import kotlinx.android.synthetic.main.activity_user_info.tvTitle
import kotlinx.android.synthetic.main.activity_user_info.tvUserBrief
import kotlinx.android.synthetic.main.activity_user_info.tvUserDes
import kotlinx.android.synthetic.main.activity_user_info.tvUserName
import kotlinx.android.synthetic.main.activity_user_info.viewPager

class UserInfo : BaseActivity(), Callback, OnClickListener {

  private lateinit var context: Context
  private var handler: HandlerUtil.HandlerHolder? = null

  // view
  private var actionBar: ActionBar? = null
  private lateinit var emptyView: View
  private lateinit var ll_retry_container: LinearLayout
  private lateinit var progress_loading: ProgressBar
  private lateinit var pagerAdapter: MyPagerAdapter

  // data
  private var tabBean: TabBean? = null
  private lateinit var id: String
  private lateinit var userType: String
  private var index: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_info)

    initUtil()
    initView()
    initData()
  }

  companion object {
    fun activityStart(
      context: Context,
      id: String,
      userType: String,
      index: Int
    ) {
      val intent = Intent()
      intent.setClass(context, UserInfo::class.java)
      intent.putExtra("id", id)
      intent.putExtra("userType", userType)
      intent.putExtra("index", index)
      context.startActivity(intent)
    }
  }

  override fun initView() {
    tvTitle.setTextColor(Color.TRANSPARENT)
    collapsingToolbarLayout.title = ""
    setSupportActionBar(toolbar)
    actionBar = supportActionBar
    if (actionBar != null) {
      actionBar
          ?.setDisplayHomeAsUpEnabled(true)
      actionBar
          ?.setHomeAsUpIndicator(R.drawable.ic_action_back_white)
      actionBar?.title = ""
    }
    tvTitle.setOnClickListener(this)

    emptyView = layoutInflater
        .inflate(R.layout.item_empty_view, null)
    ll_retry_container = emptyView
        .findViewById(R.id.ll_retry_container)
    ll_retry_container
        .setOnClickListener(this)
    progress_loading = emptyView
        .findViewById(R.id.progress_loading)

    appbarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
      if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
        // 折叠状态
        tvTitle.setTextColor(resources.getColor(R.color.white))
      } else {
        // 展开状态
        tvTitle.setTextColor(Color.TRANSPARENT)
      }
    }

    // viewpager
    pagerAdapter = MyPagerAdapter(supportFragmentManager)
    viewPager.currentItem = 0
    tabLayout.setupWithViewPager(viewPager)
  }

  override fun initUtil() {
    handler = HandlerUtil.HandlerHolder(this)
  }

  override fun initData() {
    id = intent.getStringExtra("id")
    userType = intent.getStringExtra("userType")
    index = intent.getIntExtra("index", 0)
    val url = ApiConstant.userInfoUrl + "userType=$userType&id=$id"
    Api.getInstance()
        .getAsync(this, url, object : ApiListener() {

          override fun start() {
            super.start()
            handler?.sendEmptyMessage(Code.Refreshing)
          }

          override fun success(string: String?) {
            super.success(string)
            tabBean = getGson().fromJson(string, TabBean::class.javaObjectType)
            handler?.sendEmptyMessage(Code.RefreshFinish)
          }

          override fun error(response: Response<String>?) {
            super.error(response)
            handler?.sendEmptyMessage(Code.RefreshFail)
          }

          override fun noNet() {
            super.noNet()
            handler?.sendEmptyMessage(Code.RefreshFail)
          }
        })
  }

  class MyPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private var fragments: MutableList<CommonListFragment> = mutableListOf()
    private var list: List<TabBean.TabInfo.Tab?>? = null

    fun setData(list: List<TabBean.TabInfo.Tab?>?) {
      this.list = list
      fragments.clear()
      if (list != null) {
        for (item in list) {
          fragments.add(CommonListFragment.newInstance(item?.apiUrl))
        }
      }
    }

    override fun getItem(position: Int): CommonListFragment {
      if (fragments.size == 0) return CommonListFragment.newInstance(ApiConstant.discoveryUrl)
      return fragments[position]
    }

    override fun getCount(): Int {
      return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return list?.get(position)
          ?.name
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

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.tv_title -> {
        UIUtil.scrollToTop(pagerAdapter.getItem(viewPager.currentItem).getRecyclerView())
      }
    }
  }

  override fun handleMessage(msg: Message?): Boolean {
    when (msg?.what) {
      Code.Refreshing -> {
        progress_loading.visibility = View.VISIBLE
      }
      Code.RefreshFail -> {
        progress_loading.visibility = View.GONE
      }
      Code.RefreshFinish -> {
        if ("NORMAL".equals(userType, true)) {
          if (tabBean != null && tabBean?.userInfo != null) {
            val cover: String? = tabBean?.userInfo?.cover
            val icon: String? = tabBean?.userInfo?.icon
            val name: String? = tabBean?.userInfo?.name
            val brief: String? = tabBean?.userInfo?.brief
            val des: String? = tabBean?.userInfo?.description

            if (!isNull(cover)) {
              ImageLoader.load(cover, imgHeaderBg)
            } else {
              ImageLoader.loadBlur(icon, imgHeaderBg)
            }
            ImageLoader.load(icon, imgUserInfoIcon)
            tvTitle.text = name
            tvUserName.text = name
            tvUserBrief.text = brief
            tvUserDes.text = des

            tvFansCount.text = tabBean?.userInfo?.followCount.toString()
            tvConcernCount.text = tabBean?.userInfo?.myFollowCount.toString()
            tvProductionCount.text = tabBean?.userInfo?.videoCount.toString()

            llConcern.setOnClickListener {

            }
            llFans.setOnClickListener {

            }

            pagerAdapter.setData(tabBean?.tabInfo?.tabList)
            val count = pagerAdapter.count
            if (count > 0) {
              viewPager.offscreenPageLimit = count - 1
            }
            viewPager.adapter = pagerAdapter
            viewPager.currentItem = index
          }
        } else if ("PGC".equals(userType, true)) {
          if (tabBean != null && tabBean?.pgcInfo != null) {
            val cover: String? = tabBean?.pgcInfo?.cover
            val icon: String? = tabBean?.pgcInfo?.icon
            val name: String? = tabBean?.pgcInfo?.name
            val brief: String? = tabBean?.pgcInfo?.brief
            val des: String? = tabBean?.pgcInfo?.description

            if (!isNull(cover)) {
              ImageLoader.load(cover, imgHeaderBg)
            } else {
              ImageLoader.loadBlur(icon, imgHeaderBg)
            }
            ImageLoader.load(icon, imgUserInfoIcon)
            tvTitle.text = name
            tvUserName.text = name
            tvUserBrief.text = brief
            tvUserDes.text = des

            tvFansCount.text = tabBean?.pgcInfo?.followCount.toString()
            tvConcernCount.text = tabBean?.pgcInfo?.myFollowCount.toString()
            tvProductionCount.text = tabBean?.pgcInfo?.videoCount.toString()

            llConcern.setOnClickListener {

            }
            llFans.setOnClickListener {

            }

            pagerAdapter.setData(tabBean?.tabInfo?.tabList)
            val count = pagerAdapter.count
            if (count > 0) {
              viewPager.offscreenPageLimit = count - 1
            }
            viewPager.adapter = pagerAdapter
            viewPager.currentItem = index
          }
        }
      }
    }
    return true
  }
}