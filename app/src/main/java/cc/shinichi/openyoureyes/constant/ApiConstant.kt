package cc.shinichi.openyoureyes.constant

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/23  16:38
 * description: 开眼视频部分接口
 */
object ApiConstant {
  // authorGithub
  const val authorGithub = "https://github.com/SherlockGougou"

  // 接口
  const val configUrl = "http://baobab.kaiyanapp.com/api/v2/configs"
  const val categoryUrl = "http://baobab.kaiyanapp.com/api/v5/category/list"
  const val apiPrefix = "http://baobab.kaiyanapp.com/api/v5/index/tab/"

  const val discoveryUrl = "http://baobab.kaiyanapp.com/api/v5/index/tab/discovery"
  const val allRecUrl = "http://baobab.kaiyanapp.com/api/v5/index/tab/allRec"
  const val feedUrl = "http://baobab.kaiyanapp.com/api/v5/index/tab/feed"

  const val rankListConfigUrl = "http://baobab.kaiyanapp.com/api/v4/rankList"

  // video detail
  const val videoDetailUrl = "http://baobab.kaiyanapp.com/api/v2/video/"
  const val videoDetailRelateUrl = "http://baobab.kaiyanapp.com/api/v4/video/related?id="

  // all category
  const val allCategoryUrl = "http://baobab.kaiyanapp.com/api/v4/categories/all"

  // campaign list
  const val campaignListUrl = "http://baobab.kaiyanapp.com/api/v3/specialTopics"

  // all pgcs 全部作者
  const val allPgcsUrl = "http://baobab.kaiyanapp.com/api/v4/pgcs/all"

  // tag tab
  const val tagTabUrl = "http://baobab.kaiyanapp.com/api/v1/tag/index?id="

  // category tab
  const val categoryTabUrl = "http://baobab.kaiyanapp.com/api/v4/categories/detail/tab?id="

  // user info
  const val userInfoUrl = "http://baobab.kaiyanapp.com/api/v5/userInfo/tab?"
}