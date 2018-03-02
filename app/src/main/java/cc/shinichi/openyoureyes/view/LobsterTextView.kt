package cc.shinichi.openyoureyes.view

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/3/1  15:19
* description: 
*/
class LobsterTextView : TextView {

    constructor(context: Context?) : super(context) {
        setTypeface()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setTypeface()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setTypeface()
    }

    private fun setTypeface() {
        val assetManager: AssetManager = context.assets
        val typeface: Typeface = Typeface.createFromAsset(assetManager, "fonts/Lobster-1.4.otf")
        setTypeface(typeface)
    }
}