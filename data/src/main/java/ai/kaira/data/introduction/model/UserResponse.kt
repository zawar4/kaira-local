package ai.kaira.data.introduction.model

import ai.kaira.domain.account.Token
import ai.kaira.domain.introduction.model.User

import androidx.annotation.Keep
@Keep
data class UserResponse(private val id:String = "",
                        private val firstName:String = "",
                        private val lastName:String ="",
                        private val language:String = "",
                        private val email:String="",
                        private val createdAt:String = "",
                        private val groupCode:String="",
                        private val verified:Boolean = false,
                        private val validGroupCode:Boolean = false,
                        var timezone:String = "",
                        var token: Token?= null){

    fun maptoUser():User{
        var user = User()
        user.id = id
        user.firstName = firstName
        user.createdAt = createdAt
        user.language = language
        user.validGroupCode = validGroupCode
        user.verified = verified
        user.timezone = timezone
        user.token = token
        return user
    }

}

