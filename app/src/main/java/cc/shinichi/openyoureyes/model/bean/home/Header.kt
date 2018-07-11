package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Header(
  @SerializedName("id") val id: Int? = 0,
  @SerializedName("title") val title: String? = "",
  @SerializedName("font") val font: Any? = Any(),
  @SerializedName("subTitle") val subTitle: Any? = Any(),
  @SerializedName("subTitleFont") val subTitleFont: Any? = Any(),
  @SerializedName("textAlign") val textAlign: String? = "",
  @SerializedName("cover") val cover: Any? = Any(),
  @SerializedName("label") val label: Any? = Any(),
  @SerializedName("actionUrl") val actionUrl: String? = "",
  @SerializedName("labelList") val labelList: Any? = Any(),
  @SerializedName("icon") val icon: String? = "",
  @SerializedName("iconType") val iconType: String? = "",
  @SerializedName("description") val description: String? = "",
  @SerializedName("time") val time: Long? = 0,
  @SerializedName("showHateVideo") val showHateVideo: Boolean? = false
)