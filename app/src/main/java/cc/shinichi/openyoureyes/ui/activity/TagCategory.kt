package cc.shinichi.openyoureyes.ui.activity

import android.content.Context
import android.content.Intent
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
import cc.shinichi.openyoureyes.constant.Code
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.model.bean.TagCategoryBean
import cc.shinichi.openyoureyes.ui.fragment.CommonListFragment
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_tag_category.appbarLayout
import kotlinx.android.synthetic.main.activity_tag_category.imgTagCategoryBg
import kotlinx.android.synthetic.main.activity_tag_category.tabLayout
import kotlinx.android.synthetic.main.activity_tag_category.toolbar
import kotlinx.android.synthetic.main.activity_tag_category.tvHeaderDes
import kotlinx.android.synthetic.main.activity_tag_category.tvHeaderTitle
import kotlinx.android.synthetic.main.activity_tag_category.tvTitle
import kotlinx.android.synthetic.main.activity_tag_category.viewPager

class TagCategory : BaseActivity(), Callback, OnClickListener {

  private lateinit var context: Context
  private var handler: HandlerUtil.HandlerHolder? = null

  // view
  private var actionBar: ActionBar? = null
  private lateinit var emptyView: View
  private lateinit var ll_retry_container: LinearLayout
  private lateinit var progress_loading: ProgressBar
  private lateinit var pagerAdapter: MyPagerAdapter

  // data
  private var tabBean: TagCategoryBean? = null
  private lateinit var tabUrl: String
  private lateinit var id: String
  private var index: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_tag_category)

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
    setSupportActionBar(toolbar)
    actionBar = supportActionBar
    if (actionBar != null) {
      actionBar
          ?.setDisplayHomeAsUpEnabled(true)
      actionBar
          ?.setHomeAsUpIndicator(R.drawable.ic_action_back)
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
      when {
        verticalOffset == 0 -> // 展开状态
          toolbar.alpha = 0f
        Math.abs(verticalOffset) == appBarLayout.totalScrollRange -> // 折叠状态
          toolbar.alpha = 1f
        else -> {
          // 中间状态
          val alpha = Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
          toolbar.alpha = alpha
        }
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
    tabUrl = intent.getStringExtra("tabUrl")
    id = intent.getStringExtra("id")
    index = intent.getIntExtra("index", 0)
    if (isNull(tabUrl)) {
      onBackPressed()
    }
    Api.getInstance()
        .getAsync(this, tabUrl + id, object : ApiListener() {

          override fun start() {
            super.start()
            handler?.sendEmptyMessage(Code.Refreshing)
          }

          override fun success(string: String?) {
            super.success(string)
            tabBean = getGson().fromJson(string, TagCategoryBean::class.javaObjectType)
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
    private var list: List<TagCategoryBean.TabInfo.Tab?>? = null

    fun setData(list: List<TagCategoryBean.TabInfo.Tab?>?) {
      this.list = list
      fragments.clear()
      if (list != null) {
        for (item in list) {
          fragments.add(CommonListFragment.newInstance(item?.apiUrl))
        }
      }
    }

    override fun getItem(position: Int): CommonListFragment {
      if (fragments.size == 0) return CommonListFragment.newInstance(Constant.discoveryUrl)
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
        progress_loading.visibility = View.VISIBLE
      }
      Code.RefreshFail -> {
        progress_loading.visibility = View.GONE
      }
      Code.RefreshFinish -> {
        if (tabBean != null && tabBean?.tabInfo?.tabList != null) {
          var url: String? = ""
          var title: String? = ""
          var des: String? = ""
          if (tabUrl == Constant.tagTabUrl) {
            url = tabBean?.tagInfo?.headerImage
            title = tabBean?.tagInfo?.name
            des = tabBean?.tagInfo?.tagVideoCount.toString() + "作品 / " +
                tabBean?.tagInfo?.tagFollowCount.toString() + "关注者 / " +
                tabBean?.tagInfo?.tagDynamicCount.toString() + "动态"
          } else if (tabUrl == Constant.categoryTabUrl) {
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