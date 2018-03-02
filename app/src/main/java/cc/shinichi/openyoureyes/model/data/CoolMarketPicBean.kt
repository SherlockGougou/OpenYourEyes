package cc.shinichi.openyoureyes.model.data

import java.io.Serializable

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/28  15:53
* description: 
*/
open class CoolMarketPicBean : Serializable {
    private var data: List<DataBean>? = null

    fun getData(): List<DataBean>? {
        return data
    }

    fun setData(data: List<DataBean>) {
        this.data = data
    }

    class DataBean {
        var entityType: String? = null
        var entityTemplate: String? = null
        var title: String? = null
        var url: String? = null
        var entityId: Int = 0
        var entityFixed: Int = 0
        var pic: String? = null
        var lastupdate: Int = 0
        var block_status: String? = null
        var comment_block_num: String? = null
        var dateline: String? = null
        var device_title: String? = null
        var dyh_id: String? = null
        var dyh_name: String? = null
        var extra_info: String? = null
        var extra_key: String? = null
        var extra_pic: String? = null
        var extra_status: String? = null
        var extra_title: String? = null
        var extra_type: String? = null
        var extra_url: String? = null
        var favnum: String? = null
        var fid: String? = null
        var forwardid: String? = null
        var forwardnum: String? = null
        var fromid: String? = null
        var fromname: String? = null
        var id: String? = null
        var is_html_article: String? = null
        var issummary: String? = null
        var istag: String? = null
        var label: String? = null
        var likenum: String? = null
        var location: String? = null
        var media_pic: String? = null
        var media_type: String? = null
        var media_url: String? = null
        var message: String? = null
        var message_cover: String? = null
        var message_keywords: String? = null
        var message_length: String? = null
        var message_status: String? = null
        var message_title: String? = null
        var question_answer_num: String? = null
        var question_follow_num: String? = null
        var rank_score: String? = null
        var recent_hot_reply_ids: String? = null
        var recent_reply_ids: String? = null
        var recommend: String? = null
        var relatednum: String? = null
        var replynum: String? = null
        var reportnum: String? = null
        var source_id: String? = null
        var status: String? = null
        var tags: String? = null
        var tid: String? = null
        var tinfo: String? = null
        var tpic: String? = null
        var ttitle: String? = null
        var turl: String? = null
        var type: String? = null
        var uid: String? = null
        var user_tags: String? = null
        var username: String? = null
        var index_name: String? = null
        var _queryTotal: Int = 0
        var _queryViewTotal: Int = 0
        var _querySearchTime: Double = 0.toDouble()
        var fetchType: String? = null
        var avatarFetchType: String? = null
        var userAvatar: String? = null
        var feedType: String? = null
        var feedTypeName: String? = null
        var turlTarget: String? = null
        var info: String? = null
        var infoHtml: String? = null
        var sourceFeed: Any? = null
        var userAction: UserActionBean? = null
        var entities: List<EntitiesBean>? = null
        var picArr: List<String>? = null
        var relateddata: List<*>? = null
        var recentLikeList: List<String>? = null

        class UserActionBean {
            var like: Int = 0
            var favorite: Int = 0
            var follow: Int = 0
        }

        class EntitiesBean {
            var block_status: String? = null
            var comment_block_num: String? = null
            var dateline: String? = null
            var device_title: String? = null
            var dyh_id: String? = null
            var dyh_name: String? = null
            var extra_info: String? = null
            var extra_key: String? = null
            var extra_pic: String? = null
            var extra_status: String? = null
            var extra_title: String? = null
            var extra_type: String? = null
            var extra_url: String? = null
            var favnum: String? = null
            var fid: String? = null
            var forwardid: String? = null
            var forwardnum: String? = null
            var fromid: String? = null
            var fromname: String? = null
            var id: String? = null
            var is_html_article: String? = null
            var issummary: String? = null
            var istag: String? = null
            var label: String? = null
            var lastupdate: String? = null
            var likenum: String? = null
            var location: String? = null
            var media_pic: String? = null
            var media_type: String? = null
            var media_url: String? = null
            var message: String? = null
            var message_cover: String? = null
            var message_keywords: String? = null
            var message_length: String? = null
            var message_status: String? = null
            var message_title: String? = null
            var pic: String? = null
            var question_answer_num: String? = null
            var question_follow_num: String? = null
            var rank_score: String? = null
            var recent_hot_reply_ids: String? = null
            var recent_reply_ids: String? = null
            var recommend: String? = null
            var relatednum: String? = null
            var replynum: String? = null
            var reportnum: String? = null
            var source_id: String? = null
            var status: String? = null
            var tags: String? = null
            var tid: String? = null
            var tinfo: String? = null
            var tpic: String? = null
            var ttitle: String? = null
            var turl: String? = null
            var type: String? = null
            var uid: String? = null
            var user_tags: String? = null
            var username: String? = null
            var index_name: String? = null
            var _queryTotal: Int = 0
            var _queryViewTotal: Int = 0
            var _querySearchTime: Double = 0.toDouble()
            var fetchType: String? = null
            var entityId: String? = null
            var avatarFetchType: String? = null
            var userAvatar: String? = null
            var entityTemplate: String? = null
            var entityType: String? = null
            var url: String? = null
            var feedType: String? = null
            var feedTypeName: String? = null
            var turlTarget: String? = null
            var info: String? = null
            var title: String? = null
            var infoHtml: String? = null
            var sourceFeed: Any? = null
            var userAction: UserActionBeanX? = null
            var picArrCount: Int = 0
            var picArr: List<String>? = null
            var relateddata: List<*>? = null
            var recentLikeList: List<String>? = null

            class UserActionBeanX {
                var like: Int = 0
                var favorite: Int = 0
                var follow: Int = 0
            }
        }
    }
}