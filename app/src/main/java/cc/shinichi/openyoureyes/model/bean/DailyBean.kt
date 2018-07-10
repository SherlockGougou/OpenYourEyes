package cc.shinichi.openyoureyes.model.bean

import com.google.gson.annotations.SerializedName

data class DailyBean(
  @SerializedName("itemList") val itemList: List<Item?>? = listOf(),
  @SerializedName("count") val count: Int? = 0,
  @SerializedName("total") val total: Int? = 0,
  @SerializedName("nextPageUrl") val nextPageUrl: String? = "",
  @SerializedName("adExist") val adExist: Boolean? = false
) {

  data class Item(
    @SerializedName("type") val type: String? = "",
    @SerializedName("data") val data: Data? = Data(),
    @SerializedName("tag") val tag: Any? = Any(),
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("adIndex") val adIndex: Int? = 0
  ) {

    data class Data(
      @SerializedName("dataType") val dataType: String? = "",
      @SerializedName("header") val header: Header? = Header(),
      @SerializedName("content") val content: Content? = Content(),
      @SerializedName("adTrack") val adTrack: Any? = Any()
    ) {

      data class Content(
        @SerializedName("type") val type: String? = "",
        @SerializedName("data") val data: Data? = Data(),
        @SerializedName("tag") val tag: Any? = Any(),
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("adIndex") val adIndex: Int? = 0
      ) {

        data class Data(
          @SerializedName("dataType") val dataType: String? = "",
          @SerializedName("id") val id: Int? = 0,
          @SerializedName("title") val title: String? = "",
          @SerializedName("description") val description: String? = "",
          @SerializedName("library") val library: String? = "",
          @SerializedName("tags") val tags: List<Tag?>? = listOf(),
          @SerializedName("consumption") val consumption: Consumption? = Consumption(),
          @SerializedName("resourceType") val resourceType: String? = "",
          @SerializedName("slogan") val slogan: String? = "",
          @SerializedName("provider") val provider: Provider? = Provider(),
          @SerializedName("category") val category: String? = "",
          @SerializedName("author") val author: Author? = Author(),
          @SerializedName("cover") val cover: Cover? = Cover(),
          @SerializedName("playUrl") val playUrl: String? = "",
          @SerializedName("thumbPlayUrl") val thumbPlayUrl: Any? = Any(),
          @SerializedName("duration") val duration: Int? = 0,
          @SerializedName("webUrl") val webUrl: WebUrl? = WebUrl(),
          @SerializedName("releaseTime") val releaseTime: Long? = 0,
          @SerializedName("playInfo") val playInfo: List<PlayInfo?>? = listOf(),
          @SerializedName("campaign") val campaign: Any? = Any(),
          @SerializedName("waterMarks") val waterMarks: Any? = Any(),
          @SerializedName("adTrack") val adTrack: Any? = Any(),
          @SerializedName("type") val type: String? = "",
          @SerializedName("titlePgc") val titlePgc: Any? = Any(),
          @SerializedName("descriptionPgc") val descriptionPgc: Any? = Any(),
          @SerializedName("remark") val remark: Any? = Any(),
          @SerializedName("ifLimitVideo") val ifLimitVideo: Boolean? = false,
          @SerializedName("searchWeight") val searchWeight: Int? = 0,
          @SerializedName("idx") val idx: Int? = 0,
          @SerializedName("shareAdTrack") val shareAdTrack: Any? = Any(),
          @SerializedName("favoriteAdTrack") val favoriteAdTrack: Any? = Any(),
          @SerializedName("webAdTrack") val webAdTrack: Any? = Any(),
          @SerializedName("date") val date: Long? = 0,
          @SerializedName("promotion") val promotion: Any? = Any(),
          @SerializedName("label") val label: Any? = Any(),
          @SerializedName("labelList") val labelList: List<Any?>? = listOf(),
          @SerializedName("descriptionEditor") val descriptionEditor: String? = "",
          @SerializedName("collected") val collected: Boolean? = false,
          @SerializedName("played") val played: Boolean? = false,
          @SerializedName("subtitles") val subtitles: List<Any?>? = listOf(),
          @SerializedName("lastViewTime") val lastViewTime: Any? = Any(),
          @SerializedName("playlists") val playlists: Any? = Any(),
          @SerializedName("src") val src: Any? = Any()
        ) {

          data class Consumption(
            @SerializedName("collectionCount") val collectionCount: Int? = 0,
            @SerializedName("shareCount") val shareCount: Int? = 0,
            @SerializedName("replyCount") val replyCount: Int? = 0
          )

          data class PlayInfo(
            @SerializedName("height") val height: Int? = 0,
            @SerializedName("width") val width: Int? = 0,
            @SerializedName("urlList") val urlList: List<Url?>? = listOf(),
            @SerializedName("name") val name: String? = "",
            @SerializedName("type") val type: String? = "",
            @SerializedName("url") val url: String? = ""
          ) {

            data class Url(
              @SerializedName("name") val name: String? = "",
              @SerializedName("url") val url: String? = "",
              @SerializedName("size") val size: Int? = 0
            )
          }

          data class Tag(
            @SerializedName("id") val id: Int? = 0,
            @SerializedName("name") val name: String? = "",
            @SerializedName("actionUrl") val actionUrl: String? = "",
            @SerializedName("adTrack") val adTrack: Any? = Any(),
            @SerializedName("desc") val desc: Any? = Any(),
            @SerializedName("bgPicture") val bgPicture: String? = "",
            @SerializedName("headerImage") val headerImage: String? = "",
            @SerializedName("tagRecType") val tagRecType: String? = ""
          )

          data class Author(
            @SerializedName("id") val id: Int? = 0,
            @SerializedName("icon") val icon: String? = "",
            @SerializedName("name") val name: String? = "",
            @SerializedName("description") val description: String? = "",
            @SerializedName("link") val link: String? = "",
            @SerializedName("latestReleaseTime") val latestReleaseTime: Long? = 0,
            @SerializedName("videoNum") val videoNum: Int? = 0,
            @SerializedName("adTrack") val adTrack: Any? = Any(),
            @SerializedName("follow") val follow: Follow? = Follow(),
            @SerializedName("shield") val shield: Shield? = Shield(),
            @SerializedName("approvedNotReadyVideoCount") val approvedNotReadyVideoCount: Int? = 0,
            @SerializedName("ifPgc") val ifPgc: Boolean? = false
          ) {

            data class Shield(
              @SerializedName("itemType") val itemType: String? = "",
              @SerializedName("itemId") val itemId: Int? = 0,
              @SerializedName("shielded") val shielded: Boolean? = false
            )

            data class Follow(
              @SerializedName("itemType") val itemType: String? = "",
              @SerializedName("itemId") val itemId: Int? = 0,
              @SerializedName("followed") val followed: Boolean? = false
            )
          }

          data class Provider(
            @SerializedName("name") val name: String? = "",
            @SerializedName("alias") val alias: String? = "",
            @SerializedName("icon") val icon: String? = ""
          )

          data class WebUrl(
            @SerializedName("raw") val raw: String? = "",
            @SerializedName("forWeibo") val forWeibo: String? = ""
          )

          data class Cover(
            @SerializedName("feed") val feed: String? = "",
            @SerializedName("detail") val detail: String? = "",
            @SerializedName("blurred") val blurred: String? = "",
            @SerializedName("sharing") val sharing: Any? = Any(),
            @SerializedName("homepage") val homepage: String? = ""
          )
        }
      }

      data class Header(
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("title") val title: String? = "",
        @SerializedName("font") val font: Any? = Any(),
        @SerializedName("subTitle") val subTitle: Any? = Any(),
        @SerializedName("subTitleFont") val subTitleFont: Any? = Any(),
        @SerializedName("textAlign") val textAlign: String? = "",
        @SerializedName("cover") val cover: Any? = Any(),
        @SerializedName("label") val label: Any? = Any(),
        @SerializedName("actionUrl") val actionUrl: String? = "",
        @SerializedName("labelList") val labelList: Any? = Any(),
        @SerializedName("icon") val icon: String? = "",
        @SerializedName("iconType") val iconType: String? = "",
        @SerializedName("description") val description: String? = "",
        @SerializedName("time") val time: Long? = 0,
        @SerializedName("showHateVideo") val showHateVideo: Boolean? = false
      )
    }
  }
}