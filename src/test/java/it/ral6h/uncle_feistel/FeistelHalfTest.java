package it.ral6h.uncle_feistel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FeistelHalfTest {
    @Test
    void testConstructor() {
        // Test case 1: Original test with mixed binary sequence
        var strRepr1 = "010100110101001";
        boolean[] expectedArg1 = { false, true, false, true, false, false, true, true, false, true, false, true, false,
                false, true };
        var exp1 = new FeistelHalf(expectedArg1);
        var act1 = new FeistelHalf(strRepr1);
        assertEquals(exp1, act1, "Mixed binary sequence should match");

        // Test case 2: All zeros
        var strRepr2 = "0000000";
        boolean[] expectedArg2 = { false, false, false, false, false, false, false };
        var exp2 = new FeistelHalf(expectedArg2);
        var act2 = new FeistelHalf(strRepr2);
        assertEquals(exp2, act2, "All zeros sequence should match");

        // Test case 3: All ones
        var strRepr3 = "1111111";
        boolean[] expectedArg3 = { true, true, true, true, true, true, true };
        var exp3 = new FeistelHalf(expectedArg3);
        var act3 = new FeistelHalf(strRepr3);
        assertEquals(exp3, act3, "All ones sequence should match");

        // Test case 4: Single digit sequences
        var strRepr4_0 = "0";
        boolean[] expectedArg4_0 = { false };
        var exp4_0 = new FeistelHalf(expectedArg4_0);
        var act4_0 = new FeistelHalf(strRepr4_0);
        assertEquals(exp4_0, act4_0, "Single zero should match");

        var strRepr4_1 = "1";
        boolean[] expectedArg4_1 = { true };
        var exp4_1 = new FeistelHalf(expectedArg4_1);
        var act4_1 = new FeistelHalf(strRepr4_1);
        assertEquals(exp4_1, act4_1, "Single one should match");
    }

    @Test
    void testInvalidConstructorInputs() {
        // Test invalid inputs
        assertThrows(IllegalArgumentException.class,
                () -> new FeistelHalf(""),
                "Empty string should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class,
                () -> new FeistelHalf((String) null), "Null input should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class,
                () -> new FeistelHalf("010201"),
                "Non-binary character should throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class,
                () -> new FeistelHalf("01 0"),
                "Whitespace should throw IllegalArgumentException");
    }

    @Test
    void testStandardLeftShift() {
        // Test standard left shift
        var input = new FeistelHalf("01001001");
        var shift = 2;
        var expectedResult = new FeistelHalf("00100101");
        input.shiftLeft(shift);
        assertEquals(expectedResult, input, "Left shift by 2 should work correctly");
    }

    @Test
    void testFullLengthShift() {
        // Test full-length shift (equivalent to no shift)
        var input = new FeistelHalf("01001001");
        var shift = 8;
        var expectedResult = new FeistelHalf("01001001");
        input.shiftLeft(shift);
        assertEquals(expectedResult, input, "Full-length shift should return original array");
    }

    @Test
    void testShiftLargerThanLength() {
        // Test shift larger than array length (wrapping)
        var input = new FeistelHalf("01001001"); // 00100101
        var shift = 10;
        var expectedResult = new FeistelHalf("00100101");
        input.shiftLeft(shift);
        assertEquals(expectedResult, input, "Shift larger than length should wrap around");
    }

    @Test
    void testZeroShift() {
        // Test zero shift
        var input = new FeistelHalf("01001001");
        var shift = 0;
        var expectedResult = new FeistelHalf("01001001");
        input.shiftLeft(shift);
        assertEquals(expectedResult, input, "Zero shift should return original array");
    }

    @Test
    void testSingleBitArrayShift() {
        // Test single-bit array
        var input = new FeistelHalf("1");
        var shift = 1;
        var expectedResult = new FeistelHalf("1");
        input.shiftLeft(shift);
        assertEquals(expectedResult, input, "Single-bit array shift should work");
    }

    @Test
    void testAllZeroArrayShift() {
        // Test all-zero array
        var input = new FeistelHalf("00000000");
        var shift = 3;
        var expectedResult = new FeistelHalf("00000000");
        input.shiftLeft(shift);
        assertEquals(expectedResult, input, "All-zero array shift should remain zero");
    }

    @Test
    void testNegativeLeftShift() {
        // Test standard left shift
        var input = new FeistelHalf("01001001");
        var shift = -2;
        var expectedResult = new FeistelHalf("01010010");
        input.shiftLeft(shift);
        assertEquals(expectedResult, input, "Left shift by -2 should work correctly");
    }

    @Test
    void testBitwiseAnd() {
        // Basic AND operation
        var input1 = new FeistelHalf("10101010");
        var other1 = new FeistelHalf("11110000");
        var expectedResult1 = new FeistelHalf("10100000");
        input1.and(other1);
        assertEquals(expectedResult1, input1, "Bitwise AND should work correctly");

        // All zeros
        var input2 = new FeistelHalf("11111111");
        var other2 = new FeistelHalf("00000000");
        var expectedResult2 = new FeistelHalf("00000000");
        input2.and(other2);
        assertEquals(expectedResult2, input2, "AND with all zeros should result in all zeros");

        // All ones
        var input3 = new FeistelHalf("11111111");
        var other3 = new FeistelHalf("11111111");
        var expectedResult3 = new FeistelHalf("11111111");
        input3.and(other3);
        assertEquals(expectedResult3, input3, "AND with all ones should result in all ones");

        // Single bit
        var input4 = new FeistelHalf("1");
        var other4 = new FeistelHalf("1");
        var expectedResult4 = new FeistelHalf("1");
        input4.and(other4);
        assertEquals(expectedResult4, input4, "AND with single bit should work");
    }

    @Test
    void testBitwiseOr() {
        // Basic OR operation
        var input1 = new FeistelHalf("10101010");
        var other1 = new FeistelHalf("11110000");
        var expectedResult1 = new FeistelHalf("11111010");
        input1.or(other1);
        assertEquals(expectedResult1, input1, "Bitwise OR should work correctly");

        // All zeros
        var input2 = new FeistelHalf("00000000");
        var other2 = new FeistelHalf("00000000");
        var expectedResult2 = new FeistelHalf("00000000");
        input2.or(other2);
        assertEquals(expectedResult2, input2, "OR with all zeros should result in all zeros");

        // All ones
        var input3 = new FeistelHalf("11111111");
        var other3 = new FeistelHalf("00000000");
        var expectedResult3 = new FeistelHalf("11111111");
        input3.or(other3);
        assertEquals(expectedResult3, input3, "OR with all zeros should result in original");

        // Single bit
        var input4 = new FeistelHalf("1");
        var other4 = new FeistelHalf("0");
        var expectedResult4 = new FeistelHalf("1");
        input4.or(other4);
        assertEquals(expectedResult4, input4, "OR with single bit should work");
    }

    @Test
    void testBitwiseXor() {
        // Basic XOR operation
        var input1 = new FeistelHalf("10101010");
        var other1 = new FeistelHalf("11110000");
        var expectedResult1 = new FeistelHalf("01011010");
        input1.xor(other1);
        assertEquals(expectedResult1, input1, "Bitwise XOR should work correctly");

        // All zeros
        var input2 = new FeistelHalf("00000000");
        var other2 = new FeistelHalf("00000000");
        var expectedResult2 = new FeistelHalf("00000000");
        input2.xor(other2);
        assertEquals(expectedResult2, input2, "XOR with all zeros should result in all zeros");

        // All ones
        var input3 = new FeistelHalf("11111111");
        var other3 = new FeistelHalf("11111111");
        var expectedResult3 = new FeistelHalf("00000000");
        input3.xor(other3);
        assertEquals(expectedResult3, input3, "XOR with all ones should result in all zeros");
    }

    @Test
    void testBitwiseExceptions() {
        var input = new FeistelHalf("10101010");
        var other1 = new FeistelHalf("00111110000");
        var other2 = new FeistelHalf("0");

        assertThrows(IllegalArgumentException.class, () -> input.and(other1));
        assertThrows(IllegalArgumentException.class, () -> input.and(other2));

        assertThrows(IllegalArgumentException.class, () -> input.or(other1));
        assertThrows(IllegalArgumentException.class, () -> input.or(other2));

        assertThrows(IllegalArgumentException.class, () -> input.xor(other1));
        assertThrows(IllegalArgumentException.class, () -> input.xor(other2));

    }

}
