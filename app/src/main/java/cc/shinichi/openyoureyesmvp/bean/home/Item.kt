package cc.shinichi.openyoureyesmvp.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
        @SerializedName("type") val type: String? = "",
        @SerializedName("data") var data: Data? = Data(),
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("adIndex") val adIndex: Int? = 0,
        @SerializedName("dataType") var dataType: String = ""
) : Parcelable