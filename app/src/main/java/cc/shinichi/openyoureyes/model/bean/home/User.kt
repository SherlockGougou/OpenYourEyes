package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class User(
  @SerializedName("uid") val uid: Int? = 0,
  @SerializedName("nickname") val nickname: String? = "",
  @SerializedName("avatar") val avatar: String? = "",
  @SerializedName("userType") val userType: String? = "",
  @SerializedName("ifPgc") val ifPgc: Boolean? = false,
  @SerializedName("description") val description: Any? = Any(),
  @SerializedName("area") val area: Any? = Any(),
  @SerializedName("gender") val gender: Any? = Any(),
  @SerializedName("registDate") val registDate: Long? = 0,
  @SerializedName("releaseDate") val releaseDate: Any? = Any(),
  @SerializedName("cover") val cover: Any? = Any(),
  @SerializedName("actionUrl") val actionUrl: String? = "",
  @SerializedName("followed") val followed: Boolean? = false,
  @SerializedName("limitVideoOpen") val limitVideoOpen: Boolean? = false,
  @SerializedName("library") val library: String? = "",
  @SerializedName("uploadStatus") val uploadStatus: Any? = Any(),
  @SerializedName("bannedDate") val bannedDate: Any? = Any(),
  @SerializedName("bannedDays") val bannedDays: Any? = Any()
)