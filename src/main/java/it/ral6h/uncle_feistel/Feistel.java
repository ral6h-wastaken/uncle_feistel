package it.ral6h.uncle_feistel;

/**
 * Represents a Feistel cipher structure with left and right halves of equal
 * length.
 * Both halves must be non-null and contain boolean arrays of the same size.
 * 
 * @param left  the left half of the Feistel structure
 * @param right the right half of the Feistel structure
 * @throws IllegalArgumentException if either half is null or if the halves have
 *                                  different lengths
 */
public record Feistel(FeistelHalf left, FeistelHalf right) {
  public Feistel {
    if (left == null || right == null)
      throw new IllegalArgumentException("Both halves should be not null");
    if (left.arg().length != right.arg().length)
      throw new IllegalArgumentException("Both halves should be of the same size");
  }

  @Override
  public String toString() {
    return "[%s%s]".formatted(left, right);
  }
}
