package it.ral6h.uncle_feistel;

import java.util.Arrays;
import static it.ral6h.uncle_feistel.util.MathUtils.*;

/**
 * convenient representation of a binary number
 *
 * @throws: IllegalArgumentException if the underlying array is either null or
 *                                   empty
 */
public record FeistelHalf(boolean[] arg) {

  /**
   * Creates a deep copy of the given FeistelHalf instance.
   * 
   * @param original the FeistelHalf instance to copy
   * @return a new FeistelHalf with a copy of the original's boolean array
   * @throws IllegalArgumentException if original is null
   */
  public static FeistelHalf copyOf(FeistelHalf original) {
    if (original == null) throw new IllegalArgumentException("Original FeistelHalf must be not null");

    var len = original.arg.length;
    return new FeistelHalf(Arrays.copyOf(original.arg, len));
  }

  /**
   * Constructs a FeistelHalf from a binary string representation.
   *
   * <p>
   * Converts a string of '0' and '1' characters into a boolean array.
   * The string must be non-null and non-blank, containing only binary digits.
   * </p>
   *
   * @param repr A string representing a binary sequence
   * @throws IllegalArgumentException if the input is null, blank,
   *                                  or contains characters other than '0' and
   *                                  '1'
   */
  public FeistelHalf(String repr) {
    this(
        parseStringRepr(repr));
  }

  public FeistelHalf {
    if (arg == null || arg.length == 0)
      throw new IllegalArgumentException("The underlying array must have length > 0");
  }

  /**
   * bitwise AND operation
   *
   * @throws: IllegalArgumentException if other == null or other.len() != this.len
   */
  public void and(FeistelHalf other) {
    if (other == null || other.len() != this.len())
      throw new IllegalArgumentException("other must be compatible with this");

    for (int i = 0; i < len(); i++)
      this.arg[i] = this.arg[i] && other.arg[i];
  }

  /**
   * bitwise OR operation
   *
   * @throws: IllegalArgumentException if other == null or other.len() != this.len
   */
  public void or(FeistelHalf other) {
    if (other == null || other.len() != this.len())
      throw new IllegalArgumentException("other must be compatible with this");

    for (int i = 0; i < len(); i++)
      this.arg[i] = this.arg[i] || other.arg[i];
  }

  /**
   * bitwise XOR operation
   *
   * @throws: IllegalArgumentException if other == null or other.len() != this.len
   */
  public void xor(FeistelHalf other) {
    if (other == null || other.len() != this.len())
      throw new IllegalArgumentException("other must be compatible with this");

    for (int i = 0; i < len(); i++)
      this.arg[i] = this.arg[i] ^ other.arg[i];
  }

  /**
   * Performs a circular left shift on the underlying boolean array.
   *
   * <p>
   * Shifts all elements to the left by the specified padding,
   * with elements wrapping around to the right side of the array.
   * Modifies the array in-place.
   * </p>
   *
   * <p>
   * Shift behavior:
   * </p>
   * <ul>
   * <li>Positive padding shifts elements left</li>
   * <li>Zero padding returns the original array</li>
   * <li>Padding larger than array length wraps around</li>
   * <li>Negative padding of -n is equivalent to a rightshift of +n</li>
   * </ul>
   *
   * @param padding Number of positions to shift left
   * @implSpec Uses circular shift with O(n) time complexity
   *
   * @example
   *          // Given array [1,0,1,0]
   *          shiftLeft(1) // Becomes [0,1,0,1]
   *          shiftLeft(2) // Becomes [1,0,1,0]
   *          shiftLeft(5) // Equivalent to shiftLeft(1)
   */
  public void shiftLeft(int padding) {
    int len = this.arg.length;
    padding = normalizedMod(padding, len);

    boolean[] helper = Arrays.copyOf(this.arg, len);

    for (int i = 0; i < len; i++) {
      this.arg[normalizedMod(i - padding, len)] = helper[i];
    }
  }

  /**
   * equivalent to shiftLeft(-padding)
   *
   * @param padding
   * @see shiftLeft
   */
  public void shiftRight(int padding) {
    this.shiftLeft(padding * -1);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof FeistelHalf fh))
      return false;

    return Arrays.equals(this.arg, fh.arg());
  }

  @Override
  public String toString() {
    var resultBuilder = new StringBuilder("[");

    for (boolean b : this.arg()) {
      if (b)
        resultBuilder.append(1);
      else
        resultBuilder.append(0);
    }

    return resultBuilder.append("]").toString();
  }

  private static boolean[] parseStringRepr(String repr) {
    if (repr == null ||
        repr.isBlank())
      throw new IllegalArgumentException("String representation must be non empty or null");

    var result = new boolean[repr.length()];
    char[] reprCharArray = repr.toCharArray();

    for (int i = 0; i < result.length; i++) {
      result[i] = switch (reprCharArray[i]) {
        case '0' -> false;
        case '1' -> true;
        default -> throw new IllegalArgumentException("String representation must be a valid binary");
      };
    }

    return result;
  }

  private int len() {
    return this.arg.length;
  }
}
