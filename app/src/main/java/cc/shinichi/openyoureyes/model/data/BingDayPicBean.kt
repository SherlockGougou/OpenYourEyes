package cc.shinichi.openyoureyes.model.data

import java.io.Serializable

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/26  17:21
* description: 必应每日图片接口
*/
class BingDayPicBean : Serializable {
    private var tooltips: TooltipsBean? = null
    private var images: List<ImagesBean>? = null

    fun getTooltips(): TooltipsBean? {
        return tooltips
    }

    fun setTooltips(tooltips: TooltipsBean) {
        this.tooltips = tooltips
    }

    fun getImages(): List<ImagesBean>? {
        return images
    }

    fun setImages(images: List<ImagesBean>) {
        this.images = images
    }

    class TooltipsBean {
        var loading: String? = null
        var previous: String? = null
        var next: String? = null
        var walle: String? = null
        var walls: String? = null
    }

    class ImagesBean {
        var startdate: String? = null
        var fullstartdate: String? = null
        var enddate: String? = null
        var url: String? = null
        var urlbase: String? = null
        var copyright: String? = null
        var copyrightlink: String? = null
        var quiz: String? = null
        var isWp: Boolean = false
        var hsh: String? = null
    }
}