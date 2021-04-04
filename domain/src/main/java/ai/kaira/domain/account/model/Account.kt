package ai.kaira.domain.account.model

data class Account(var firstName:String, var lastName:String, var language:String,var email:String,var password:String,var groupCode:String,var createContact:Boolean = true,var id:String = "") {
}