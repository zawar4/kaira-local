package ai.kaira.domain.account.login.usecase

import javax.inject.Inject

class LoginUseCase @Inject constructor(val login: Login, val rememberMe: RememberMe) {
}