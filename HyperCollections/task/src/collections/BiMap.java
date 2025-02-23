package collections;

import java.util.*;

public class BiMap<K, V> {
  private final Map<K, V> forwardMap = new HashMap<>();
  private final Map<V, K> inverseMap = new HashMap<>();

  public BiMap() {}

  public V put(K key, V value) {
    if (forwardMap.containsKey(key) || inverseMap.containsKey(value)) {
      throw new IllegalArgumentException("Key or value already exists in BiMap");
    }
    forwardMap.put(key, value);
    inverseMap.put(value, key);
    return value;
  }

  public void putAll(Map<K, V> map) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      if (forwardMap.containsKey(entry.getKey()) || inverseMap.containsKey(entry.getValue())) {
        throw new IllegalArgumentException("Duplicate key or value in map");
      }
    }
    for (Map.Entry<K, V> entry : map.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  public Set<V> values() {
    return new HashSet<>(inverseMap.keySet());
  }

  public V forcePut(K key, V value) {
    if (forwardMap.containsKey(key)) {
      V oldValue = forwardMap.remove(key);
      inverseMap.remove(oldValue);
    }
    if (inverseMap.containsKey(value)) {
      K oldKey = inverseMap.remove(value);
      forwardMap.remove(oldKey);
    }
    forwardMap.put(key, value);
    inverseMap.put(value, key);
    return value;
  }

  public BiMap<V, K> inverse() {
    BiMap<V, K> inverseBiMap = new BiMap<>();
    inverseBiMap.forwardMap.putAll(inverseMap);
    inverseBiMap.inverseMap.putAll(forwardMap);
    return inverseBiMap;
  }

  @Override
  public String toString() {
    return forwardMap.toString();
  }
}

// 🚀 Moved Multiset OUTSIDE of BiMap ✅
class Multiset<E> {
  private final Map<E, Integer> countMap = new HashMap<>();

  public Multiset() {} // ✅ Add a constructor

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

