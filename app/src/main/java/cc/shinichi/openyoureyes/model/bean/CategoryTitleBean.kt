package cc.shinichi.openyoureyes.model.bean

import com.google.gson.annotations.SerializedName

data class CategoryTitle(@SerializedName("tabInfo") val tabInfo: TabInfo)

data class TabInfo(
  @SerializedName("tabList") val tabList: List<TabListItem>?, @SerializedName(
      "defaultIdx"
  ) val defaultIdx: Int = 0
)

data class TabListItem(
  @SerializedName("apiUrl") val apiUrl: String = "", @SerializedName(
      "name"
  ) val name: String = "", @SerializedName("id")
  val id: Int = 0
)


