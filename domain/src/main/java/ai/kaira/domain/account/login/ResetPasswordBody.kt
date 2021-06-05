package ai.kaira.domain.account.login

import androidx.annotation.Keep
@Keep
data class ResetPasswordBody(val password:String, val token:String)
