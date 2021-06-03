package ai.kaira.domain.splash.usecase

import ai.kaira.domain.account.login.usecase.DeleteToken
import ai.kaira.domain.account.login.usecase.UserLoggedIn
import ai.kaira.domain.banking.institution.usecase.GetMyInstitutions
import javax.inject.Inject

class SplashUseCase @Inject constructor(val deleteToken: DeleteToken,val getMyInstitutions: GetMyInstitutions,val userLoggedIn: UserLoggedIn) {
}