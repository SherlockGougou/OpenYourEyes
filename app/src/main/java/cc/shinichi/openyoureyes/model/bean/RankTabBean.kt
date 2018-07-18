package cc.shinichi.openyoureyes.model.bean

import com.google.gson.annotations.SerializedName

data class RankTabBean(
  @SerializedName("tabInfo") val tabInfo: TabInfo? = TabInfo()
) {

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