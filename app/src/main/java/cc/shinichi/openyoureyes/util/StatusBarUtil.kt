package cc.shinichi.openyoureyes.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import cc.shinichi.openyoureyes.util.log.ALog

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyes.util
 * create at 2018/4/25  11:45
 * description:
 */
object StatusBarUtil {

  fun setDarkStatusBar(
    activity: Activity,
    needDarkText: Boolean
  ) {
    if (RomUtil.isFlyme()) {
      FlymeStatusLight(activity, needDarkText)
    } else if (RomUtil.isMIUI()) {
      MIUIStatusLight(activity, needDarkText)
    }
  }

  private fun FlymeStatusLight(
    activity: Activity,
    needDarkText: Boolean
  ) {
    try {
      val window = activity
          .window
      val layoutParams: WindowManager.LayoutParams = window
          .attributes
      val darkFlag = WindowManager.LayoutParams::class
          .java
          .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
      val meizuFlags = WindowManager.LayoutParams::class
          .java
          .getDeclaredField("meizuFlags")
      darkFlag
          .isAccessible = true
      meizuFlags
          .isAccessible = true
      val bit = darkFlag
          .getInt(null)
      var value = meizuFlags
          .getInt(layoutParams)
      value = if (needDarkText) {
        value or bit
      } else {
        value and bit.inv()
      }
      meizuFlags
          .setInt(bit, value)
      window
          .attributes = layoutParams
    } catch (e: Exception) {
      ALog
          .log("FlymeStatusLight", e.toString())
    }
  }

  private fun MIUIStatusLight(
    activity: Activity,
    needDarkText: Boolean
  ) {
    try {
      val window = activity
          .window
      var darkModeFlag = 0
      val clz = window::class
          .java
      val layoutParams = Class
          .forName("android.view.MiuiWindowManager\$LayoutParams")
      val field = layoutParams
          .getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
      darkModeFlag = field
          .getInt(layoutParams)
      val extraFieldMethod = clz
          .getMethod("setExtraFlags", Int::class.java, Int::class.java)
      if (needDarkText) {
        extraFieldMethod
            .invoke(window, darkModeFlag, darkModeFlag) // 状态栏透明且黑色字体
      } else {
        extraFieldMethod
            .invoke(window, 0, darkModeFlag) // 清除黑色字体
      }
      // MIUI 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
      if (Build.VERSION.SDK_INT >= 23) {
        activity
            .window
            .decorView
            .systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
      } else {
        activity
            .window
            .decorView
            .systemUiVisibility = View
            .SYSTEM_UI_FLAG_VISIBLE
      }
    } catch (e: Exception) {
      ALog
          .log("MIUIStatusLight", e.toString())
    }
  }

  fun setColor(
    activity: Activity, @ColorInt color: Int,
    statusBarAlpha: Int
  ) {
    //先设置的全屏模式
    setFullScreen(activity)
    //在透明状态栏的垂直下方放置一个和状态栏同样高宽的view
    addStatusBarBehind(activity, color, statusBarAlpha)
  }

  /**
   * 添加了一个状态栏(实际上是个view)，放在了状态栏的垂直下方
   */
  fun addStatusBarBehind(
    activity: Activity, @ColorInt color: Int,
    statusBarAlpha: Int
  ) {
    //获取windowphone下的decorView
    val decorView = activity.window.decorView as ViewGroup
    val count = decorView.childCount
    //判断是否已经添加了statusBarView
    if (count > 0 && decorView.getChildAt(count - 1) is StatusBarView) {
      decorView.getChildAt(count - 1)
          .setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
    } else {
      //新建一个和状态栏高宽的view
      val statusView = createStatusBarView(activity, color, statusBarAlpha)
      decorView.addView(statusView)
    }
    setRootView(activity)
  }

  fun setTranslucentImageHeader(
    activity: Activity,
    alpha: Int,
    needOffsetView: View?
  ) {
    setFullScreen(activity)
    //获取windowphone下的decorView
    val decorView = activity.window.decorView as ViewGroup
    val count = decorView.childCount
    //判断是否已经添加了statusBarView
    if (count > 0 && decorView.getChildAt(count - 1) is StatusBarView) {
      decorView.getChildAt(count - 1)
          .setBackgroundColor(Color.argb(alpha, 0, 0, 0))
    } else {
      //新建一个和状态栏高宽的view
      val statusView = createTranslucentStatusBarView(activity, alpha)
      decorView.addView(statusView)
    }

    if (needOffsetView != null) {
      val layoutParams = needOffsetView.layoutParams as ViewGroup.MarginLayoutParams
      layoutParams.setMargins(0, getStatusBarHeight(activity), 0, 0)
    }

  }

  private fun createTranslucentStatusBarView(
    activity: Activity,
    alpha: Int
  ): StatusBarView {
    // 绘制一个和状态栏一样高的矩形
    val statusBarView = StatusBarView(activity)
    val params =
      LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
    statusBarView.layoutParams = params
    statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
    return statusBarView
  }

  /**
   * 设置根布局参数
   */
  private fun setRootView(activity: Activity) {
    val rootView =
      (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
    //rootview不会为状态栏流出状态栏空间
    ViewCompat.setFitsSystemWindows(rootView, true)
    rootView.clipToPadding = true
  }

  private fun createStatusBarView(
    activity: Activity,
    color: Int,
    alpha: Int
  ): StatusBarView {
    // 绘制一个和状态栏一样高的矩形
    val statusBarView = StatusBarView(activity)
    val params =
      LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
    statusBarView.layoutParams = params
    statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
    return statusBarView
  }

  /**
   * 获取状态栏高度
   *
   * @param context context
   * @return 状态栏高度
   */
  private fun getStatusBarHeight(context: Context): Int {
    // 获得状态栏高度
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    return context.resources.getDimensionPixelSize(resourceId)
  }

  /**
   * 计算状态栏颜色
   *
   * @param color color值
   * @param alpha alpha值
   * @return 最终的状态栏颜色
   */
  private fun calculateStatusColor(
    color: Int,
    alpha: Int
  ): Int {
    val a = 1 - alpha / 255f
    var red = color shr 16 and 0xff
    var green = color shr 8 and 0xff
    var blue = color and 0xff
    red = (red * a + 0.5).toInt()
    green = (green * a + 0.5).toInt()
    blue = (blue * a + 0.5).toInt()
    return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
  }

  fun setFullScreen(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val window = activity.window
      window.clearFlags(
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
      )
      window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      window.statusBarColor = Color.TRANSPARENT
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      // 设置透明状态栏,这样才能让 ContentView 向上
      activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
  }

  class StatusBarView : View {

    constructor(context: Context) : super(context) {}

    constructor(
      context: Context,
      attrs: AttributeSet
    ) : super(context, attrs) {
    }

    constructor(
      context: Context,
      attrs: AttributeSet,
      defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }
  }
}