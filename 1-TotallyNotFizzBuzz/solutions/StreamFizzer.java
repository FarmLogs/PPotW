import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StreamFizzer {

  public static void main(String[] args) {
    final List<Entry> values = StreamSupport.stream(new Range<>(Entry::new, 101).spliterator(), false)
      .collect(Collectors.toList());
    values.parallelStream()
      .filter(Entry::isFizz)
      .forEach(entry -> entry.append("Fizz"));
    values.parallelStream()
      .filter(Entry::isBuzz)
      .forEach(entry -> entry.append("Buzz"));
    values.parallelStream()
      .filter(Entry::isBoring)
      .forEach(entry -> entry.append(String.valueOf(entry.value)));
    values.forEach(System.out::println);
  }

  private interface Factory<K, V> {
    V create(K k);
  }

  private static class Entry {
    public final int value;
    private final StringBuilder text = new StringBuilder();

    public Entry(int value) {
      this.value = value;
    }

    public boolean isFizz() {
      return value % 3 == 0;
    }

    public boolean isBuzz() {
      return value % 5 == 0;
    }

    public boolean isBoring() {
      return !isFizz() && !isBuzz();
    }

    public void append(String string) {
      text.append(string);
    }

    @Override
    public String toString() {
      return text.toString();
    }
  }

  private static class Range<T> implements Iterable<T>, Iterator<T> {

    private final Factory<Integer, T> factory;
    private final int start;
    private final int step;
    private final int end;

    private int position;

    public Range(Factory<Integer, T> factory, int start, int step, int end) {
      this.factory = factory;
      this.start = start;
      this.step = step;
      this.end = end;
      this.position = start;
    }

    public Range(Factory<Integer, T> factory, int end) {
      this(factory, 0, 1, end);
    }

    @Override
    public Iterator<T> iterator() {
      return this;
    }

    @Override
    public boolean hasNext() {
      return position + step < end;
    }

    @Override
    public T next() {
      try { return factory.create(position); } finally { position += step; }
    }

  }

}
