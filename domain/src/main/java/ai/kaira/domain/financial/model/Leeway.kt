package ai.kaira.domain.financial.model

import androidx.annotation.Keep
import java.text.SimpleDateFormat
import java.util.*

@Keep
data class Leeway(val date : String, val amount : Double)
