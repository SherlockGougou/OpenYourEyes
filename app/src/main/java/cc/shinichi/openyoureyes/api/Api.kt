package cc.shinichi.openyoureyes.api

import android.content.Context
import cc.shinichi.openyoureyes.app.App

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/23  16:33
* description: 
*/

public class Api(context: Context) {

    companion object {
        fun getInstance(): Api {
            return InnerClass.api
        }
    }

    class InnerClass {
        companion object {
            val api: Api = Api(App.application)
        }
    }
}