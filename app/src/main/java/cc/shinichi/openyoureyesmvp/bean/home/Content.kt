package cc.shinichi.openyoureyesmvp.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Content(
        @SerializedName("type") val type: String? = "",
        @SerializedName("data") val data: DataX? = DataX(),
        @SerializedName("tag") val tag: List<Tag?>? = listOf(),
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("adIndex") val adIndex: Int? = 0
) : Parcelable