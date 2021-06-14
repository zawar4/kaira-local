package ai.kaira.domain.financial.model

import androidx.annotation.Keep
import java.text.SimpleDateFormat
import java.util.*

@Keep
data class Spending(val date : String, val amount : Double) {

    fun getFormattedDate() : String{
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val monthDateFormat = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
        val convertedCurrentDate: Date = sdf.parse(date)
        return monthDateFormat.format(convertedCurrentDate)
    }

    fun getFormattedAmount():String{
        return String.format("%.2f", amount)
    }
}
