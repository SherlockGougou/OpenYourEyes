package cc.shinichi.openyoureyes.ui.activity

import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.ActionBar
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
import cc.shinichi.openyoureyes.model.bean.RankTabBean
import cc.shinichi.openyoureyes.ui.fragment.CommonListFragment
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_rank_list.tab_layout
import kotlinx.android.synthetic.main.activity_rank_list.toolbar
import kotlinx.android.synthetic.main.activity_rank_list.view_pager

class RankList : BaseActivity(), Callback, OnClickListener {

  private var handler: HandlerUtil.HandlerHolder? = null

  // view
  private var actionBar: ActionBar? = null
  private lateinit var emptyView: View
  private lateinit var ll_retry_container: LinearLayout
  private lateinit var progress_loading: ProgressBar
  private lateinit var pagerAdapter: MyPagerAdapter

  // data
  private var rankTabBean: RankTabBean? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_rank_list)

    initView()
    initUtil()
    initData()
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

    emptyView = layoutInflater
        .inflate(R.layout.item_empty_view, null)
    ll_retry_container = emptyView
        .findViewById(R.id.ll_retry_container)
    ll_retry_container
        .setOnClickListener(this)
    progress_loading = emptyView
        .findViewById(R.id.progress_loading)

    // viewpager
    pagerAdapter = MyPagerAdapter(supportFragmentManager)
    view_pager.currentItem = 0
    tab_layout.setupWithViewPager(view_pager)
  }

  override fun initUtil() {
    handler = HandlerUtil.HandlerHolder(this)
  }

  override fun initData() {
    Api.getInstance()
        .getAsync(this, Constant.rankListConfigUrl, object : ApiListener() {

          override fun start() {
            super.start()
            handler?.sendEmptyMessage(Code.Refreshing)
          }

          override fun success(string: String?) {
            super.success(string)
            rankTabBean = getGson().fromJson(string, RankTabBean::class.javaObjectType)
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

    private var fragments: MutableList<Fragment> = mutableListOf()

    fun setData(list: List<RankTabBean.TabInfo.Tab?>?) {
      fragments.clear()
      if (list != null) {
        for (item in list) {
          fragments.add(CommonListFragment.newInstance(item?.apiUrl))
        }
        notifyDataSetChanged()
      }
    }

    override fun getItem(position: Int): Fragment {
      if (fragments.size == 0) return CommonListFragment.newInstance(Constant.rankListConfigUrl)
      return fragments[position]
    }

    override fun getCount(): Int {
      return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return when (position) {
        0 -> "周排行"
        1 -> "月排行"
        2 -> "总排行"
        else -> "周排行"
      }
    }
  }

  override fun onClick(v: View?) {
    when(v?.id) {

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
        progress_loading.visibility = View.GONE
        if (rankTabBean != null && rankTabBean?.tabInfo?.tabList != null) {
          pagerAdapter.setData(rankTabBean?.tabInfo?.tabList)
          val count = pagerAdapter.count
          if (count > 0) {
            view_pager.offscreenPageLimit = count - 1
          }
          view_pager.adapter = pagerAdapter
        }
      }
    }
    return true
  }
}