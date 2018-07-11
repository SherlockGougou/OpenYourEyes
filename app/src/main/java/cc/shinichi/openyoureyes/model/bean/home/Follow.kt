package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Follow(
  @SerializedName("itemType") val itemType: String? = "",
  @SerializedName("itemId") val itemId: Int? = 0,
  @SerializedName("followed") val followed: Boolean? = false
)