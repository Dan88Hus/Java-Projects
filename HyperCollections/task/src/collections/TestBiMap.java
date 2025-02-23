package collections;

public class TestBiMap {
  public static void main(String[] args) {
    Multiset<String> multiSet = new Multiset<>();
    multiSet.add("apple");
    multiSet.add("banana", 2);
    System.out.println(multiSet);  // Expected output: [apple, banana, banana]
  }
}
