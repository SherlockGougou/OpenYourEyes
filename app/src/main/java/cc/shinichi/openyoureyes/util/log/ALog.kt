package cc.shinichi.openyoureyes.util.log

import cc.shinichi.openyoureyes.constant.Config

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/28  15:04
 * description:
 */
object ALog {
  fun log(
    TAG: String? = "Shinichi.log",
    msg: String
  ) {
    // 规定每段显示的长度
    val LOG_MAXLENGTH = 2000
    if (Config.是否打印日志) {
      val strLength = msg
          .length
      var start = 0
      var end = LOG_MAXLENGTH
      for (i in 0..99) {
        // 剩下的文本还是大于规定长度则继续重复截取并输出
        if (strLength > end) {
          android
              .util
              .Log
              .d(TAG + i, msg.substring(start, end))
          start = end
          end += LOG_MAXLENGTH
        } else {
          android
              .util
              .Log
              .d(TAG, msg.substring(start, strLength))
          break
        }
      }
    }
  }
}