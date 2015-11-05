import com.google.common.collect.Table

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 11/3/15
 * (C) 2015 Damian Wieczorek
 */

val UNDEFINED_CHAR = 0xffff.toChar()

operator fun <R, C, V> Table<R, C, V>.set(rowKey: R, columnKey: C, value: V) = put(rowKey, columnKey, value)

fun CharSequence.suffixesSequence(): Sequence<CharSequence> = (0 until length).asSequence().map {
  subSequence(it, length)
}

fun CharSequence.combinations(): Sequence<CharSequence> {
  val seen = hashSetOf<CharSequence>()
  val first = this[0].toString()
  return suffixesSequence().drop(1).filter {
    (it !in seen).apply { seen.add(it) }
  }.flatMap { suffix ->
    suffix.combinations().map { first + it }
  } + suffixesSequence().drop(1)
}
