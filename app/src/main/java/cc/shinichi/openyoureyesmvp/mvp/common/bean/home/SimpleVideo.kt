package cc.shinichi.openyoureyesmvp.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SimpleVideo(
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("resourceType") val resourceType: String? = "",
        @SerializedName("uid") val uid: Int? = 0,
        @SerializedName("title") val title: String? = "",
        @SerializedName("description") val description: String? = "",
        @SerializedName("cover") val cover: Cover? = Cover(),
        @SerializedName("category") val category: String? = "",
        @SerializedName("playUrl") val playUrl: String? = "",
        @SerializedName("duration") val duration: Int? = 0,
        @SerializedName("releaseTime") val releaseTime: Long? = 0,
        @SerializedName("consumption") val consumption: Consumption? = Consumption(),
        @SerializedName("collected") val collected: Boolean? = false,
        @SerializedName("actionUrl") val actionUrl: String? = "",
        @SerializedName("onlineStatus") val onlineStatus: String? = "",
        @SerializedName("count") val count: Int? = 0
) : Parcelable