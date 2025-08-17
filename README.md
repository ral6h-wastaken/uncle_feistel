# uncle_feistel
Simple java library for implementing feistel networks of any dimension.
This is a study project and *by no means production-ready*: performance is lacking and some operations are not in place (mostly due to using records for brevity to define the models).

## Usage
```java
import java.util.function.UnaryOperator;

public class FeistelExample {
    public static void main(String[] args) {
        // Create initial data using string constructor
        FeistelHalf left = new FeistelHalf("1010");   
        FeistelHalf right = new FeistelHalf("0110");
        Feistel original = new Feistel(left, right);
        
        System.out.println("Original: " + original);
        
        // Define round functions using utility methods
        UnaryOperator<FeistelHalf> flipFunction = half -> {
            FeistelHalf allOnes = new FeistelHalf("1111");
            FeistelHalf result = FeistelHalf.copyOf(half);
            result.xor(allOnes); // XOR with all 1s flips all bits
            return result;
        };
        
        UnaryOperator<FeistelHalf> andFunction = half -> {
            FeistelHalf mask = new FeistelHalf("1010");
            FeistelHalf result = FeistelHalf.copyOf(half);
            result.and(mask);
            return result;
        };
        
        // Perform forward Feistel round with flip function
        Feistel encrypted1 = UncleFeistel.feistelRound(flipFunction, original);
        System.out.println("After flip round: " + encrypted1);
        
        // Perform another round with AND function
        Feistel encrypted2 = UncleFeistel.feistelRound(andFunction, encrypted1);
        System.out.println("After AND round: " + encrypted2);
        
        // Perform inverse operations in reverse order
        Feistel decrypted1 = UncleFeistel.unfeistelRound(andFunction, encrypted2);
        System.out.println("After inverse AND: " + decrypted1);
        
        Feistel decrypted2 = UncleFeistel.unfeistelRound(flipFunction, decrypted1);
        System.out.println("After inverse flip: " + decrypted2);
        
        // Use equals method to verify they match
        System.out.println("Matches original: " + 
            original.left().equals(decrypted2.left()) &&
            original.right().equals(decrypted2.right()));
  }
}
```
