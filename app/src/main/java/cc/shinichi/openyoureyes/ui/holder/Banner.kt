package cc.shinichi.openyoureyes.ui.holder

import android.content.Context
import android.view.View
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.model.bean.home.Data
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.util.eye.ActionUrlUtil
import cc.shinichi.openyoureyes.util.image.ImageLoader
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

class Banner {

  private var context: Context
  private var helper: BaseViewHolder
  private var entity: HomeDataEntity

  constructor(
    context: Context,
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    this.context = context
    this.entity = entity
    this.helper = helper
    setData()
  }

  private fun setData() {
    val data: Data? = entity.getData()?.data ?: return
    val img_banner: SimpleDraweeView = helper.getView(R.id.img_banner)
    ImageLoader.load(data?.image, img_banner)

    helper.getView<View>(R.id.layout_root_container).setOnClickListener {
      ActionUrlUtil.jump(context, data?.actionUrl)
    }
  }
}