package ai.kaira.app.utils

import ai.kaira.app.R
import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UIUtils {

    companion object {
        fun alert(context: Context, title:String, message:String){
            MaterialAlertDialogBuilder(context).setTitle(title).setMessage(message).setPositiveButton(context.getText(R.string.ok),null).show()
        }

        fun alert(context: Context, title:String, message:String,runnable: ()->Unit){
            MaterialAlertDialogBuilder(context).setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(context.getText(R.string.ok)) { dialog, which -> runnable() }.show()
        }

        fun alertYesNo(context: Context, title:String, message:String,runnable: ()->Unit){
            MaterialAlertDialogBuilder(context).setTitle(title).setMessage(message).setCancelable(false).setNegativeButton(context.getText(R.string.action_no),null).setPositiveButton(context.getText(R.string.action_yes)) { dialog, which -> runnable() }.show()
        }


        fun networkCallAlert(context: Context,message: String){
            MaterialAlertDialogBuilder(context).setTitle(context.getString(R.string.attention)).setMessage(message).setPositiveButton(context.getText(R.string.ok),null).show()
        }

        fun networkCallAlert(context: Context,message: String,runnable: ()->Unit){
            MaterialAlertDialogBuilder(context).setTitle(context.getString(R.string.attention)).setMessage(message).setCancelable(false).setPositiveButton(context.getText(R.string.ok),{ dialog, which -> runnable() }).show()
        }

        fun networkCallAlert(context: Context,message: String,positive:String, negative:String, runnable: ()->Unit){
            MaterialAlertDialogBuilder(context).setTitle(context.getString(R.string.attention))
                .setMessage(message)
                .setNegativeButton(negative,null)
                .setPositiveButton(positive, { _, _ -> runnable() }).show()
        }
        fun networkConnectivityAlert(context: Context){
            alert(context,context.getString(R.string.attention),context.getString(R.string.error_network))
        }

        fun networkConnectivityAlert(context: Context, runnable: ()->Unit){
            alert(context,context.getString(R.string.attention),context.getString(R.string.error_network),runnable)
        }
    }
}