import java.io.File
import java.nio.charset.Charset

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 11/3/15
 * (C) 2015 Damian Wieczorek
 */
fun main(args: Array<String>) {

  val tree = CharSequenceTrie()

  time("Populate tree") {
    usingDictionary(args[0]) { words ->
      words.filter { it.length > 1 }.forEach { word ->
        tree.insertSorted(word)
      }
    }
  }

  time ("Search for anagrams") {
    val combinations = tree.search("thebrownfox")
    val stats = combinations.intStatistics { size }
    printerr("Count: ${combinations.size}")
    printerr("Distinct words: ${combinations.count { it.size == 1 }}")
    printerr("Stats: $stats")

    val charCount = combinations.sumBy { it.sumBy { it.length } }
    println("$charCount")
  }
}

inline fun usingDictionary(path: String, block: (Sequence<String>) -> Unit) {
  val f = File(path)
  if (!f.exists()) {
    throw IllegalArgumentException("Dictionary file $path does not exist")
  }

  f.inputStream().bufferedReader(Charset.forName("UTF-8")).useLines(block)
}
