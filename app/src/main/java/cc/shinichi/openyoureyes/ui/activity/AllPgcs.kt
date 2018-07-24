package cc.shinichi.openyoureyes.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.ui.fragment.CommonListFragment
import cc.shinichi.openyoureyes.util.UIUtil
import kotlinx.android.synthetic.main.activity_all_pgcs.toolbar
import kotlinx.android.synthetic.main.activity_all_pgcs.tv_title

class AllPgcs : BaseActivity(), OnClickListener {

  // view
  private var actionBar: ActionBar? = null
  // fragment
  private lateinit var commonListFragment: CommonListFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_all_pgcs)

    initUtil()
    initView()
    initData()
  }

  companion object {
    fun activityStart(context: Context) {
      val intent = Intent()
      intent.setClass(context, AllPgcs::class.java)
      context.startActivity(intent)
    }
  }

  override fun initUtil() {
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

    tv_title.setOnClickListener(this)

    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    commonListFragment = CommonListFragment.newInstance(Constant.allPgcsUrl)
    commonListFragment.setUrl(Constant.allPgcsUrl)
    fragmentTransaction.replace(R.id.fm_container, commonListFragment)
    fragmentTransaction.commit()
  }

  override fun initData() {
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
        UIUtil.scrollToTop(commonListFragment.getRecyclerView())
      }
    }
  }
}