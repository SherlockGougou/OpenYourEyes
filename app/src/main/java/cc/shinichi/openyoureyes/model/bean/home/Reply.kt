package cc.shinichi.openyoureyes.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
  @SerializedName("user") val user: User? = User()
) : Parcelable