package cc.shinichi.openyoureyesmvp.module.userinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.ActionBar
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import android.widget.ProgressBar
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyesmvp.bean.TabBean
import cc.shinichi.openyoureyesmvp.constant.ApiConstant
import cc.shinichi.openyoureyesmvp.constant.Code
import cc.shinichi.openyoureyesmvp.module.base.BaseActivity
import cc.shinichi.openyoureyesmvp.module.commonfragment.CommonListFragment
import cc.shinichi.openyoureyesmvp.util.StatusBarUtil
import cc.shinichi.openyoureyesmvp.util.ToastUtil
import cc.shinichi.openyoureyesmvp.util.UIUtil
import cc.shinichi.openyoureyesmvp.util.handler.HandlerUtil
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import cc.shinichi.openyoureyesmvp.util.kt_extend.Gone
import cc.shinichi.openyoureyesmvp.util.kt_extend.Visible
import cc.shinichi.openyoureyesmvp.util.kt_extend.setTextColorCompat
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

class UserInfo : BaseActivity(), Callback, OnClickListener, IUserInfo.View {

    private lateinit var context: Context
    private lateinit var iuserInfoPresenter: IUserInfo.Presenter
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
        StatusBarUtil.setStatusBarColor(this, R.color.transparent, R.color.colorPrimary)

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
        tvTitle.setTextColorCompat(R.color.transparent)
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

        appbarLayout.addOnOffsetChangedListener(
                OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                        // 折叠状态
                        tvTitle.setTextColorCompat(R.color.white)
                    } else {
                        // 展开状态
                        tvTitle.setTextColorCompat(R.color.transparent)
                    }
                }
        )

        // viewpager
        pagerAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.currentItem = 0
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun initUtil() {
        handler = HandlerUtil.HandlerHolder(this)
        iuserInfoPresenter = UserInfoPresenter(this, this)
    }

    fun initData() {
        id = intent.getStringExtra("id")
        userType = intent.getStringExtra("userType")
        index = intent.getIntExtra("index", 0)
        val url = ApiConstant.userInfoUrl + "userType=$userType&id=$id"
        iuserInfoPresenter.getData(url)
    }

    override fun setData(tabBean: TabBean?) {
        this.tabBean = tabBean
        handler?.sendEmptyMessage(Code.RefreshFinish)
        handler?.sendEmptyMessage(Code.Success)
    }

    override fun loadFail(msg: String?) {
        if (!isNull(msg)) {
            ToastUtil._short(msg!!)
        }
        handler?.sendEmptyMessage(Code.RefreshFinish)
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
                progress_loading.Visible()
            }
            Code.RefreshFail -> {
                progress_loading.Gone()
            }
            Code.RefreshFinish -> {
                progress_loading.Gone()
            }
            Code.Success -> {
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