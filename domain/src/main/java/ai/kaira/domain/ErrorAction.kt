package ai.kaira.domain

import androidx.annotation.Keep
@Keep
data class ErrorAction(val message:String,val kairaAction: KairaAction? = null)
