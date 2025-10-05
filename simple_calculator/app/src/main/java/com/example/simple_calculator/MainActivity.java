package com.example.simple_calculator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonClear = findViewById(R.id.buttonClear);
        Button buttonExp = findViewById(R.id.buttonExp);
        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDecimal = findViewById(R.id.buttonDecimal);
        Button buttonBase10 = findViewById(R.id.buttonBase10);
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);

        TextView textViewDisplay = findViewById(R.id.textViewDisplay);

        // Operator buttons
        buttonPlus.setOnClickListener(v -> {
            textViewDisplay.append("+");
        });

        buttonMinus.setOnClickListener(v -> {
            textViewDisplay.append("-");
        });

        buttonMultiply.setOnClickListener(v -> {
            textViewDisplay.append("*");
        });

        buttonDivide.setOnClickListener(v -> {
            textViewDisplay.append("/");
        });

        buttonClear.setOnClickListener(v -> {
            textViewDisplay.setText("");
        });

        buttonExp.setOnClickListener(v -> {
            textViewDisplay.append("^");
        });

        buttonEquals.setOnClickListener(v -> {
            String expression = textViewDisplay.getText().toString();
            // and then evaluate the expression (stack implementation and also the rpn stuff)
            String expression_rpn = convert_to_rpn(expression);
            String result = solve_rpn(expression_rpn);
            textViewDisplay.setText(result);
        });

        buttonDecimal.setOnClickListener(v -> {
            textViewDisplay.append(".");
        });

        buttonBase10.setOnClickListener(v -> {
            textViewDisplay.append("*10^");
        });

// Number buttons
        button0.setOnClickListener(v -> {
            textViewDisplay.append("0");
        });

        button1.setOnClickListener(v -> {
            textViewDisplay.append("1");
        });

        button2.setOnClickListener(v -> {
            textViewDisplay.append("2");
        });

        button3.setOnClickListener(v -> {
            textViewDisplay.append("3");
        });

        button4.setOnClickListener(v -> {
            textViewDisplay.append("4");
        });

        button5.setOnClickListener(v -> {
            textViewDisplay.append("5");
        });

        button6.setOnClickListener(v -> {
            textViewDisplay.append("6");
        });

        button7.setOnClickListener(v -> {
            textViewDisplay.append("7");
        });

        button8.setOnClickListener(v -> {
            textViewDisplay.append("8");
        });

        button9.setOnClickListener(v -> {
            textViewDisplay.append("9");
        });
    }

    private double solve_operation(double x1, double x2, char operator) {
        switch (operator) {
            case '^': {
                return Math.pow(x1, x2);
            }
            case '*':
                return x1 * x2;
            case '/':
                return (double) x1 / x2;
            case '+':
                return x1 + x2;
            case '-':
                return x1 - x2;
            default:
                throw new IllegalArgumentException("Invalid Operator: " + operator);
        }
    }

    private int precedence(char c) {
        if (c == '^') {
            return 3;
        } else if (c == '/' || c == '*') {
            return 2;
        } else if (c == '+' || c == '-') {
            return 1;
        } else {
            return -1;
        }
    }

    private String convert_to_rpn(String buffer) {
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
                while (top != -1 &&
                        (precedence(c) < precedence(stack[top]) ||
                                precedence(c) == precedence(stack[top]))) {
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

    private String solve_rpn(String rpn) {
        Log.d("DEBUG: ", rpn);
        int len = rpn.length();
        Stack<Double> solver = new Stack<>();

        for (int i = 0; i < len; i++) {
            char c = rpn.charAt(i);
            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < len && (Character.isDigit(rpn.charAt(i)) || rpn.charAt(i) == '.')) {
                    num.append(rpn.charAt(i));
                    i++;
                }
                i--; // step back one because the for loop will increment
                solver.push(Double.parseDouble(num.toString()));
            }

            else if ("+-*/^".indexOf(c) != -1) {
                if (solver.size() < 2) {
                    Log.e("ERROR: ","Invalid RPN expression: not enough operands");
                    return "Error";
                }
                double b = solver.pop();
                double a = solver.pop();
                double result = solve_operation(a, b, c);
                solver.push(result);
            }

            else {
                Log.e("ERROR:", "Unrecognized operator {" + c + "} in solveRPN()");
            }
        }

        if (solver.isEmpty()) {
            return "Error: empty stack";
        }

        return String.valueOf(solver.peek());
    }

}