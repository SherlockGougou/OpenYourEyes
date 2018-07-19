package cc.shinichi.openyoureyes.datapool

import cc.shinichi.openyoureyes.model.bean.home.Item

object VideoDataPool {

  private lateinit var item: Item

  fun setDataBean(item: Item) {
    this.item = item
  }

  fun getDataBean(): Item {
    return item
  }
}