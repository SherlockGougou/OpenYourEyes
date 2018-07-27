package cc.shinichi.openyoureyes.model.bean

import com.google.gson.annotations.SerializedName

data class TabBean(
  @SerializedName("tabInfo") val tabInfo: TabInfo? = TabInfo(),
  @SerializedName("categoryInfo") val categoryInfo: CategoryInfo? = CategoryInfo(),
  @SerializedName("tagInfo") val tagInfo: TagInfo? = TagInfo(),
  @SerializedName("pgcInfo") val pgcInfo: PgcInfo? = PgcInfo(),
  @SerializedName("userInfo") val userInfo: UserInfo? = UserInfo()
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

  data class PgcInfo(
    @SerializedName("dataType") val dataType: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("icon") val icon: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("brief") val brief: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("actionUrl") val actionUrl: String? = "",
    @SerializedName("area") val area: String? = "",
    @SerializedName("gender") val gender: String? = "",
    @SerializedName("registDate") val registDate: Int? = 0,
    @SerializedName("followCount") val followCount: Int? = 0,
    @SerializedName("follow") val follow: Follow? = Follow(),
    @SerializedName("self") val self: Boolean? = false,
    @SerializedName("cover") val cover: String? = "",
    @SerializedName("videoCount") val videoCount: Int? = 0,
    @SerializedName("shareCount") val shareCount: Int? = 0,
    @SerializedName("collectCount") val collectCount: Int? = 0,
    @SerializedName("myFollowCount") val myFollowCount: Int? = 0,
    @SerializedName("videoCountActionUrl") val videoCountActionUrl: String? = "",
    @SerializedName("myFollowCountActionUrl") val myFollowCountActionUrl: String? = "",
    @SerializedName("followCountActionUrl") val followCountActionUrl: String? = "",
    @SerializedName("shield") val shield: Shield? = Shield()
  ) {

    data class Shield(
      @SerializedName("itemType") val itemType: String? = "",
      @SerializedName("itemId") val itemId: Int? = 0,
      @SerializedName("shielded") val shielded: Boolean? = false
    )

    data class Follow(
      @SerializedName("itemType") val itemType: String? = "",
      @SerializedName("itemId") val itemId: Int? = 0,
      @SerializedName("followed") val followed: Boolean? = false
    )
  }

  data class UserInfo(
    @SerializedName("dataType") val dataType: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("icon") val icon: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("brief") val brief: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("actionUrl") val actionUrl: String? = "",
    @SerializedName("area") val area: String? = "",
    @SerializedName("gender") val gender: String? = "",
    @SerializedName("registDate") val registDate: Int? = 0,
    @SerializedName("followCount") val followCount: Int? = 0,
    @SerializedName("follow") val follow: Follow? = Follow(),
    @SerializedName("self") val self: Boolean? = false,
    @SerializedName("cover") val cover: String? = "",
    @SerializedName("videoCount") val videoCount: Int? = 0,
    @SerializedName("shareCount") val shareCount: Int? = 0,
    @SerializedName("collectCount") val collectCount: Int? = 0,
    @SerializedName("myFollowCount") val myFollowCount: Int? = 0,
    @SerializedName("videoCountActionUrl") val videoCountActionUrl: String? = "",
    @SerializedName("myFollowCountActionUrl") val myFollowCountActionUrl: String? = "",
    @SerializedName("followCountActionUrl") val followCountActionUrl: String? = "",
    @SerializedName("shield") val shield: Shield? = Shield()
  ) {

    data class Shield(
      @SerializedName("itemType") val itemType: String? = "",
      @SerializedName("itemId") val itemId: Int? = 0,
      @SerializedName("shielded") val shielded: Boolean? = false
    )

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