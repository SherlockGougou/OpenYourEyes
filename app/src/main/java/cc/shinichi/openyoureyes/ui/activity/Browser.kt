package cc.shinichi.openyoureyes.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.UIUtil
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import cc.shinichi.openyoureyes.util.log.ALog
import kotlinx.android.synthetic.main.activity_browser.tv_title
import kotlinx.android.synthetic.main.activity_home.toolbar_home

class Browser : BaseActivity(), Callback {

  private lateinit var context: Context
  private var handler: HandlerUtil.HandlerHolder? = null
  private lateinit var webview: WebView
  private lateinit var webSettings: WebSettings
  private lateinit var progress_loading: ProgressBar

  private var url: String = ""
  private var title: String = ""

  // view
  private var actionBar: ActionBar? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_browser)
    context = this

    initView()
    initUtil()
    initData()
  }

  companion object {
    fun activityStart(
      context: Context,
      url: String,
      title: String
    ) {
      val intent = Intent()
      intent
          .setClass(context, Browser::class.java)
      intent.putExtra("url", url)
      intent.putExtra("title", title)
      context
          .startActivity(intent)
    }
  }

  @SuppressLint("SetJavaScriptEnabled")
  override fun initView() {
    setSupportActionBar(toolbar_home)
    actionBar = supportActionBar
    if (actionBar != null) {
      actionBar
          ?.setDisplayHomeAsUpEnabled(true)
      actionBar
          ?.setHomeAsUpIndicator(R.drawable.ic_action_back_white)
      actionBar?.title = ""
    }
    progress_loading = findViewById(R.id.progress_loading)
    webview = findViewById(R.id.web_view)
    webSettings = webview.settings

    webSettings.displayZoomControls = false
    webSettings.loadsImagesAutomatically = true
    webSettings.javaScriptCanOpenWindowsAutomatically = true//设置js可以直接打开窗口，如window.open()，默认为false
    webSettings.javaScriptEnabled = true//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
    webSettings.setSupportZoom(true)//是否可以缩放，默认true
    webSettings.builtInZoomControls = true//是否显示缩放按钮，默认false
    webSettings.useWideViewPort = true//设置此属性，可任意比例缩放。大视图模式
    webSettings.loadWithOverviewMode = true//和setUseWideViewPort(true)一起解决网页自适应问题
    webSettings.setAppCacheEnabled(true)//是否使用缓存
    webSettings.domStorageEnabled = true//DOM Storage
    webSettings.defaultTextEncodingName = "utf-8"

    webview.webViewClient = object : WebViewClient() {

      override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String
      ): Boolean {
        webview.loadUrl(url)
        return true
      }

      override fun onPageStarted(
        view: WebView,
        url: String?,
        favicon: Bitmap?
      ) {
        super.onPageStarted(view, url, favicon)
        progress_loading.visibility = View.VISIBLE
      }

      override fun onPageFinished(
        view: WebView,
        url: String?
      ) {
        super.onPageFinished(view, url)
        progress_loading.visibility = View.GONE
      }
    }
  }

  override fun initUtil() {
    handler = HandlerUtil.HandlerHolder(this)
  }

  override fun initData() {
    val intent = intent
    if (intent == null) {
      finish()
      return
    }
    url = intent.getStringExtra("url")
    title = intent.getStringExtra("title")
    ALog.log(TAG, "url = $url, title = $title")
    if (isNull(url)) {
      finish()
      return
    }
    webview.loadUrl(url)
    tv_title.text = title
  }

  override fun onBackPressed() {
    if (webview.canGoBack()) {
      webview.goBack()
      return
    }
    finish()
  }

  override fun onDestroy() {
    super.onDestroy()
    webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
    webview.clearHistory()
    (webview.parent as ViewGroup).removeView(webview)
    webview.destroy()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater
        .inflate(R.menu.menu_broswer_toolbar, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> onBackPressed()
      R.id.menu_copy -> UIUtil.copy(webview.url)
      R.id.menu_open_by_outside -> IntentUtil.intent2Browser(context, webview.url)
    }
    return true
  }

  override fun handleMessage(msg: Message?): Boolean {
    return true
  }
}