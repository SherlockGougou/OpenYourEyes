package cc.shinichi.openyoureyes.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.app.AppManager
import cc.shinichi.openyoureyes.util.StatusBarUtil

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/24  15:31
* description: 基activity
*/
open class BaseActivity : AppCompatActivity() {

    open var toast: Toast = Toast.makeText(App.application, "", Toast.LENGTH_SHORT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        StatusBarUtil.setDarkStatusBar(this, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().killActivity(this)
    }

    open fun toast(string: String, d: Int = Toast.LENGTH_SHORT) {
        toast.setText(string)
        toast.duration = d
        toast.show()
    }
}