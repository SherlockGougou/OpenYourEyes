package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Url(
  @SerializedName("name") val name: String? = "",
  @SerializedName("url") val url: String? = "",
  @SerializedName("size") val size: Int? = 0
)