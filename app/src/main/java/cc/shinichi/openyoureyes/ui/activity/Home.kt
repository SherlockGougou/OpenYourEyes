package cc.shinichi.openyoureyes.ui.activity

import android.os.Bundle
import android.widget.TextView
import cc.shinichi.openyoureyes.R.id
import cc.shinichi.openyoureyes.R.layout
import cc.shinichi.openyoureyes.app.AppManager
import cc.shinichi.openyoureyes.base.BaseActivity

class Home : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)

        findViewById<TextView>(id.text).setOnClickListener {
            toast("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AppManager.getInstance().exit()
    }
}