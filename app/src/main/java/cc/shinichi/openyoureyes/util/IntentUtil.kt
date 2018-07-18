package cc.shinichi.openyoureyes.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import cc.shinichi.openyoureyes.ui.activity.Browser
import cc.shinichi.openyoureyes.ui.activity.Home
import cc.shinichi.openyoureyes.ui.activity.RankList

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/24  16:27
 * description:
 */
object IntentUtil {

  fun intent2Home(context: Context) {
    val intent = Intent()
    intent
        .setClass(context, Home::class.java)
    context
        .startActivity(intent)
  }

  fun intent2Browser(
    context: Context,
    url: String
  ) {
    val uri: Uri = Uri
        .parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context
        .startActivity(intent)
  }

  fun intent2InnerBrowser(
    context: Context,
    url: String,
    title: String
  ) {
    Browser.activityStart(context, url, title)
  }

  fun intent2RankList(
    context: Context
  ) {
    context.startActivity(Intent(context, RankList::class.java))
  }
}