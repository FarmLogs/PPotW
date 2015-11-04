import trees.CharSequenceTrie
import java.io.File
import java.nio.charset.Charset

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 11/3/15
 * (C) 2015 Damian Wieczorek
 */
fun main(args: Array<String>) {
  val tree = CharSequenceTrie()

  withDictionary(args[0]) { words ->
    words.forEach { word ->
      tree.insertSorted(word)
    }
  }

  println("Done creating tree")

  val f = File("dict_tree.txt")
  f.printWriter().use { writer ->
    tree.prettyPrint(writer)
  }

  println("Wrote tree to file")
}

inline fun withDictionary(path: String, block: (Sequence<String>) -> Unit) {
  val f = File(path)
  if (!f.exists()) {
    throw IllegalArgumentException("Dictionary file $path does not exist")
  }

  f.inputStream().bufferedReader(Charset.forName("UTF-8")).useLines(block)
}
