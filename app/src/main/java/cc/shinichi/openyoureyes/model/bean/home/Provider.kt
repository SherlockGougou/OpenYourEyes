package cc.shinichi.openyoureyes.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Provider(
  @SerializedName("name") val name: String? = "",
  @SerializedName("alias") val alias: String? = "",
  @SerializedName("icon") val icon: String? = ""
) : Parcelable