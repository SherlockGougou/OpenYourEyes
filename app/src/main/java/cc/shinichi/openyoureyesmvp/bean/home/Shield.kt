package cc.shinichi.openyoureyesmvp.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shield(
        @SerializedName("itemType") val itemType: String? = "",
        @SerializedName("itemId") val itemId: Int? = 0,
        @SerializedName("shielded") val shielded: Boolean? = false
) : Parcelable