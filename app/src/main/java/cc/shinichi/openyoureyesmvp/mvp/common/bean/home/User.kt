package cc.shinichi.openyoureyesmvp.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        @SerializedName("uid") val uid: Int? = 0,
        @SerializedName("nickname") val nickname: String? = "",
        @SerializedName("avatar") val avatar: String? = "",
        @SerializedName("userType") val userType: String? = "",
        @SerializedName("ifPgc") val ifPgc: Boolean? = false,
        @SerializedName("description") val description: String? = "",
        @SerializedName("area") val area: String? = "",
        @SerializedName("gender") val gender: String? = "",
        @SerializedName("registDate") val registDate: String? = "",
        @SerializedName("releaseDate") val releaseDate: String? = "",
        @SerializedName("actionUrl") val actionUrl: String? = "",
        @SerializedName("followed") val followed: Boolean? = false,
        @SerializedName("limitVideoOpen") val limitVideoOpen: Boolean? = false,
        @SerializedName("library") val library: String? = "",
        @SerializedName("uploadStatus") val uploadStatus: String? = "",
        @SerializedName("bannedDate") val bannedDate: String? = "",
        @SerializedName("bannedDays") val bannedDays: String? = ""
) : Parcelable