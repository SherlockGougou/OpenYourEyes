package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class WebUrl(
  @SerializedName("raw") val raw: String? = "",
  @SerializedName("forWeibo") val forWeibo: String? = ""
)