package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Content(
  @SerializedName("type") val type: String? = "",
  @SerializedName("data") val data: DataX? = DataX(),
  @SerializedName("tag") val tag: List<Tag?>? = listOf(),
  @SerializedName("id") val id: Int? = 0,
  @SerializedName("adIndex") val adIndex: Int? = 0
)