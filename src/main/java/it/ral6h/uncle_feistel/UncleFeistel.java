package it.ral6h.uncle_feistel;

import java.util.function.UnaryOperator;
import java.util.logging.Logger;

/**
 * Utility class for performing Feistel cipher operations.
 * Provides methods for forward and inverse Feistel rounds.
 */
public class UncleFeistel {
  private static final Logger log = Logger.getLogger(UncleFeistel.class.getCanonicalName());

  /**
   * Performs a single Feistel cipher round transformation.
   * <p>
   * Transforms the input [L][R] to [R][L ⊕ f(R)] where:
   * <ul>
   * <li>L' = R</li>
   * <li>R' = L ⊕ f(R)</li>
   * </ul>
   * 
   * @param f     the round function to apply to the right half
   * @param input the Feistel structure to transform
   * @return a new Feistel structure with the round transformation applied
   */
  public static Feistel feistelRound(UnaryOperator<FeistelHalf> f, Feistel input) {
    log.info("Starting feistelRound with input %s".formatted(input));

    /*
     * [ l ][ r ]
     *
     * l' = r
     * r' = l XOR f(r)
     *
     * [ l' ][ r' ]
     * [ r ] [ l XOR f(r) ]
     * 
     * to invert:
     *
     * r = l'
     * r' = l xor f(r) => r' xor f(r) = l xor f(r) xor f(r) => r' xor f(r) = l
     *
     * l = r' xor f(r)
     */
    var l_prime = input.right();
    var r_prime = FeistelHalf.copyOf(input.left());
    r_prime.xor(f.apply(input.right()));

    var result = new Feistel(l_prime, r_prime);
    log.info("Ended feistelRound with result %s".formatted(result));

    return result;
  }

  /**
   * Performs the inverse of a Feistel cipher round.
   * <p>
   * Given the output [L'][R'] of a Feistel round, recovers the original [L][R]
   * where:
   * <ul>
   * <li>R = L'</li>
   * <li>L = R' ⊕ f(L')</li>
   * </ul>
   * 
   * @param f        the same round function used in the forward transformation
   * @param input the Feistel structure to invert
   * @return the original Feistel structure before the round transformation
   */
  public static Feistel unfeistelRound(UnaryOperator<FeistelHalf> f, Feistel input) {
    var r = input.left();

    var l = FeistelHalf.copyOf(input.right());
    l.xor(f.apply(r)); // r' xor f(r)

    var result = new Feistel(l, r);
    return result;
  }
}
