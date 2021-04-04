package ai.kaira.domain.account.usecase

import ai.kaira.domain.introduction.usecase.FetchUser
import javax.inject.Inject

class AccountCreateUseCase @Inject constructor(val groupCodeExists: GroupCodeExists,
                                               val fetchUser: FetchUser,
                                               val emailExists: EmailExists,
                                               val createAccount: CreateAccount,
                                               val fetchUserCreateAccount: FetchUserCreateAccount,
                                               val sendVerificationEmail: SendVerificationEmail) {
}