package cc.shinichi.openyoureyes.model.bean

import com.google.gson.annotations.SerializedName

data class TagCategoryBean(
  @SerializedName("tabInfo") val tabInfo: TabInfo? = TabInfo(),
  @SerializedName("categoryInfo") val categoryInfo: CategoryInfo? = CategoryInfo(),
  @SerializedName("tagInfo") val tagInfo: TagInfo? = TagInfo()
) {

  data class CategoryInfo(
    @SerializedName("dataType") val dataType: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("headerImage") val headerImage: String? = "",
    @SerializedName("actionUrl") val actionUrl: String? = "",
    @SerializedName("follow") val follow: Follow? = Follow()
  ) {

    data class Follow(
      @SerializedName("itemType") val itemType: String? = "",
      @SerializedName("itemId") val itemId: Int? = 0,
      @SerializedName("followed") val followed: Boolean? = false
    )
  }

  data class TagInfo(
    @SerializedName("dataType") val dataType: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("headerImage") val headerImage: String? = "",
    @SerializedName("bgPicture") val bgPicture: String? = "",
    @SerializedName("actionUrl") val actionUrl: String? = "",
    @SerializedName("recType") val recType: Int? = 0,
    @SerializedName("follow") val follow: Follow? = Follow(),
    @SerializedName("tagFollowCount") val tagFollowCount: Int? = 0,
    @SerializedName("tagVideoCount") val tagVideoCount: Int? = 0,
    @SerializedName("tagDynamicCount") val tagDynamicCount: Int? = 0
  ) {

    data class Follow(
      @SerializedName("itemType") val itemType: String? = "",
      @SerializedName("itemId") val itemId: Int? = 0,
      @SerializedName("followed") val followed: Boolean? = false
    )
  }

  data class TabInfo(
    @SerializedName("tabList") val tabList: List<Tab?>? = listOf(),
    @SerializedName("defaultIdx") val defaultIdx: Int? = 0
  ) {

    data class Tab(
      @SerializedName("id") val id: Int? = 0,
      @SerializedName("name") val name: String? = "",
      @SerializedName("apiUrl") val apiUrl: String? = ""
    )
  }
}