package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Provider(
  @SerializedName("name") val name: String? = "",
  @SerializedName("alias") val alias: String? = "",
  @SerializedName("icon") val icon: String? = ""
)