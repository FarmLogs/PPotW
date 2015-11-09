import com.google.common.collect.*
import java.io.PrintWriter

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 11/4/15
 * (C) 2015 Damian Wieczorek
 *
 * Simple trie of chars for searching the dictionary
 */
class CharSequenceTrie {

  /**
   * Map of Node -> CharSequences matching that node
   */
  private val leafMatches = Multimaps.newSetMultimap<Node, CharSequence>(hashMapOf()) { Sets.newHashSet() }

  /**
   * Map of Node -> Children of that node
   */
  private val nodeChildren = HashBasedTable.create<AbstractNode, Char, Node>()

  /**
   * Anonymous node implementation for the root node
   */
  val rootNode = object : AbstractNode(null, 0, UNDEFINED_CHAR) {
    override val isRoot = true
    override val isLeaf = false
    override val matches = emptySet<CharSequence>()
  }

  /**
   * Inserts a CharSequence into the trie without sorting it in advance.
   *
   * @param charSequence  The unsorted CharSequence to insert
   */
  fun insert(charSequence: CharSequence) {
    var pointer = rootNode
    for (i in 0 until charSequence.length) {
      pointer = pointer.getOrAddChild(charSequence[i], if (i == charSequence.length - 1) charSequence else null)
    }
  }

  /**
   * Sorts a CharSequence and inserts it into the trie
   *
   * @param charSequence  The unsorted CharSequence to be sorted and inserted into the trie
   */
  fun insertSorted(charSequence: CharSequence) {
    val sorted = charSequence.toString().toSortedSet()
    var pointer = rootNode
    for ((i, c) in sorted.withIndex()) {
      pointer = pointer.getOrAddChild(c, if (i == charSequence.length - 1) charSequence else null)
    }
  }

  /**
   * Search for an unsorted CharSequence in the trie by first sorting it, and then searching for it.
   *
   * @param charSequence  The CharSequence to search for
   */
  fun search(charSequence: CharSequence) = String(charSequence.toString().toCharArray().apply { sort() }).let {
    searchSorted(ImmutableMultiset.copyOf(it.toList()), it)
  }

  /**
   * Recursively search the trie for a CharSequence, all of its permutations, and all
   * the permutations of its sub-sequences.
   * This implementation is about the worst thing ever. But it works :)
   * I'll probably implement this for real if/when the competition gets a bit stiffer. ;)
   *
   * DON'T READ TOO DEEP INTO THIS, IT'S HELD TOGETHER WITH GLUE AND MY CPU'S TEARS
   */
  private fun searchSorted(source: ImmutableMultiset<Char>,
                           charSequence: CharSequence,
                           result: MutableSet<MutableSet<CharSequence>> = hashSetOf(),
                           seen: MutableSet<CharSequence> = hashSetOf()): MutableSet<MutableSet<CharSequence>> {
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

  /**
   * Pretty print the trie into a PrintWriter. Don't try to stringify this representation...
   * it will probably be very large.
   *
   * @param printWriter The writer to print to
   */
  fun prettyPrint(printWriter: PrintWriter) = rootNode.prettyPrint(printWriter)

  // terrible way to generate hash code, but I can't stop getting collisions
  // shouldn't matter; duplicate nodes are never inserted
  private var nodeCount = 0

  /**
   * Abstract base class representing a node which may be the root node,
   * an inner node, or a leaf node.
   *
   * @property parent The parent of this node, or `null` if it is the root node
   * @property depth  The depth of this node in the trie
   * @property value  The character represented by this node
   */
  abstract inner class AbstractNode
  protected constructor(open val parent: AbstractNode?, val depth: Int, val value: Char) {

    /**
     * Whether this node is the root node
     */
    abstract val isRoot: Boolean

    /**
     * This node's children, represented by an edge map to the child's value
     */
    val children: Map<Char, Node>
      get() = nodeChildren.row(this)

    /**
     * Whether this node is a "leaf". I'm using the term more generally to indicate
     * a node which has one or more CharSequences matched to it. Leaves may still have children.
     */
    abstract val isLeaf: Boolean

    /**
     * The CharSequences matched by this node, e.g. given a matching string A,
     * one can sort the string and traverse the tree downward to arrive at this node.
     */
    abstract val matches: Set<CharSequence>

    /**
     * Travel along the edge connecting this node to the child having a given value, if one exists.
     *
     * @param value The value of the child
     * @return The child, if it exists, else `null`
     */
    operator fun get(value: Char): Node? = nodeChildren[this, value]

    /**
     * Insert a now node as a child of this node. If `match` is provided, the match will be saved and can
     * be retrieved by traversing down to this node again.
     *
     * @param value The value of the node to be inserted
     * @param match A CharSequence terminating on this node, if one exists
     * @return The new node
     */
    fun addChild(value: Char, match: CharSequence? = null): Node = Node(this, depth + 1, value).apply {
      if (match != null) {
        leafMatches[this] += match
      }
      if (nodeChildren.containsRow(this)) {
        throw IllegalStateException("Hash collision occurred or node exists: $this")
      }
      nodeChildren[this@AbstractNode, value] = this
    }

    /**
     * Travel along the edge connecting this node to the child having a given value, if one exists.
     * Otherwise, insert a now node as a child of this node.
     * If `match` is provided, the match will be saved to the node
     * and can be retrieved by traversing down to this node again.
     *
     * @param value The value of the child node or node to be inserted
     * @param match A CharSequence terminating on this node, if one exists
     * @return The existing node, if it exists, or a new node (which the match attached in both cases)
     */
    fun getOrAddChild(value: Char, match: CharSequence? = null): Node = children[value]?.apply {
      if (match != null) {
        leafMatches[this] += match
      }
    } ?: addChild(value, match)

    /**
     * Build the string of chars from the root of the trie leading down to this node.
     *
     * @return Either a new [StringBuilder] or the parent's [StringBuilder], appended with this node's value
     */
    private fun positionStringBuilder(): StringBuilder {
      return parent?.positionStringBuilder()?.append(value) ?: StringBuilder()
    }

    /**
     * The name of the position of this node
     *
     * @see [positionStringBuilder]
     */
    val position: String by lazy {
      positionStringBuilder().toString()
    }

    /**
     * Bad things
     */
    private val code = ++nodeCount
    override fun hashCode() = code

    /**
    * When bad things go worse
    */
    override fun equals(other: Any?) = other === this || other is Node && other.hashCode() == this.hashCode()

    /**
     * Basic stringification for this node. Does not perform any traversal.
     */
    override fun toString() = "Node(depth = $depth, value = $value)"

    /**
     * Pretty print this node and all of its' children into a PrintWriter.
     * Don't try to stringify this representation... it will probably be very large.
     *
     * @param printWriter The writer to print to
     */
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

  /**
   * Class representing any node other than the root node
   */
  inner class Node(override val parent: AbstractNode, depth: Int, value: Char)
  : AbstractNode(parent, depth, value) {

    override val isRoot: Boolean = false

    override val isLeaf: Boolean
      get() = !leafMatches[this].isEmpty()

    override val matches: Set<CharSequence>
      get() = leafMatches[this]

  }

}
