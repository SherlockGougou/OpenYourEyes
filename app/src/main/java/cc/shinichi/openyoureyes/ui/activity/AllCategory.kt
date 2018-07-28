package cc.shinichi.openyoureyes.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.v7.app.ActionBar
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.view.View
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.api.Api
import cc.shinichi.openyoureyes.api.ApiListener
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.constant.ApiConstant
import cc.shinichi.openyoureyes.constant.Code
import cc.shinichi.openyoureyes.model.bean.AllCategoryBean
import cc.shinichi.openyoureyes.model.bean.AllCategoryBean.Item
import cc.shinichi.openyoureyes.model.entity.AllCategoryEntity
import cc.shinichi.openyoureyes.ui.adapter.AllCategoryAdapter
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_all_category.progress_loading
import kotlinx.android.synthetic.main.activity_all_category.rvAllCategory
import kotlinx.android.synthetic.main.activity_all_category.swipe_refresh
import kotlinx.android.synthetic.main.activity_rank_list.toolbar

class AllCategory : BaseActivity(), Callback {

  private lateinit var context: Context
  private var handler: HandlerUtil.HandlerHolder? = null

  // view
  private var actionBar: ActionBar? = null

  // data
  private var allEntity: MutableList<AllCategoryEntity> = ArrayList()
  private lateinit var adapter: AllCategoryAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_all_category)

    initUtil()
    initView()
    initData()
  }

  companion object {
    fun activityStart(context: Context) {
      val intent = Intent()
      intent.setClass(context, AllCategory::class.java)
      context.startActivity(intent)
    }
  }

  override fun initUtil() {
    context = this
    handler = HandlerUtil.HandlerHolder(this)
  }

  override fun initView() {
    setSupportActionBar(toolbar)
    actionBar = supportActionBar
    if (actionBar != null) {
      actionBar
          ?.setDisplayHomeAsUpEnabled(true)
      actionBar
          ?.setHomeAsUpIndicator(R.drawable.ic_action_back_white)
      actionBar?.title = ""
    }
    swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
    swipe_refresh.setOnRefreshListener {
      initData()
    }
  }

  override fun initData() {
    Api.getInstance()
        .getAsync(context, ApiConstant.allCategoryUrl, object : ApiListener() {
          override fun start() {
            super.start()
            handler?.sendEmptyMessage(Code.Refreshing)
          }

          override fun success(string: String?) {
            super.success(string)
            val categoryBean = getGson().fromJson(string, AllCategoryBean::class.javaObjectType)
            if (categoryBean?.itemList != null) {
              allEntity.clear()
              for (item in categoryBean.itemList) {
                allEntity.add(AllCategoryEntity(AllCategoryEntity.TYPE_Item, item))
              }
              allEntity.add(AllCategoryEntity(AllCategoryEntity.TYPE_ItemEnd, Item().apply {
                type = "rectangleCard"
              }))

              val gridLayoutManager = GridLayoutManager(context, 2)
              rvAllCategory.layoutManager = gridLayoutManager
              adapter = AllCategoryAdapter(context, allEntity)
              adapter.setEnableLoadMore(false)
              rvAllCategory.adapter = adapter
            }
          }

          override fun error(response: Response<String>?) {
            super.error(response)
          }

          override fun noNet() {
            super.noNet()
            toast("无网络")
          }

          override fun finish() {
            super.finish()
            handler?.sendEmptyMessage(Code.RefreshFinish)
          }
        })
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> {
        onBackPressed()
      }
    }
    return true
  }

  override fun handleMessage(msg: Message?): Boolean {
    when (msg?.what) {
      Code.Refreshing -> {
        progress_loading.visibility = View.VISIBLE
      }
      Code.RefreshFinish -> {
        swipe_refresh.isRefreshing = false
        progress_loading.visibility = View.GONE
      }
    }
    return true
  }
}