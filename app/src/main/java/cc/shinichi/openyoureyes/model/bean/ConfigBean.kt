package cc.shinichi.openyoureyes.model.bean


import com.google.gson.annotations.SerializedName

data class Consumption(@SerializedName("display")
                       val display: Boolean = false,
                       @SerializedName("version")
                       val version: String = "")


data class Homepage(@SerializedName("cover")
                    val cover: String = "",
                    @SerializedName("version")
                    val version: String = "")


data class Log(@SerializedName("playLog")
               val playLog: Boolean = false,
               @SerializedName("version")
               val version: String = "")


data class ShareTitle(@SerializedName("weibo")
                      val weibo: String = "",
                      @SerializedName("wechatMoments")
                      val wechatMoments: String = "",
                      @SerializedName("qzone")
                      val qzone: String = "",
                      @SerializedName("version")
                      val version: String = "")


data class Config(@SerializedName("autoCache")
                  val autoCache: AutoCache,
                  @SerializedName("startPageAd")
                  val startPageAd: StartPageAd,
                  @SerializedName("videoAdsConfig")
                  val videoAdsConfig: VideoAdsConfig,
                  @SerializedName("startPage")
                  val startPage: StartPage,
                  @SerializedName("log")
                  val log: Log,
                  @SerializedName("issueCover")
                  val issueCover: IssueCover,
                  @SerializedName("startPageVideo")
                  val startPageVideo: StartPageVideo,
                  @SerializedName("consumption")
                  val consumption: Consumption,
                  @SerializedName("launch")
                  val launch: Launch,
                  @SerializedName("preload")
                  val preload: Preload,
                  @SerializedName("version")
                  val version: String = "",
                  @SerializedName("push")
                  val push: Push,
                  @SerializedName("androidPlayer")
                  val androidPlayer: AndroidPlayer,
                  @SerializedName("profilePageAd")
                  val profilePageAd: ProfilePageAd,
                  @SerializedName("firstLaunch")
                  val firstLaunch: FirstLaunch,
                  @SerializedName("shareTitle")
                  val shareTitle: ShareTitle,
                  @SerializedName("campaignInDetail")
                  val campaignInDetail: CampaignInDetail,
                  @SerializedName("campaignInFeed")
                  val campaignInFeed: CampaignInFeed,
                  @SerializedName("reply")
                  val reply: Reply,
                  @SerializedName("startPageVideoConfig")
                  val startPageVideoConfig: StartPageVideoConfig,
                  @SerializedName("pushInfo")
                  val pushInfo: PushInfo,
                  @SerializedName("homepage")
                  val homepage: Homepage)


data class VideoAdsConfig(@SerializedName("version")
                          val version: String = "")


data class StartPageAd(@SerializedName("displayTimeDuration")
                       val displayTimeDuration: Int = 0,
                       @SerializedName("showBottomBar")
                       val showBottomBar: Boolean = false,
                       @SerializedName("countdown")
                       val countdown: Boolean = false,
                       @SerializedName("actionUrl")
                       val actionUrl: String = "",
                       @SerializedName("blurredImageUrl")
                       val blurredImageUrl: String = "",
                       @SerializedName("canSkip")
                       val canSkip: Boolean = false,
                       @SerializedName("version")
                       val version: String = "",
                       @SerializedName("imageUrl")
                       val imageUrl: String = "",
                       @SerializedName("displayCount")
                       val displayCount: Int = 0,
                       @SerializedName("startTime")
                       val startTime: Long = 0,
                       @SerializedName("endTime")
                       val endTime: Long = 0,
                       @SerializedName("newImageUrl")
                       val newImageUrl: String = "")


data class Launch(@SerializedName("version")
                  val version: String = "",
                  @SerializedName("adTrack")
                  val adTrack: String = "")


data class AndroidPlayer(@SerializedName("playerList")
                         val playerList: List<String>?,
                         @SerializedName("version")
                         val version: String = "")


data class ProfilePageAd(@SerializedName("imageUrl")
                         val imageUrl: String = "",
                         @SerializedName("actionUrl")
                         val actionUrl: String = "",
                         @SerializedName("startTime")
                         val startTime: Long = 0,
                         @SerializedName("endTime")
                         val endTime: Long = 0,
                         @SerializedName("version")
                         val version: String = "")


data class IssueCover(@SerializedName("issueLogo")
                      val issueLogo: IssueLogo,
                      @SerializedName("version")
                      val version: String = "")


data class CampaignInDetail(@SerializedName("imageUrl")
                            val imageUrl: String = "",
                            @SerializedName("available")
                            val available: Boolean = false,
                            @SerializedName("actionUrl")
                            val actionUrl: String = "",
                            @SerializedName("showType")
                            val showType: String = "",
                            @SerializedName("version")
                            val version: String = "")


data class StartPage(@SerializedName("imageUrl")
                     val imageUrl: String = "",
                     @SerializedName("actionUrl")
                     val actionUrl: String = "",
                     @SerializedName("version")
                     val version: String = "")


data class Preload(@SerializedName("version")
                   val version: String = "",
                   @SerializedName("on")
                   val on: Boolean = false)


data class IssueLogo(@SerializedName("adapter")
                     val adapter: Boolean = false,
                     @SerializedName("weekendExtra")
                     val weekendExtra: String = "")


data class AutoCache(@SerializedName("forceOff")
                     val forceOff: Boolean = false,
                     @SerializedName("videoNum")
                     val videoNum: Int = 0,
                     @SerializedName("version")
                     val version: String = "")


data class StartPageVideo(@SerializedName("displayTimeDuration")
                          val displayTimeDuration: Int = 0,
                          @SerializedName("showImageTime")
                          val showImageTime: Int = 0,
                          @SerializedName("actionUrl")
                          val actionUrl: String = "",
                          @SerializedName("countdown")
                          val countdown: Boolean = false,
                          @SerializedName("showImage")
                          val showImage: Boolean = false,
                          @SerializedName("canSkip")
                          val canSkip: Boolean = false,
                          @SerializedName("version")
                          val version: String = "",
                          @SerializedName("url")
                          val url: String = "",
                          @SerializedName("loadingMode")
                          val loadingMode: Int = 0,
                          @SerializedName("displayCount")
                          val displayCount: Int = 0,
                          @SerializedName("startTime")
                          val startTime: Long = 0,
                          @SerializedName("endTime")
                          val endTime: Long = 0,
                          @SerializedName("timeBeforeSkip")
                          val timeBeforeSkip: Int = 0)


data class PushInfo(@SerializedName("normal")
                    val normal: String = "",
                    @SerializedName("version")
                    val version: String = "")


data class Reply(@SerializedName("version")
                 val version: String = "",
                 @SerializedName("on")
                 val on: Boolean = false)


data class FirstLaunch(@SerializedName("showFirstDetail")
                       val showFirstDetail: Boolean = false,
                       @SerializedName("version")
                       val version: String = "")


data class StartPageVideoConfig(@SerializedName("version")
                                val version: String = "")


data class Push(@SerializedName("startTime")
                val startTime: Int = 0,
                @SerializedName("endTime")
                val endTime: Int = 0,
                @SerializedName("message")
                val message: String = "",
                @SerializedName("version")
                val version: String = "")


data class CampaignInFeed(@SerializedName("imageUrl")
                          val imageUrl: String = "",
                          @SerializedName("available")
                          val available: Boolean = false,
                          @SerializedName("actionUrl")
                          val actionUrl: String = "",
                          @SerializedName("version")
                          val version: String = "")


