package cc.shinichi.openyoureyesmvp.module.tagcategory

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
import kotlinx.android.synthetic.main.activity_tag_category.appbarLayout
import kotlinx.android.synthetic.main.activity_tag_category.collapsing_toolbar_layout
import kotlinx.android.synthetic.main.activity_tag_category.imgTagCategoryBg
import kotlinx.android.synthetic.main.activity_tag_category.tabLayout
import kotlinx.android.synthetic.main.activity_tag_category.toolbar
import kotlinx.android.synthetic.main.activity_tag_category.tvHeaderDes
import kotlinx.android.synthetic.main.activity_tag_category.tvHeaderTitle
import kotlinx.android.synthetic.main.activity_tag_category.tvTitle
import kotlinx.android.synthetic.main.activity_tag_category.viewPager

class TagCategory : BaseActivity(), Callback, OnClickListener, ITagCategory.View {

    private lateinit var context: Context
    private lateinit var iTagCategoryPresenter: ITagCategory.Presenter
    private var handler: HandlerUtil.HandlerHolder? = null

    // view
    private var actionBar: ActionBar? = null
    private lateinit var emptyView: View
    private lateinit var ll_retry_container: LinearLayout
    private lateinit var progress_loading: ProgressBar
    private lateinit var pagerAdapter: MyPagerAdapter

    // data
    private var tabBean: TabBean? = null
    private lateinit var tabUrl: String
    private lateinit var id: String
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_category)
        StatusBarUtil.setStatusBarColor(this, R.color.transparent, R.color.colorPrimary)

        initUtil()
        initView()
        initData()
    }

    enum class Type {
        Category,
        Tag
    }

    companion object {
        fun activityStart(
                context: Context,
                tabUrl: String,
                id: String,
                index: Int
        ) {
            val intent = Intent()
            intent.setClass(context, TagCategory::class.java)
            intent.putExtra("tabUrl", tabUrl)
            intent.putExtra("id", id)
            intent.putExtra("index", index)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        tvTitle.setTextColorCompat(R.color.transparent)
        collapsing_toolbar_layout.title = ""
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
        iTagCategoryPresenter = TagCategoryPresenter(this, this)
    }

    fun initData() {
        tabUrl = intent.getStringExtra("tabUrl")
        id = intent.getStringExtra("id")
        index = intent.getIntExtra("index", 0)
        if (isNull(tabUrl)) {
            onBackPressed()
        }
        val url = tabUrl + id
        iTagCategoryPresenter.getData(url)
    }

    override fun setData(tabBean: TabBean?) {
        this.tabBean = tabBean
        handler?.sendEmptyMessage(Code.Success)
        handler?.sendEmptyMessage(Code.RefreshFinish)
    }

    override fun loadFail(msg: String?) {
        handler?.sendEmptyMessage(Code.RefreshFinish)
        if (!isNull(msg)) {
            ToastUtil._short(msg!!)
        }
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
            return list?.get(position)?.name
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
                if (tabBean != null && tabBean?.tabInfo?.tabList != null) {
                    var url: String? = ""
                    var title: String? = ""
                    var des: String? = ""
                    if (tabUrl == ApiConstant.tagTabUrl) {
                        url = tabBean?.tagInfo?.headerImage
                        title = tabBean?.tagInfo?.name
                        des = tabBean?.tagInfo?.tagVideoCount.toString() + "作品 / " +
                                tabBean?.tagInfo?.tagFollowCount.toString() + "关注者 / " +
                                tabBean?.tagInfo?.tagDynamicCount.toString() + "动态"
                    } else if (tabUrl == ApiConstant.categoryTabUrl) {
                        url = tabBean?.categoryInfo?.headerImage
                        title = tabBean?.categoryInfo?.name
                        des = tabBean?.categoryInfo?.description
                    }
                    ImageLoader.load(url, imgTagCategoryBg)
                    tvTitle.text = title
                    tvHeaderTitle.text = title
                    tvHeaderDes.text = des

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
        return true
    }
}