package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Tag(
  @SerializedName("id") val id: Int? = 0,
  @SerializedName("name") val name: String? = "",
  @SerializedName("actionUrl") val actionUrl: String? = "",
  @SerializedName("adTrack") val adTrack: List<Any?>? = listOf(),
  @SerializedName("desc") val desc: String? = "",
  @SerializedName("bgPicture") val bgPicture: String? = "",
  @SerializedName("headerImage") val headerImage: String? = "",
  @SerializedName("tagRecType") val tagRecType: String? = ""
)