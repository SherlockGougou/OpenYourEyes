package cc.shinichi.openyoureyesmvp.model.bean

import com.google.gson.annotations.SerializedName

data class CategoryListBean(
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
                @SerializedName("icon") val icon: String? = "",
                @SerializedName("iconType") val iconType: String? = "",
                @SerializedName("title") val title: String? = "",
                @SerializedName("subTitle") val subTitle: String? = "",
                @SerializedName("description") val description: String? = "",
                @SerializedName("actionUrl") val actionUrl: String? = "",
                @SerializedName("adTrack") val adTrack: List<Any?>? = listOf(),
                @SerializedName("follow") val follow: Follow? = Follow(),
                @SerializedName("ifPgc") val ifPgc: Boolean? = false
        ) {

            data class Follow(
                    @SerializedName("itemType") val itemType: String? = "",
                    @SerializedName("itemId") val itemId: Int? = 0,
                    @SerializedName("followed") val followed: Boolean? = false
            )
        }
    }
}