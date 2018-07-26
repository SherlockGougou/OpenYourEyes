package cc.shinichi.openyoureyes.util.eye

import android.content.Context
import cc.shinichi.openyoureyes.constant.ApiConstant
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.UIUtil
import java.util.regex.Pattern

object ActionUrlUtil {

  fun jump(
    context: Context,
    actionUrl: String?
  ) {
    var action = UIUtil.urlDecode(actionUrl)
    if (UIUtil.isNull(action)) return

    // 浏览器：
    // eyepetizer://webview/?
    // title=广场
    // &url=http://www.kaiyanapp.com/campaign/tag_square/tag_square.html?nid=1204&tid=844&cookie=&udid=23074f87cfef48bd9a52b3ad1c054dada94e999e&shareable=true

    if (action.startsWith("eyepetizer://webview/?")) {
      action = action.replace("eyepetizer://webview/?", "")
      var url: String = ""
      var title: String = ""
      val pattern: Pattern = Pattern.compile("[a-zA-z]+://[^\\s]*")
      val matcher = pattern.matcher(action)
      if (matcher.find()) {
        url = matcher.group(0)
      }
      title = action.substring(action.indexOf("title=") + "title=".length, action.indexOf("&url="))
      IntentUtil.intent2InnerBrowser(context, url, title)
      //
    } else if (action.startsWith("eyepetizer://ranklist/")) {
      IntentUtil.intent2RankList(context)
    } else if (action.startsWith("eyepetizer://categories/all")) {
      IntentUtil.intent2AllCategoryList(context)
    } else if (action.startsWith("eyepetizer://campaign/list")) {
      IntentUtil.intent2CampaignList(context)
    } else if (action.startsWith("eyepetizer://pgcs/all")) {
      IntentUtil.intent2AllPgcs(context)
      // eyepetizer://tag/658/?title=360%C2%B0%E5%85%A8%E6%99%AF
    } else if (action.startsWith("eyepetizer://tag")) {
      var id = ""
      var index = 0
      val pattern: Pattern = Pattern.compile("//tag/(.*)/\\?")
      val matcher = pattern.matcher(action)
      if (matcher.find()) {
        id = matcher.group(1)
      }

      // eyepetizer://category/18/?title=%E8%BF%90%E5%8A%A8&tabIndex=2
      val patternIndex: Pattern = Pattern.compile("tabIndex=(.*)")
      val matcherIndex = patternIndex.matcher(action)
      if (matcherIndex.find()) {
        index = matcherIndex.group(1).toInt()
      }
      IntentUtil.intent2TagCategory(context, ApiConstant.tagTabUrl, id, index)
      // eyepetizer://category/24/?title=%E6%97%B6%E5%B0%9A
    } else if (action.startsWith("eyepetizer://category")) {
      var id = ""
      var index = 0
      val pattern: Pattern = Pattern.compile("//category/(.*)/\\?")
      val matcher = pattern.matcher(action)
      if (matcher.find()) {
        id = matcher.group(1)
      }

      // eyepetizer://category/18/?title=%E8%BF%90%E5%8A%A8&tabIndex=2
      val patternIndex: Pattern = Pattern.compile("tabIndex=(.*)")
      val matcherIndex = patternIndex.matcher(action)
      if (matcherIndex.find()) {
        index = matcherIndex.group(1).toInt()
      }
      IntentUtil.intent2TagCategory(context, ApiConstant.categoryTabUrl, id, index)
    }
  }
}