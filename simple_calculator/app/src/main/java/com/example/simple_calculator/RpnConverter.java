package com.example.simple_calculator;

public class RpnConverter {

    /**
     * Converts an infix expression to RPN
     * This version intentionally has a precedence bug for * and / operators
     * they are treated the same as + and -
     */
    public String convert_to_rpn(String buffer) {
        int len = buffer.length();
        StringBuilder result = new StringBuilder();
        char[] stack = new char[len];
        int top = -1;

        for (int i = 0; i < len; i++) {
            char c = buffer.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                while (i < len && (Character.isDigit(buffer.charAt(i)) || buffer.charAt(i) == '.')) {
                    result.append(buffer.charAt(i));
                    i++;
                }
                result.append(' ');
                i--; // negate the increment made on i 3 lines above
            } else if (c == '(') {
                stack[++top] = '(';
            } else if (c == ')') {
                while (top != -1 && stack[top] != '(') {
                    result.append(stack[top--]);
                }
                top--;
            } else {
                while (top != -1 && stack[top] != '(') {
                    result.append(stack[top--]);
                }
                stack[++top] = c;
            }
        }

        while (top != -1) {
            result.append(stack[top--]);
        }

        return result.toString();
    }
}