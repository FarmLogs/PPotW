/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 10/5/15
 * (C) 2015 Damian Wieczorek
 */
fun main(args: Array<String>) {
  val size = args[0].toInt()
  for (r in 0 until size) {
    for (c in 0 until size) {
      print(if ((r + c) == size / 2 || (r + c + 1) == size * 3 / 2 || (c - r).square() == (size / 2).square()) 'X' else ' ')
    }
    println()
  }
}

fun Int.square() = this * this
