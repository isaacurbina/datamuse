package urbina.isaac.dictionary.model

data class APIResponse(
    val word: String?,
    val score: Long,
    val tags: List<String?>?
)