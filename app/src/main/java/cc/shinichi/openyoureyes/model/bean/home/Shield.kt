package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Shield(
  @SerializedName("itemType") val itemType: String? = "",
  @SerializedName("itemId") val itemId: Int? = 0,
  @SerializedName("shielded") val shielded: Boolean? = false
)