package cc.shinichi.openyoureyes.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.app.AppManager
import com.gyf.barlibrary.ImmersionBar

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/24  15:31
* description: 基activity
*/
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    open lateinit var immersionBar: ImmersionBar
    open var toast: Toast = Toast.makeText(App.application, "", Toast.LENGTH_SHORT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        immersionBar = ImmersionBar.with(this)
        immersionBar.statusBarColor(R.color.white)
                .statusBarDarkFont(true).init()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().killActivity(this)
        immersionBar.destroy()
    }

    open fun toast(string: String, d: Int = Toast.LENGTH_SHORT) {
        toast.setText(string)
        toast.duration = d
        toast.show()
    }
}