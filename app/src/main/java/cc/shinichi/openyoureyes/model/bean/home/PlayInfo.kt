package cc.shinichi.openyoureyes.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayInfo(
  @SerializedName("height") val height: Int? = 0,
  @SerializedName("width") val width: Int? = 0,
  @SerializedName("urlList") val urlList: List<Url?>? = listOf(),
  @SerializedName("name") val name: String? = "",
  @SerializedName("type") val type: String? = "",
  @SerializedName("url") val url: String? = ""
) : Parcelable