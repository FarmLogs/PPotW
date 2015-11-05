import com.google.common.collect.*
import set
import java.io.PrintWriter
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.security.SecureRandom

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 11/4/15
 * (C) 2015 Damian Wieczorek
 */
class CharSequenceTrie {

  private val leafMatches = Multimaps.newMultimap<Node, CharSequence>(hashMapOf()) { Sets.newHashSet() }

  private val nodeChildren = HashBasedTable.create<AbstractNode, Char, Node>()

  // terrible way to generate hash code, but I can't stop getting collisions
  private var nodeCount = 0

  val rootNode = object : AbstractNode(null, 0, UNDEFINED_CHAR) {}

  fun insert(charSequence: CharSequence) {
    var pointer = rootNode
    for (i in 0 until charSequence.length) {
      pointer = pointer.getOrAddChild(charSequence[i], if (i == charSequence.length - 1) charSequence else null)
    }
  }

  fun insertSorted(charSequence: CharSequence) {
    var pointer = rootNode
    //    println("wut: ${charSequence.toString().asSequence().sorted().joinToString("")}")
    val sorted = charSequence.toString().asSequence().sorted()
    for ((i, c) in sorted.withIndex()) {
      pointer = pointer.getOrAddChild(c, if (i == charSequence.length - 1) charSequence else null)
    }
  }

  // TODO: proof of concept, non-exhaustive
  fun search(charSequence: CharSequence) = String(charSequence.toString().toCharArray().apply { sort() }).let {
    searchSorted(ImmutableMultiset.copyOf(it.toList()), it)
  }

  private fun searchSorted(source: ImmutableMultiset<Char>, charSequence: CharSequence, result: MutableSet<MutableSet<CharSequence>> = hashSetOf(), seen: MutableSet<CharSequence> = hashSetOf()): MutableSet<MutableSet<CharSequence>> {
    //    println("Subsequence: $charSequence")
        seen.add(charSequence)
    var pointer = rootNode
    for ((i, c) in charSequence.toString().asSequence().withIndex()) {

      pointer = pointer[c] ?: break
      val matches = pointer.matches
      if (!matches.isEmpty()) {
        val matchSets = matches.map { hashSetOf(it) }
        result.addAll(matchSets)
        if (i < charSequence.length - 1) {
          val remaining = HashMultiset.create(source)
          matches.first().toString().toList().forEach { remaining.remove(it) }
          for (subResult in search(String(remaining.toCharArray()))) {
            for (match in matchSets) {
              result.add(subResult.union(match).toHashSet())
            }
          }
        }
      }
    }
    charSequence.combinations().filter { it !in seen }.forEach { subSequence ->
      searchSorted(source, subSequence, result, seen)
    }
    return result
  }

  fun prettyPrint(printWriter: PrintWriter) = rootNode.prettyPrint(printWriter)

  abstract inner class AbstractNode
  protected constructor(open val parent: AbstractNode?, val depth: Int, val value: Char) {

    val isRoot: Boolean
      get() = this === rootNode

    val children: Map<Char, Node>
      get() = nodeChildren.row(this)

    open val isLeaf: Boolean
      get() = false

    open val matches: Set<CharSequence>
      get() = emptySet()

    operator fun get(value: Char): Node? = nodeChildren[this, value]

    fun addChild(value: Char, match: CharSequence? = null): Node = Node(this, depth + 1, value).apply {
      if (match != null) {
        leafMatches[this].add(match)
      }
      if (nodeChildren.containsRow(this)) {
        throw IllegalStateException("Hash collision occurred: $this")
      }
      nodeChildren[this@AbstractNode, value] = this
    }

    fun getOrAddChild(value: Char, match: CharSequence? = null): Node = children[value]?.apply {
      if (match != null) {
        leafMatches[this].add(match)
      }
    } ?: addChild(value, match)

    private fun positionStringBuilder(): StringBuilder {
      return (parent?.positionStringBuilder()?.append(value) ?: StringBuilder())
    }

    val position: String by lazy {
      positionStringBuilder().toString()
    }

    private val code = ++nodeCount
    override fun hashCode() = code

    override fun equals(other: Any?) = other === this || other is Node && other.hashCode() == this.hashCode()

    override fun toString() = "Node(depth = $depth, value = $value)"

    fun prettyPrint(printWriter: PrintWriter): Unit = with (printWriter) {
      for (i in 0 until depth * 2) {
        append(" ")
      }
      append("└(")
      if (value == UNDEFINED_CHAR) {
        append("ROOT")
      } else {
        append(value)
      }
      append(") [")
      append(matches.joinToString(", "))
      append("]\n")
      for ((v, child) in children) {
        child.prettyPrint(printWriter)
      }
    }

  }

  inner class Node(override val parent: AbstractNode, depth: Int, value: Char)
  : AbstractNode(parent, depth, value) {

    override val isLeaf: Boolean
      get() = !leafMatches[this].isEmpty()

    override val matches: Set<CharSequence>
      get() = leafMatches[this] as Set<CharSequence>

  }
}