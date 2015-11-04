package trees

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 11/3/15
 * (C) 2015 Damian Wieczorek
 */
inline fun <T> iterableOf(crossinline  getIterator: () -> Iterator<T>) = object : Iterable<T> {
  override fun iterator() = getIterator()
}

val UNDEFINED_CHAR = 0xffff.toChar()

