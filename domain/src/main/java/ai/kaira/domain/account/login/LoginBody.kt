package ai.kaira.domain.account.login

import androidx.annotation.Keep
@Keep
data class LoginBody(val email:String,val password:String,val timezone: String)
