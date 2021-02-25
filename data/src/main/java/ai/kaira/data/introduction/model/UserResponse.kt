package ai.kaira.data.introduction.model

import ai.kaira.domain.introduction.model.User

data class UserResponse(private val id:String = "",
                        private val firstName:String = "",
                        private val language:String = "",
                        private val createdAt:String = "",
                        private val verified:Boolean = false,
                        private val validGroupCode:Boolean = false){

    fun maptoUser():User{
        var user = User()
        user.id = id
        user.firstName = firstName
        user.createdAt = createdAt
        user.language = language
        user.validGroupCode = validGroupCode
        user.verified = verified

        return user
    }

}

