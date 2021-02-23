package ai.kaira.domain.assessment.model

enum class PsychologicalProfileType (val rawValue: Int) {
    socialStatus(0), autonomy(1), benevolence(2), hedonism(3), stress(999);

    companion object {
        operator fun invoke(rawValue: Int) = PsychologicalProfileType.values().firstOrNull { it.rawValue == rawValue }
    }
}