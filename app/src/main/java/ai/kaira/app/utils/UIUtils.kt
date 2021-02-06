package ai.kaira.app.utils

import ai.kaira.app.R
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UIUtils {

    companion object {
        fun alert(context: Context, title:String, message:String){
            MaterialAlertDialogBuilder(context).setTitle(title).setMessage(message).setPositiveButton(context.getText(R.string.ok),null).show()
        }

        fun networkCallAlert(context: Context,message: String){
            MaterialAlertDialogBuilder(context).setTitle(context.getString(R.string.attention)).setMessage(message).setPositiveButton(context.getText(R.string.ok),null).show()
        }
        fun networkContectivityAlert(context: Context){
            alert(context,context.getString(R.string.attention),context.getString(R.string.error_network))
        }
    }
}