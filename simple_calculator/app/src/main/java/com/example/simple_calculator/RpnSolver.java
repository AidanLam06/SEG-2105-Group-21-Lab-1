package com.example.simple_calculator;

import java.util.Stack;

public class RpnSolver {

    public String solve_rpn(String rpn) {
        int len = rpn.length();
        Stack<Double> solver = new Stack<>();

        for (int i = 0; i <= len; i++) {
            char c = rpn.charAt(i);
            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < len && (Character.isDigit(rpn.charAt(i)) || rpn.charAt(i) == '.')) {
                    num.append(rpn.charAt(i));
                    i++;
                }
                i--;
                solver.push(Double.parseDouble(num.toString()));
            } else if ("+-*/^".indexOf(c) != -1) {
                if (solver.size() < 2) return "Error";
                double b = solver.pop();
                double a = solver.pop();
                solver.push(solve_operation(a, b, c));
            } else {
                return "Error: unrecognized operator";
            }
        }

        if (solver.isEmpty()) return "Error";
        return String.valueOf(solver.peek());
    }

    private double solve_operation(double a, double b, char op) {
        switch(op) {
            case '+': return a+b;
            case '-': return a-b;
            case '*': return a*b;
            case '/': return a/b;
            case '^': return Math.pow(a,b);
            default: return 0;
        }
    }
}
