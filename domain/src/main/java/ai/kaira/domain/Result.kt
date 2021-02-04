package ai.kaira.domain

data class Result<T> (val data: T, val resultState: ResultState, val error: String = "") {
}