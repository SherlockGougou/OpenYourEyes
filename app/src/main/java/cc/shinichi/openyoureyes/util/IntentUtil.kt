package cc.shinichi.openyoureyes.util

import android.content.Context
import android.content.Intent
import cc.shinichi.openyoureyes.ui.activity.Home

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/24  16:27
* description: 
*/
open class IntentUtil {
    companion object {
        fun intent2Home(context: Context?) {
            val intent : Intent = Intent()
            intent.setClass(context, Home :: class.java)
            context!!.startActivity(intent)
        }
    }
}