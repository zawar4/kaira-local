package ai.kaira.domain

import androidx.annotation.Keep

@Keep
enum class KairaAction {
    UNVERIFIED_REDIRECT,
    UNKOWN_REDIRECT,
    UNAUTHORIZED_REDIRECT
}