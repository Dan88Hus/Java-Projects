package collections;

import java.util.*;

public final class ImmutableCollection<E> {


  private final E[] elements;

  @SuppressWarnings("unchecked")
  private ImmutableCollection(E[] elements) {
    // Create a defensive copy to ensure true immutability
    this.elements = Arrays.copyOf(elements, elements.length);
  }

  // Specific overload for empty collection
  @SuppressWarnings("unchecked")
  public static <E> ImmutableCollection<E> of() {
    return new ImmutableCollection<>((E[]) new Object[0]);
  }

  @SafeVarargs
  public static <E> ImmutableCollection<E> of(E... elements) {
    // Validate that no elements are null
    if (elements != null) {
      for (E element : elements) {
        Objects.requireNonNull(element, "Collection elements cannot be null");
      }
    }

    return new ImmutableCollection<>(elements);
  }

  public boolean contains(E element) {
    if (element == null) {
      return false;
    }

    for (E e : elements) {
      if (element.equals(e)) {
        return true;
      }
    }
    return false;
  }

  public int size() {
    return elements.length;
  }

  public boolean isEmpty() {
    return elements.length == 0;
  }

}

/**
public class Multiset<E> {
  private final Map<E, Integer> countMap = new HashMap<>();

  public void add(E element) {
    countMap.put(element, countMap.getOrDefault(element, 0) + 1);
  }

  public void add(E element, int occurrences) {
    if (occurrences <= 0) return;
    countMap.put(element, countMap.getOrDefault(element, 0) + occurrences);
  }

  public boolean contains(E element) {
    return countMap.containsKey(element);
  }

  public int count(E element) {
    return countMap.getOrDefault(element, 0);
  }

  public Set<E> elementSet() {
    return new HashSet<>(countMap.keySet());
  }

  public void remove(E element) {
    countMap.computeIfPresent(element, (k, v) -> v > 1 ? v - 1 : null);
  }

  public void remove(E element, int occurrences) {
    if (occurrences <= 0) return;
    countMap.computeIfPresent(element, (k, v) -> v > occurrences ? v - occurrences : null);
  }

  public void setCount(E element, int count) {
    if (count > 0) {
      countMap.put(element, count);
    } else {
      countMap.remove(element);
    }
  }

  public void setCount(E element, int oldCount, int newCount) {
    if (countMap.getOrDefault(element, 0) == oldCount) {
      setCount(element, newCount);
    }
  }

  @Override
  public String toString() {
    List<E> elements = new ArrayList<>();
    for (Map.Entry<E, Integer> entry : countMap.entrySet()) {
      elements.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
    }
    return elements.toString();
  }
}
**/