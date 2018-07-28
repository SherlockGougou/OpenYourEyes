package cc.shinichi.openyoureyes.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Url(
  @SerializedName("name") val name: String? = "",
  @SerializedName("url") val url: String? = "",
  @SerializedName("size") val size: Long? = 0
) : Parcelable