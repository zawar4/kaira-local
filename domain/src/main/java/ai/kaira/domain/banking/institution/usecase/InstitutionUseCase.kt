package ai.kaira.domain.banking.institution.usecase

import ai.kaira.domain.account.login.usecase.DeleteToken
import javax.inject.Inject

class InstitutionUseCase @Inject constructor(val getAllInstitutions: GetAllInstitutions,val connectInstitution: ConnectInstitution,val deleteToken: DeleteToken) {
}