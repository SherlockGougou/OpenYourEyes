package cc.shinichi.openyoureyes.model.bean

import com.google.gson.annotations.SerializedName

data class DiscoveryBean(
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
      @SerializedName("dynamicType") val dynamicType: String? = "",
      @SerializedName("text") val text: String? = "",
      @SerializedName("actionUrl") val actionUrl: String? = "",
      @SerializedName("user") val user: User? = User(),
      @SerializedName("mergeNickName") val mergeNickName: Any? = Any(),
      @SerializedName("mergeSubTitle") val mergeSubTitle: Any? = Any(),
      @SerializedName("merge") val merge: Boolean? = false,
      @SerializedName("createDate") val createDate: Long? = 0,
      @SerializedName("simpleVideo") val simpleVideo: SimpleVideo? = SimpleVideo(),
      @SerializedName("reply") val reply: Reply? = Reply()
    ) {

      data class Reply(
        @SerializedName("id") val id: Long? = 0,
        @SerializedName("videoId") val videoId: Int? = 0,
        @SerializedName("videoTitle") val videoTitle: String? = "",
        @SerializedName("message") val message: String? = "",
        @SerializedName("likeCount") val likeCount: Int? = 0,
        @SerializedName("showConversationButton") val showConversationButton: Boolean? = false,
        @SerializedName("parentReplyId") val parentReplyId: Int? = 0,
        @SerializedName("rootReplyId") val rootReplyId: Long? = 0,
        @SerializedName("ifHotReply") val ifHotReply: Boolean? = false,
        @SerializedName("liked") val liked: Boolean? = false,
        @SerializedName("parentReply") val parentReply: Any? = Any(),
        @SerializedName("user") val user: User? = User()
      ) {

        data class User(
          @SerializedName("uid") val uid: Int? = 0,
          @SerializedName("nickname") val nickname: String? = "",
          @SerializedName("avatar") val avatar: String? = "",
          @SerializedName("userType") val userType: String? = "",
          @SerializedName("ifPgc") val ifPgc: Boolean? = false,
          @SerializedName("description") val description: String? = "",
          @SerializedName("area") val area: Any? = Any(),
          @SerializedName("gender") val gender: Any? = Any(),
          @SerializedName("registDate") val registDate: Long? = 0,
          @SerializedName("releaseDate") val releaseDate: Long? = 0,
          @SerializedName("cover") val cover: String? = "",
          @SerializedName("actionUrl") val actionUrl: String? = "",
          @SerializedName("followed") val followed: Boolean? = false,
          @SerializedName("limitVideoOpen") val limitVideoOpen: Boolean? = false,
          @SerializedName("library") val library: String? = ""
        )
      }

      data class User(
        @SerializedName("uid") val uid: Int? = 0,
        @SerializedName("nickname") val nickname: String? = "",
        @SerializedName("avatar") val avatar: String? = "",
        @SerializedName("userType") val userType: String? = "",
        @SerializedName("ifPgc") val ifPgc: Boolean? = false,
        @SerializedName("description") val description: String? = "",
        @SerializedName("area") val area: Any? = Any(),
        @SerializedName("gender") val gender: Any? = Any(),
        @SerializedName("registDate") val registDate: Long? = 0,
        @SerializedName("releaseDate") val releaseDate: Long? = 0,
        @SerializedName("cover") val cover: String? = "",
        @SerializedName("actionUrl") val actionUrl: String? = "",
        @SerializedName("followed") val followed: Boolean? = false,
        @SerializedName("limitVideoOpen") val limitVideoOpen: Boolean? = false,
        @SerializedName("library") val library: String? = ""
      )

      data class SimpleVideo(
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("resourceType") val resourceType: String? = "",
        @SerializedName("uid") val uid: Int? = 0,
        @SerializedName("title") val title: String? = "",
        @SerializedName("description") val description: String? = "",
        @SerializedName("cover") val cover: Cover? = Cover(),
        @SerializedName("category") val category: String? = "",
        @SerializedName("playUrl") val playUrl: String? = "",
        @SerializedName("duration") val duration: Int? = 0,
        @SerializedName("releaseTime") val releaseTime: Long? = 0,
        @SerializedName("consumption") val consumption: Any? = Any(),
        @SerializedName("collected") val collected: Boolean? = false,
        @SerializedName("actionUrl") val actionUrl: String? = "",
        @SerializedName("onlineStatus") val onlineStatus: String? = "",
        @SerializedName("count") val count: Int? = 0
      ) {

        data class Cover(
          @SerializedName("feed") val feed: String? = "",
          @SerializedName("detail") val detail: String? = "",
          @SerializedName("blurred") val blurred: String? = "",
          @SerializedName("sharing") val sharing: Any? = Any(),
          @SerializedName("homepage") val homepage: String? = ""
        )
      }
    }
  }
}