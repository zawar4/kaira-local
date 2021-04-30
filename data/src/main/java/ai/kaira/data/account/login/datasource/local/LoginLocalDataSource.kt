package ai.kaira.data.account.login.datasource.local

import ai.kaira.domain.introduction.model.User

interface LoginLocalDataSource {

    fun saveToken(user:User)
    fun isLoggedIn():Boolean
    fun logout()
}