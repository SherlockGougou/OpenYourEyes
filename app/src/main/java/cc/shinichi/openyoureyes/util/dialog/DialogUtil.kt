package cc.shinichi.openyoureyes.util.dialog

import android.app.Dialog
import cc.shinichi.openyoureyes.R
import cc.shinichi.openyoureyes.app.AppManager

class DialogUtil {

  companion object {
    fun showLoading(): Dialog {
      var dialog = Dialog(AppManager.getInstance().currentActivity())
      dialog
          .setContentView(R.layout.layout_loading_dialog)
      dialog
          .setCancelable(true)
      dialog
          .setCanceledOnTouchOutside(false)
      return dialog
    }
  }
}