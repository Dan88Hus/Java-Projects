package collections;

import java.util.Arrays;
import java.util.Objects;

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