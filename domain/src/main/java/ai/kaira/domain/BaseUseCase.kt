package ai.kaira.domain

import kotlinx.coroutines.CoroutineScope

import androidx.annotation.Keep

@Keep
open class BaseUseCase constructor(private val viewModelCoroutineScope: CoroutineScope) {
}