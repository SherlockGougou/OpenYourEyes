package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

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