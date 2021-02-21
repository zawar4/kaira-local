package ai.kaira.data.utils

import java.text.SimpleDateFormat
import java.util.*

class UtilityFunctions {

    companion object{
        fun iso8601FormatDate(time:Long):String{
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val timeCreatedDate = Date(time)
            return dateFormat.format(timeCreatedDate)
        }
    }
}