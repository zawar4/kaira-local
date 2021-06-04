package ai.kaira.data.account.login.datasource.local

import ai.kaira.domain.introduction.model.User
import android.content.SharedPreferences
import javax.inject.Inject

class LoginLocalDataSourceImp @Inject constructor(private val prefs: SharedPreferences) : LoginLocalDataSource {

    override fun saveToken(user: User) {
        user.token?.let{ token ->
            prefs.edit().putString("token",token.token).apply()
            prefs.edit().putString("refresh",token.refresh).apply()
            prefs.edit().putLong("expiresIn", token.expiresIn).apply()
        }
    }

    override fun deleteToken() {
        prefs.edit().remove("token").apply()
    }

    override fun isLoggedIn(): Boolean {
        return prefs.contains("token")
    }

    override fun logout() {
        prefs.edit().clear().apply()
    }
}