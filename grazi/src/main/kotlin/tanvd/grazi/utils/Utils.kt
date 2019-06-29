package tanvd.grazi.utils

import org.languagetool.rules.RuleMatch

fun RuleMatch.toIntRange(offset: Int = 0) = IntRange(fromPos + offset, toPos + offset - 1)

fun IntRange.withOffset(offset: Int) = IntRange(start + offset, endInclusive + offset)

fun <T> String.ifContains(value: String, body: (Int) -> T): T? {
    val index = indexOf(value)
    if (index != -1) {
        return body(index)
    }
    return null
}

fun <T> List<T>.dropFirstIf(body: (T) -> Boolean) = this.getOrNull(0)?.let { if (body(it)) drop(1) else this } ?: this
