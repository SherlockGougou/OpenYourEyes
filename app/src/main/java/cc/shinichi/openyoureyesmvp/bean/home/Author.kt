package cc.shinichi.openyoureyesmvp.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("icon") val icon: String? = "",
        @SerializedName("name") val name: String? = "",
        @SerializedName("description") val description: String? = "",
        @SerializedName("link") val link: String? = "",
        @SerializedName("latestReleaseTime") val latestReleaseTime: Long? = 0,
        @SerializedName("videoNum") val videoNum: Int? = 0,
        @SerializedName("follow") val follow: Follow? = Follow(),
        @SerializedName("shield") val shield: Shield? = Shield(),
        @SerializedName("approvedNotReadyVideoCount") val approvedNotReadyVideoCount: Int? = 0,
        @SerializedName("ifPgc") val ifPgc: Boolean? = false
) : Parcelable