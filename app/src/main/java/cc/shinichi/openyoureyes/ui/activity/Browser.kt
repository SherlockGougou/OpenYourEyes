package cc.shinichi.openyoureyes.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.base.BaseActivity
import cc.shinichi.openyoureyes.util.handler.HandlerUtil
import android.webkit.WebViewClient
import android.widget.ProgressBar

class Browser : BaseActivity(), Callback {

  private lateinit var context: Context
  private var handler: HandlerUtil.HandlerHolder? = null
  private lateinit var webview: WebView
  private lateinit var webSettings: WebSettings
  private lateinit var progress_loading: ProgressBar

  private var url: String = ""

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
      url: String
    ) {
      val intent = Intent()
      intent
          .setClass(context, Home::class.java)
      intent.putExtra("url", url)
      context
          .startActivity(intent)
    }
  }

  private fun initView() {
    webview = findViewById(R.id.web_view)
    webSettings = webview.settings
    webSettings.useWideViewPort = true
    webSettings.loadWithOverviewMode = true
    webSettings.setSupportZoom(true)
    webSettings.builtInZoomControls = true
    webSettings.displayZoomControls = false
    webSettings.loadsImagesAutomatically = true
    webSettings.defaultTextEncodingName = "utf-8"

    webview.webViewClient = object : WebViewClient() {

      override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest?
      ): Boolean {
        view.loadUrl(url)
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

    progress_loading = findViewById(R.id.progress_loading)
  }

  private fun initUtil() {
    handler = HandlerUtil.HandlerHolder(this)
  }

  private fun initData() {
    url = intent.getStringExtra("url")
    if (isNull(url)) {
      finish()
      return
    }
  }

  override fun onBackPressed() {
    super.onBackPressed()
    if (webview.canGoBack()) {
      webview.goBack()
      return
    }
    finish()
  }

  override fun handleMessage(msg: Message?): Boolean {
    return true
  }
}
