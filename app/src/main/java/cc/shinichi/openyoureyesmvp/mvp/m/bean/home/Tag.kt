package cc.shinichi.openyoureyesmvp.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("name") val name: String? = "",
        @SerializedName("actionUrl") val actionUrl: String? = "",
        @SerializedName("desc") val desc: String? = "",
        @SerializedName("bgPicture") val bgPicture: String? = "",
        @SerializedName("headerImage") val headerImage: String? = "",
        @SerializedName("tagRecType") val tagRecType: String? = ""
) : Parcelable