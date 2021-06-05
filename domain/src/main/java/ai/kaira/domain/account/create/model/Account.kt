package ai.kaira.domain.account.create.model

import androidx.annotation.Keep

@Keep
data class Account(var firstName:String, var lastName:String, var language:String,var email:String,var password:String,var groupCode:String,var createContact:Boolean = true,var id:String, var  bankingAggregator:Int) {
}

