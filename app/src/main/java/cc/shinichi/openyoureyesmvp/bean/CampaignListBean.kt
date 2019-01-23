package cc.shinichi.openyoureyesmvp.bean

import com.google.gson.annotations.SerializedName

data class CampaignListBean(
        @SerializedName("itemList") val itemList: List<Item?>? = listOf(),
        @SerializedName("count") val count: Int? = 0,
        @SerializedName("total") val total: Int? = 0,
        @SerializedName("nextPageUrl") val nextPageUrl: String? = "",
        @SerializedName("adExist") val adExist: Boolean? = false
) {

    data class Item(
            @SerializedName("type") val type: String? = "",
            @SerializedName("data") val data: Data? = Data(),
            @SerializedName("id") val id: Int? = 0,
            @SerializedName("adIndex") val adIndex: Int? = 0
    ) {

        data class Data(
                @SerializedName("dataType") val dataType: String? = "",
                @SerializedName("id") val id: Int? = 0,
                @SerializedName("title") val title: String? = "",
                @SerializedName("description") val description: String? = "",
                @SerializedName("image") val image: String? = "",
                @SerializedName("actionUrl") val actionUrl: String? = "",
                @SerializedName("shade") val shade: Boolean? = false,
                @SerializedName("label") val label: Label? = Label()
        ) {

            data class Label(
                    @SerializedName("text") val text: String? = "",
                    @SerializedName("card") val card: String? = ""
            )
        }
    }
}