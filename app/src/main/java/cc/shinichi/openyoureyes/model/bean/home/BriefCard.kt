package cc.shinichi.openyoureyes.model.bean.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BriefCard(
  @SerializedName("dataType") val dataType: String? = "",
  @SerializedName("id") val id: Int? = 0,
  @SerializedName("icon") val icon: String? = "",
  @SerializedName("iconType") val iconType: String? = "",
  @SerializedName("title") val title: String? = "",
  @SerializedName("subTitle") val subTitle: String? = "",
  @SerializedName("description") val description: String? = "",
  @SerializedName("actionUrl") val actionUrl: String? = "",
  @SerializedName("follow") val follow: Follow? = Follow()
) : Parcelable