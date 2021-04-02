package ai.kaira.domain.account.model

data class Account(val firstName:String, val lastName:String, val language:String,val email:String,val password:String,val groupCode:String,val createContact:Boolean = true) {
}