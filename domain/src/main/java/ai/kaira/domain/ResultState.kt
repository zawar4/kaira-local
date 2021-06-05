package ai.kaira.domain

import androidx.annotation.Keep

@Keep
enum class ResultState {
    SUCCESS,
    ERROR,
    EXCEPTION,
    LOADING
}