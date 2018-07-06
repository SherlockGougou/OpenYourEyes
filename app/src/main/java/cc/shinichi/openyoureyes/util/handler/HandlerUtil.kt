package cc.shinichi.openyoureyes.util.handler

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/3/5  11:54
 * description: handler工具类，防止handler导致的内存泄漏
 */
object HandlerUtil {
  private fun HandlerUtil() {
    throw UnsupportedOperationException("u can't instantiate me...")
  }

  class HandlerHolder(listener: Handler.Callback) : Handler() {

    private var mListenerWeakReference: WeakReference<Callback>? = null

    init {
      mListenerWeakReference = WeakReference(listener)
    }

    override fun handleMessage(msg: Message) {
      if (mListenerWeakReference != null && mListenerWeakReference!!.get() != null) {
        mListenerWeakReference!!.get()!!
            .handleMessage(msg)
      }
    }
  }
}