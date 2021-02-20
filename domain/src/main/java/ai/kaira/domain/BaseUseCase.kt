package ai.kaira.domain

import kotlinx.coroutines.CoroutineScope

open class BaseUseCase constructor(private val viewModelCoroutineScope: CoroutineScope) {
}