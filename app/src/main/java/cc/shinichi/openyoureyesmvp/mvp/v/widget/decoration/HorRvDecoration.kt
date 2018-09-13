package cc.shinichi.openyoureyesmvp.mvp.v.widget.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State
import android.view.View
import cc.shinichi.openyoureyesmvp.util.UIUtil

class HorRvDecoration : RecyclerView.ItemDecoration {

    private var count: Int = 0

    constructor(count: Int) : super() {
        this.count = count
    }

    override fun getItemOffsets(
            outRect: Rect?,
            view: View?,
            parent: RecyclerView?,
            state: State?
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent?.getChildAdapterPosition(view)
        if (count == 1) {
            outRect?.left = UIUtil.dp2px(10)
            outRect?.right = UIUtil.dp2px(10)
        } else if (count == 2) {
            if (position == 0) {// 第一个
                outRect?.left = UIUtil.dp2px(10)
                outRect?.right = 5
            } else if (position == 1) {// 最后一个
                outRect?.left = 5
                outRect?.right = UIUtil.dp2px(10)
            }
        } else {
            if (position == 0) {// 第一个
                outRect?.left = UIUtil.dp2px(10)
                outRect?.right = 5
            } else if (position == count - 1) {// 最后一个
                outRect?.left = 5
                outRect?.right = UIUtil.dp2px(10)
            } else {
                outRect?.left = 5
                outRect?.right = 5
            }
        }
    }
}