package cc.shinichi.openyoureyes.ui.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.GridLayoutManager
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import kotlinx.android.synthetic.main.activity_all_category.rvAllCategory

class AllCategory : BaseActivity() {

  private var handler: HandlerUtil.HandlerHolder? = null

  // view
  private var actionBar: ActionBar? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_all_category)

    initUtil()
    initView()
    initData()
  }

  override fun initView() {
    rvAllCategory.layoutManager = GridLayoutManager(this, 2)
  }

  override fun initUtil() {
    TODO(
        "not implemented"
    ) //To change body of created functions use File | Settings | File Templates.
  }

  override fun initData() {
    TODO(
        "not implemented"
    ) //To change body of created functions use File | Settings | File Templates.
  }
}