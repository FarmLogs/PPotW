/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 10/27/15
 * (C) 2015 Damian Wieczorek
 */
fun main(args: Array<String>) {
  println(generate(args[0].toInt()))
}

operator fun String.times(count: Int) = (0 until count).map { this }.join("")

fun generate(length: Int) = when {
  length == 1 -> "invalid"
  length % 2 == 1 -> "'('" + "()" * ((length - 3) / 2)
  else -> "()" * (length / 2)
}
