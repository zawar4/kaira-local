package ai.kaira.domain.introduction.model

class User (var id:String = "",
            var firstName:String = "",
            var lastName:String = "",
            var language:String = "",
            var email:String = "",
            var createdAt:String = "",
            var verified:Boolean = false,
            var validGroupCode:Boolean=false) {
}