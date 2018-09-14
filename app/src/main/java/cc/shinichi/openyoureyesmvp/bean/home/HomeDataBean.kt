package cc.shinichi.openyoureyesmvp.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeDataBean(
        @SerializedName("itemList") val itemList: List<Item?>? = listOf(),
        @SerializedName("count") val count: Int? = 0,
        @SerializedName("total") val total: Int? = 0,
        @SerializedName("nextPageUrl") val nextPageUrl: String? = "",
        @SerializedName("adExist") val adExist: Boolean? = false
) : Parcelable