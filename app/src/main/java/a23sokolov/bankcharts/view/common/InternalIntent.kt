package a23sokolov.bankcharts.view.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Created by a.v.sokolov
 */
class InternalIntent(
    private val clazz: Class<out Activity>? = null,
    private val extras: Bundle? = null
) {
    fun start(activity: Activity) {
        val intent = buildIntent(activity)
        if (intent != null ) {
            activity.startActivity(intent)
        }
    }

    private fun buildIntent(context: Context): Intent? {
        if (clazz != null) {
            return Intent(context, clazz).apply {
                if (this@InternalIntent.extras != null) {
                    putExtras(this@InternalIntent.extras)
                }
            }
        }
        return null
    }
}