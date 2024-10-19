package com.aadithyan.calculatorassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText number1, number2;
    TextView result;
    Button btnAdd, btnSubtract, btnMultiply, btnDivide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI elements
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        result = findViewById(R.id.result);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);

        // Set onClickListeners for each button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("+");
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("-");
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("*");
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate("/");
            }
        });
    }

    // Method to perform calculation
    private void calculate(String operation) {
        // Get input numbers as strings
        String num1String = number1.getText().toString();
        String num2String = number2.getText().toString();

        // Validate input
        if (num1String.isEmpty() || num2String.isEmpty()) {
            result.setText("Please enter both numbers");
            return;
        }

        // Convert strings to double
        double num1 = Double.parseDouble(num1String);
        double num2 = Double.parseDouble(num2String);
        double resultValue = 0;

        // Perform operation
        switch (operation) {
            case "+":
                resultValue = num1 + num2;
                break;
            case "-":
                resultValue = num1 - num2;
                break;
            case "*":
                resultValue = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    resultValue = num1 / num2;
                } else {
                    result.setText("Cannot divide by zero");
                    return;
                }
                break;
        }

        // Display result
        result.setText("Result: " + resultValue);
    }
}
