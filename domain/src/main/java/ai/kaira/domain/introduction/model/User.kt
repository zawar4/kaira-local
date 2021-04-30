package ai.kaira.domain.introduction.model

import ai.kaira.domain.account.Token

class User (var id:String = "",
            var firstName:String = "",
            var lastName:String = "",
            var language:String = "",
            var email:String = "",
            var createdAt:String = "",
            var verified:Boolean = false,
            var validGroupCode:Boolean=false,
            var timezone:String = "",
            var token:Token ?= null) {
}