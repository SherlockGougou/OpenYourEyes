package cc.shinichi.openyoureyes.model.bean

import com.google.gson.annotations.SerializedName

data class Follow(
  @SerializedName("itemId") val itemId: Int = 0, @SerializedName(
      "itemType"
  ) val itemType: String = "", @SerializedName("followed")
  val followed: Boolean = false
)

data class ItemListItem(
  @SerializedName("data") val data: CategoryData, @SerializedName(
      "adIndex"
  ) val adIndex: Int = 0, @SerializedName("tag")
  val tag: String = "", @SerializedName("id") val id: Int = 0, @SerializedName(
      "type"
  ) val type: String = ""
)

data class Category(
  @SerializedName("adExist") val adExist: Boolean = false, @SerializedName(
      "total"
  ) val total: Int = 0,
  @SerializedName("nextPageUrl") val nextPageUrl: String = "", @SerializedName(
      "count"
  ) val count: Int = 0,
  @SerializedName("itemList") val itemList: List<ItemListItem>?
)

data class CategoryData(
  @SerializedName("subTitle") val subTitle: String = "", @SerializedName(
      "dataType"
  ) val dataType: String = "",
  @SerializedName("iconType") val iconType: String = "", @SerializedName(
      "icon"
  ) val icon: String = "",
  @SerializedName("actionUrl") val actionUrl: String = "", @SerializedName(
      "ifPgc"
  ) val ifPgc: Boolean = false,
  @SerializedName("description") val description: String = "", @SerializedName(
      "id"
  ) val id: Int = 0, @SerializedName("title")
  val title: String = "", @SerializedName("follow") val follow: Follow, @SerializedName(
      "adTrack"
  ) val adTrack: String = ""
)


