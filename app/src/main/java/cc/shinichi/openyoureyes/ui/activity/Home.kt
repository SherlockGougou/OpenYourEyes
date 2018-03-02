package cc.shinichi.openyoureyes.ui.activity

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.R.layout
import cc.shinichi.openyoureyes.app.AppManager
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.model.data.EyeCategoryListBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.drawable_layout_home
import kotlinx.android.synthetic.main.activity_home.toolbar_home
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Home : BaseActivity() {

    private val gson: Gson = Gson()
    private var categoryBean: EyeCategoryListBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar_home)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_category)
        }
    }

    override fun onStart() {
        super.onStart()

    }

    private fun getCategoryListData() {
        val okHttpClient: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder().url(Constant.类别列表).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                if (response != null && response.isSuccessful) {
                    val json = response.body()!!.string()
                    categoryBean = gson.fromJson(json, EyeCategoryListBean::class.java)
                } else {

                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AppManager.getInstance().exit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> drawable_layout_home.openDrawer(GravityCompat.START)
            R.id.action_search -> ""
            R.id.action_more -> ""
        }
        return true
    }
}