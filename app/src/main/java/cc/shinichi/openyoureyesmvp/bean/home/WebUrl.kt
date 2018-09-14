package cc.shinichi.openyoureyesmvp.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WebUrl(
        @SerializedName("raw") val raw: String? = "",
        @SerializedName("forWeibo") val forWeibo: String? = ""
) : Parcelable