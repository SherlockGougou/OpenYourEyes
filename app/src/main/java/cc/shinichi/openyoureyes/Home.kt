package cc.shinichi.openyoureyes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        findViewById<TextView>(R.id.text).setOnClickListener {
            Toast.makeText(this, "hh", Toast.LENGTH_SHORT).show()
        }
    }
}
