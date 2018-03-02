package cc.shinichi.openyoureyes.constant

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/23  16:38
* description: 开眼视频部分接口
*/
open class Constant {
    companion object {
        // 接口
        const val 类别列表 = "http://baobab.kaiyanapp.com/api/v5/index/tab/list"
        const val 发现 = "http://baobab.kaiyanapp.com/api/v5/index/tab/discovery"
        const val 推荐 = "http://baobab.kaiyanapp.com/api/v5/index/tab/allRec"
        const val 日报 = "http://baobab.kaiyanapp.com/api/v5/index/tab/feed"

        // 酷安图片
        private val 酷安图片0 = "http://image.coolapk.com/picture/2018/0222/1282852_1519258611_1707@1080x1920.jpg"
        private val 酷安图片1 = "http://image.coolapk.com/picture/2018/0227/844681_1519742056_1754@1080x2160.png"
        private val 酷安图片2 = "http://image.coolapk.com/picture/2018/0227/716123_1519746286_5343@1440x2560.jpg"
        private val 酷安图片3 = "http://image.coolapk.com/picture/2018/0225/807832_1519517357_7577@1440x2560.jpg"
        private val 酷安图片4 = "http://image.coolapk.com/picture/2018/0227/473142_1519740815_0371@1080x2160.jpg"
        private val 酷安图片5 = "http://image.coolapk.com/picture/2018/0226/486655_1519638285_2885@1080x1920.jpg"
        private val 酷安图片6 = "http://image.coolapk.com/picture/2018/0131/606004_1517401329_8016@1080x1920.png"
        val 酷安图片列表 = listOf<String>(酷安图片0,
                酷安图片1,
                酷安图片2,
                酷安图片3,
                酷安图片4,
                酷安图片5,
                酷安图片6)

        // 是否打印日志
        const val 是否打印日志 = true
//        const val 是否打印日志 = false
    }
}