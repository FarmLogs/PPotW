import java.util.*

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 10/22/15
 * (C) 2015 Damian Wieczorek
 */
fun main(args: Array<String>) {
  println(Machine.process(args[0]))
}

enum class Type(val open: Char, val close: Char) {
  PARENTHESES('(', ')'),
  SPONGEBOB_SQUARE_BRACKETS('[', ']'),
  BRACES('{', '}');
}

sealed class Scope(val type: Type) {

  class Open(type: Type) : Scope(type)
  class Close(type: Type) : Scope(type)

  companion object {
    fun of(char: Char) = Type.values.firstOrNull { it.open == char }?.let { Open(it) }
        ?: Type.values.firstOrNull { it.close == char }?.let { Close(it) }
  }

}

object Machine {

  private val stack = ArrayDeque<Type>()
  private var inString: Boolean = false

  fun reset() {
    inString = false
    stack.clear()
  }

  fun process(string: String): Boolean = reset().let {
    string.all { feed(it) } && complete
  }

  fun feed(char: Char): Boolean {
    if (char == '\'') {
      inString = !inString
      return true
    }
    if (inString) {
      return true
    }
    val scope = Scope.of(char) ?: return true
    return when (scope) {
      is Scope.Open -> {
        stack.push(scope.type)
        true
      }
      is Scope.Close -> {
        if (stack.peek() != scope.type) false
        else {
          stack.pop()
          true
        }
      }
    }
  }

  val complete: Boolean
    get() = stack.isEmpty()

}
