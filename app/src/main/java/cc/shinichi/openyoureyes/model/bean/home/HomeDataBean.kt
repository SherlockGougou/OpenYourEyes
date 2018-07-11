package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class HomeDataBean(
  @SerializedName("itemList") val itemList: List<Item?>? = listOf(),
  @SerializedName("count") val count: Int? = 0,
  @SerializedName("total") val total: Int? = 0,
  @SerializedName("nextPageUrl") val nextPageUrl: String? = "",
  @SerializedName("adExist") val adExist: Boolean? = false
)