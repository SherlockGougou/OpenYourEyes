package cc.shinichi.openyoureyesmvp.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Header(
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("title") val title: String? = "",
        @SerializedName("issuerName") val issuerName: String? = "",
        @SerializedName("font") val font: String? = "",
        @SerializedName("subTitle") val subTitle: String? = "",
        @SerializedName("subTitleFont") val subTitleFont: String? = "",
        @SerializedName("textAlign") val textAlign: String? = "",
        @SerializedName("actionUrl") val actionUrl: String? = "",
        @SerializedName("icon") val icon: String? = "",
        @SerializedName("iconType") val iconType: String? = "",
        @SerializedName("description") val description: String? = "",
        @SerializedName("time") val time: Long? = 0,
        @SerializedName("showHateVideo") val showHateVideo: Boolean? = false
) : Parcelable