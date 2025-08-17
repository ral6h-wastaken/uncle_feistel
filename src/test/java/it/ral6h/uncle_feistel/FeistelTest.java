package it.ral6h.uncle_feistel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static it.ral6h.uncle_feistel.UncleFeistel.*;

import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;

class FeistelTest {
    final UncleFeistel uf = new UncleFeistel();

    @Test
    void testRound() {
        var l = new FeistelHalf("0001000111001010");
        var r = new FeistelHalf("1101010101010001");
        var input = new Feistel(l, r);

        var res = feistelRound(UnaryOperator.identity(), input);

        assertEquals(new Feistel(r, new FeistelHalf("1100010010011011")), res);
    }

    @Test
    void testLeftInverse() {
        UnaryOperator<FeistelHalf> f = UnaryOperator.identity();

        var l = new FeistelHalf("0001000111001010");
        var r = new FeistelHalf("1101010101010001");
        var input = new Feistel(l, r);

        assertEquals(input, unfeistelRound(f, feistelRound(f, input)));
    }

    @Test
    void testRightInverse() {
        UnaryOperator<FeistelHalf> f = UnaryOperator.identity();

        var l = new FeistelHalf("0001000111001010");
        var r = new FeistelHalf("1101010101010001");
        var input = new Feistel(l, r);

        assertEquals(input, feistelRound(f, unfeistelRound(f, input)));
    }
}
