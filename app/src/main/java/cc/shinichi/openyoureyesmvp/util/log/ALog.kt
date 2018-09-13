package cc.shinichi.openyoureyesmvp.util.log

import cc.shinichi.openyoureyesmvp.constant.Config

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/28  15:04
 * description:
 */
object ALog {
    fun log(
            TAG: String? = "Shinichi.log",
            msg: String? = "null"
    ) {
        // 规定每段显示的长度
        val LOG_MAXLENGTH = 2000
        if (Config.isPrintLog) {
            val strLength = msg?.length ?: 0
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                // 剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    android
                            .util
                            .Log
                            .d(TAG + i, msg?.substring(start, end))
                    start = end
                    end += LOG_MAXLENGTH
                } else {
                    android
                            .util
                            .Log
                            .d(TAG, msg?.substring(start, strLength))
                    break
                }
            }
        }
    }
}