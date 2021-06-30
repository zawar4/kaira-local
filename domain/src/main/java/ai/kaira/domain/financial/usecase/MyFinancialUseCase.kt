package ai.kaira.domain.financial.usecase

import ai.kaira.domain.account.login.usecase.DeleteToken
import javax.inject.Inject

class MyFinancialUseCase @Inject constructor(val GetMyFinancials: GetMyFinancials,val deleteToken: DeleteToken)
