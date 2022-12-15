package id.bts.devin.alfagift_test.data.utils

fun String?.replaceIfNull(replacementValue: String = ""): String {
    if (this == null)
        return replacementValue
    return this
}

fun String.replaceIfEmpty(replacementValue: String): String {
    if (this.isEmpty())
        return replacementValue
    return this
}

fun Int?.replaceIfNull(replacementValue: Int = 0): Int {
    if (this == null)
        return replacementValue
    return this
}

fun Boolean?.replaceIfNull(replacementValue: Boolean = false): Boolean {
    if (this == null) return replacementValue
    return this
}

fun Long?.replaceIfNull(replacementValue: Long = 0L): Long {
    if (this == null)
        return replacementValue
    return this
}

fun Double?.replaceIfNull(replacementValue: Double = 0.0): Double {
    if (this == null) return replacementValue
    return this
}

fun <T> List<T>?.replaceIfNull(replacementValue: List<T> = listOf()): List<T> {
    if (this == null) return replacementValue
    return this
}

