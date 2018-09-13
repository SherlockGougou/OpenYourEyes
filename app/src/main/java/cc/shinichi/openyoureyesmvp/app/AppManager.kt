package cc.shinichi.openyoureyesmvp.app

import android.app.Activity
import cc.shinichi.openyoureyesmvp.util.image.ImageLoader
import java.util.Stack

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/26  13:33
 * description:
 */
class AppManager {

    private val activityStack: Stack<Activity> = Stack<Activity>()

    companion object {
        fun getInstance(): AppManager {
            return InnerClass.appManager
        }
    }

    class InnerClass {
        companion object {
            val appManager: AppManager = AppManager()
        }
    }

    fun addActivity(activity: Activity) {
        activityStack
                .add(activity)
    }

    fun currentActivity(): Activity? {
        var activity: Activity? = null
        if (!activityStack.empty()) {
            activity = activityStack
                    .lastElement()
        }
        if (activity == null) {
            exit()
        }
        return activity
    }

    fun exit() {
        try {
            ImageLoader
                    .clearImageMemoryCache()
        } catch (e: Exception) {

        }
        try {
            killAllActivity()
            gcAndFinalize()
        } catch (e: Exception) {
            System
                    .exit(1)
        }
    }

    private fun gcAndFinalize() {
        val runtime = Runtime
                .getRuntime()
        System
                .gc()
        runtime
                .runFinalization()
        System
                .gc()
    }

    fun killAllActivity() {
        while (!activityStack.empty()) {
            val activity = currentActivity()
            killActivity(activity!!)
        }
        activityStack
                .clear()
    }

    fun killActivity(activity: Activity) {
        if (activity.isFinishing) {
            if (activityStack.contains(activity)) {
                activityStack
                        .remove(activity)
            }
        } else {
            activity
                    .finish()
            if (activityStack.contains(activity)) {
                activityStack
                        .remove(activity)
            }
        }
    }
}