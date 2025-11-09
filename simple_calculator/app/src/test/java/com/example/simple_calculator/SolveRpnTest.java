package com.example.simple_calculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class SolveRpnTest {

    private RpnSolver solver = new RpnSolver();

    @Test
    public void testSimpleAddition() {
        assertEquals("7.0", solver.solve_rpn("3 4 +"));
    }

    @Test
    public void testSubtraction() {
        assertEquals("6.0", solver.solve_rpn("10 4 -"));
    }

    @Test
    public void testMultiplication() {
        assertEquals("6.0", solver.solve_rpn("2 3 *"));
    }

    @Test
    public void testDivision() {
        assertEquals("4.0", solver.solve_rpn("8 2 /"));
    }

    @Test
    public void testExponentiation() {
        assertEquals("8.0", solver.solve_rpn("2 3 ^"));
    }

    @Test
    public void testInvalidExpression() {
        assertEquals("Error", solver.solve_rpn("3 +"));
    }

    @Test
    public void testEmptyInput() {
        assertTrue(solver.solve_rpn("").contains("Error"));
    }
}
