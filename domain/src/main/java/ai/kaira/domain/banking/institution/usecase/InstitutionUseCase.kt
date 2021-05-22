package ai.kaira.domain.banking.institution.usecase

import javax.inject.Inject

class InstitutionUseCase @Inject constructor(val getAllInstitutions: GetAllInstitutions,val connectInstitution: ConnectInstitution) {
}