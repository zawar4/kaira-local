package ai.kaira.domain.account.login.usecase

import ai.kaira.domain.account.create.usecase.SendVerificationEmail
import javax.inject.Inject

class LoginUseCase @Inject constructor(val login: Login,val forgotPassword: ForgotPassword,val resetPassword: ResetPassword,val sendVerificationEmail: SendVerificationEmail,val saveToken: SaveToken) {
}