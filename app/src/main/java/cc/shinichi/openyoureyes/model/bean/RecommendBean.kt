package cc.shinichi.openyoureyes.model.bean

import com.google.gson.annotations.SerializedName

data class RecommendBean(
  @SerializedName("itemList") val itemList: List<Item?>? = listOf(),
  @SerializedName("count") val count: Int? = 0,
  @SerializedName("total") val total: Int? = 0,
  @SerializedName("nextPageUrl") val nextPageUrl: String? = "",
  @SerializedName("adExist") val adExist: Boolean? = false
) {

  data class Item(
    @SerializedName("type") val type: String? = "",
    @SerializedName("data") val data: Data? = Data(),
    @SerializedName("tag") val tag: Any? = Any(),
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("adIndex") val adIndex: Int? = 0
  ) {

    data class Data(
      @SerializedName("dataType") val dataType: String? = "",
      @SerializedName("id") val id: Int? = 0,
      @SerializedName("type") val type: String? = "",
      @SerializedName("text") val text: String? = "",
      @SerializedName("subTitle") val subTitle: Any? = Any(),
      @SerializedName("actionUrl") val actionUrl: String? = "",
      @SerializedName("adTrack") val adTrack: Any? = Any(),
      @SerializedName("follow") val follow: Any? = Any()
    )
  }
}