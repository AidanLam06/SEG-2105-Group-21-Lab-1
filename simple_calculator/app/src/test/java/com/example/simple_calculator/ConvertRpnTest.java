package com.example.simple_calculator;

import static org.junit.Assert.*;
import org.junit.Test;

public class ConvertRpnTest {

    RpnConverter converter = new RpnConverter();
    @Test
    public void testSimpleAddition() {
        assertEquals("3 4 +", converter.convert_to_rpn("3+4"));
    }
    // should pass

    @Test
    public void testSimpleSubtraction() {
        assertEquals("10 2 -", converter.convert_to_rpn("10-2"));
        // should pass
    }

    @Test
    public void testParentheses() {
        assertEquals("3 4 + 2 *", converter.convert_to_rpn("(3+4)*2"));
        // should fail
    }

    @Test
    public void testMixedOperators() {
        assertEquals("3 4 2 * +", converter.convert_to_rpn("3+4*2"));
        // should fail
    }

    @Test
    public void testDecimalNumbers() {
        assertEquals("3.5 2 +", converter.convert_to_rpn("3.5+2"));
    }
    // should pass

    @Test
    public void testNestedParentheses() {
        assertEquals("3 4 2 * 1 5 - 2 3 ^ ^ / +", converter.convert_to_rpn("3+4*2/(1-5)^(2^3)"));
        // should fail
    }
}