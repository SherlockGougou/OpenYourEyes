package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Cover(
  @SerializedName("feed") val feed: String? = "",
  @SerializedName("detail") val detail: String? = "",
  @SerializedName("blurred") val blurred: String? = "",
  @SerializedName("sharing") val sharing: String? = "",
  @SerializedName("homepage") val homepage: String? = ""
)