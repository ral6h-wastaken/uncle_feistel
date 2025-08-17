package it.ral6h.uncle_feistel.util;

public class MathUtils {
  public static int normalizedMod(int arg, int base) {
    int rem = arg % base;

    return (rem >= 0) ? rem : base + rem;
  }
}
