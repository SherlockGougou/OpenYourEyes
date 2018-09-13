package cc.shinichi.openyoureyesmvp.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import cc.shinichi.openyoureyesmvp.mvp.view.common.activity.AllCategory
import cc.shinichi.openyoureyesmvp.mvp.view.common.activity.AllPgcs
import cc.shinichi.openyoureyesmvp.mvp.view.common.activity.Browser
import cc.shinichi.openyoureyesmvp.mvp.view.common.activity.CampaignList
import cc.shinichi.openyoureyesmvp.mvp.view.home.HomeView
import cc.shinichi.openyoureyesmvp.mvp.view.common.activity.RankList
import cc.shinichi.openyoureyesmvp.mvp.view.common.activity.TagCategory
import cc.shinichi.openyoureyesmvp.mvp.view.common.activity.UserInfo
import cc.shinichi.openyoureyesmvp.mvp.view.common.activity.VideoDetail

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/24  16:27
 * description:
 */
object IntentUtil {

    fun intent2Home(context: Context) {
        val intent = Intent()
        intent
                .setClass(context, HomeView::class.java)
        context
                .startActivity(intent)
    }

    fun intent2Browser(
            context: Context,
            url: String
    ) {
        val uri: Uri = Uri
                .parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context
                .startActivity(intent)
    }

    fun intent2InnerBrowser(
            context: Context,
            url: String,
            title: String
    ) {
        Browser.activityStart(context, url, title)
    }

    fun intent2RankList(
            context: Context
    ) {
        RankList.activityStart(context)
    }

    fun intent2AllCategoryList(
            context: Context
    ) {
        AllCategory.activityStart(context)
    }

    fun intent2CampaignList(
            context: Context
    ) {
        CampaignList.activityStart(context)
    }

    fun intent2AllPgcs(
            context: Context
    ) {
        AllPgcs.activityStart(context)
    }

    fun intent2VideoDetail(
            context: Context,
            playUrl: String?,
            videoId: String?,
            videoCover: String?
    ) {
        if (UIUtil.isNull(playUrl) || UIUtil.isNull(videoId)) {
            ToastUtil._short("作品不存在或已被删除！")
            return
        }
        VideoDetail.activityStart(context, playUrl, videoId, videoCover)
    }

    fun intent2TagCategory(
            context: Context,
            tabUrl: String,
            id: String,
            index: Int
    ) {
        TagCategory.activityStart(context, tabUrl, id, index)
    }

    fun intent2UserInfo(
            context: Context,
            id: String,
            userType: String,
            index: Int
    ) {
        UserInfo.activityStart(context, id, userType, index)
    }
}