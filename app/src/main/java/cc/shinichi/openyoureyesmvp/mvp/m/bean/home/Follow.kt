package cc.shinichi.openyoureyesmvp.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Follow(
        @SerializedName("itemType") val itemType: String? = "",
        @SerializedName("itemId") val itemId: Int? = 0,
        @SerializedName("followed") val followed: Boolean? = false
) : Parcelable