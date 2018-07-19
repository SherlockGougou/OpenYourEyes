package cc.shinichi.openyoureyes.datapool

import cc.shinichi.openyoureyes.model.bean.home.Data

object VideoDataPool {

  private lateinit var data: Data

  fun setDataBean(data: Data) {
    this.data = data
  }

  fun getDataBean(): Data {
    return data
  }
}