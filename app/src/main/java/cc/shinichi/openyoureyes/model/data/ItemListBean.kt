package cc.shinichi.openyoureyes.model.data

import com.google.gson.annotations.SerializedName


/*
* @author 工藤
* @emil gougou@16fan.com
* cc.shinichi.openyoureyes.model.data
* create at 2018/4/25  16:21
* description: 
*/
data class ItemListBean(
		@SerializedName("itemList") val itemList: List<Item>,
		@SerializedName("count") val count: Int,
		@SerializedName("total") val total: Int,
		@SerializedName("nextPageUrl") val nextPageUrl: String,
		@SerializedName("adExist") val adExist: Boolean
)

data class Item(
		@SerializedName("type") val type: String,
		@SerializedName("data") val data: Data,
		@SerializedName("id") val id: Int,
		@SerializedName("adIndex") val adIndex: Int
)

data class Data(
		@SerializedName("dataType") val dataType: String,
        @SerializedName("itemList") val itemList: List<Item>,
		@SerializedName("dynamicType") val dynamicType: String,
		@SerializedName("text") val text: String,
		@SerializedName("actionUrl") val actionUrl: String,
		@SerializedName("user") val user: User,
		@SerializedName("createDate") val createDate: Long,
		@SerializedName("simpleVideo") val simpleVideo: SimpleVideo,
		@SerializedName("reply") val reply: Reply
)

data class SimpleVideo(
		@SerializedName("id") val id: Int,
		@SerializedName("uid") val uid: Int,
		@SerializedName("title") val title: String,
		@SerializedName("description") val description: String,
		@SerializedName("cover") val cover: Cover,
		@SerializedName("category") val category: String,
		@SerializedName("playUrl") val playUrl: String,
		@SerializedName("duration") val duration: Int,
		@SerializedName("releaseTime") val releaseTime: Long,
		@SerializedName("resourceType") val resourceType: String,
		@SerializedName("consumption") val consumption: Any,
		@SerializedName("collected") val collected: Boolean,
		@SerializedName("actionUrl") val actionUrl: String,
		@SerializedName("onlineStatus") val onlineStatus: String
)

data class Cover(
		@SerializedName("feed") val feed: String,
		@SerializedName("detail") val detail: String,
		@SerializedName("blurred") val blurred: String,
		@SerializedName("sharing") val sharing: Any,
		@SerializedName("homepage") val homepage: Any
)

data class User(
		@SerializedName("uid") val uid: Int,
		@SerializedName("nickname") val nickname: String,
		@SerializedName("avatar") val avatar: String,
		@SerializedName("userType") val userType: String,
		@SerializedName("ifPgc") val ifPgc: Boolean,
		@SerializedName("description") val description: Any,
		@SerializedName("area") val area: Any,
		@SerializedName("gender") val gender: Any,
		@SerializedName("registDate") val registDate: Long,
		@SerializedName("releaseDate") val releaseDate: Any,
		@SerializedName("cover") val cover: Any,
		@SerializedName("actionUrl") val actionUrl: String,
		@SerializedName("followed") val followed: Boolean,
		@SerializedName("limitVideoOpen") val limitVideoOpen: Boolean
)

data class Reply(
		@SerializedName("id") val id: Long,
		@SerializedName("videoId") val videoId: Int,
		@SerializedName("videoTitle") val videoTitle: String,
		@SerializedName("message") val message: String,
		@SerializedName("likeCount") val likeCount: Int,
		@SerializedName("showConversationButton") val showConversationButton: Boolean,
		@SerializedName("parentReplyId") val parentReplyId: Int,
		@SerializedName("rootReplyId") val rootReplyId: Long,
		@SerializedName("ifHotReply") val ifHotReply: Boolean,
		@SerializedName("liked") val liked: Boolean
)