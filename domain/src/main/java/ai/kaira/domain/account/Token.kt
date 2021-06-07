package ai.kaira.domain.account

import androidx.annotation.Keep
@Keep
data class Token(var token:String,var refresh:String,var expiresIn:Long)
