package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Consumption(
  @SerializedName("collectionCount") val collectionCount: Int? = 0,
  @SerializedName("shareCount") val shareCount: Int? = 0,
  @SerializedName("replyCount") val replyCount: Int? = 0
)