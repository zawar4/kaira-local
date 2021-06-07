package ai.kaira.data.utils

import androidx.annotation.Keep
import java.text.SimpleDateFormat
import java.util.*

@Keep
class UtilityFunctions {

    companion object{
        fun iso8601FormatDate(time:Long):String{
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val timeCreatedDate = Date(time)
            return dateFormat.format(timeCreatedDate)
        }
    }
}