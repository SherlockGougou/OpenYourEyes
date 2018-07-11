package cc.shinichi.openyoureyes.model.bean.home

import com.google.gson.annotations.SerializedName

data class Item(
  @SerializedName("type") val type: String? = "",
  @SerializedName("data") val data: Data? = Data(),
  @SerializedName("tag") val tag: Any? = Any(),
  @SerializedName("id") val id: Int? = 0,
  @SerializedName("adIndex") val adIndex: Int? = 0
)