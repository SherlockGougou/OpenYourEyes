package cc.shinichi.openyoureyes.ui.adapter

import android.content.Context
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.TYPE_DynamicInfoCard
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.TYPE_HorizontalScrollCard
import cc.shinichi.openyoureyes.model.entity.HomeDataEntity.Companion.TYPE_banner
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/3/5  12:21
 * description:
 */
class HomeDataAdapter(
  context: Context,
  data: List<HomeDataEntity>
) : BaseMultiItemQuickAdapter<HomeDataEntity, BaseViewHolder>(data) {

  init {

  }

  override fun convert(
    helper: BaseViewHolder,
    entity: HomeDataEntity
  ) {
    when(entity.itemType) {
      TYPE_HorizontalScrollCard -> {

      }
      TYPE_DynamicInfoCard -> {

      }
      TYPE_banner -> {

      }
    }
  }
}