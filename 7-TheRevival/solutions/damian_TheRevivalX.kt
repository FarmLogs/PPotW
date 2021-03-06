/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 10/5/15
 * (C) 2015 Damian Wieczorek
 */
fun main(args: Array<String>) {
  val size = args[0].toInt()
  for (r in 0 until size) {
    for (c in 0 until size) {
      print(if (r == c || r == size - c - 1) 'X' else ' ')
    }
    println()
  }
}
