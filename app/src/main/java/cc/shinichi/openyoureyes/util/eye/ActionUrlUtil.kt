package cc.shinichi.openyoureyes.util.eye

import android.content.Context
import cc.shinichi.openyoureyes.util.IntentUtil
import cc.shinichi.openyoureyes.util.UIUtil
import java.util.regex.Pattern

object ActionUrlUtil {

  fun jump(context: Context, actionUrl: String?) {
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
    }
  }
}