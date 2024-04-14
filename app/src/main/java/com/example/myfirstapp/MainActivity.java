package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Deklarerer variabler til at håndtere brugerinput og knapper
    private EditText textNum1, textNum2;
    private Button buttonAdd, buttonSubtract, buttonMultiply, buttonDivide, buttonNextPage;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiserer variablerne med deres tilsvarende views i layoutfilen
        textNum1 = findViewById(R.id.TextNum1);
        textNum2 = findViewById(R.id.TextNum2);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonNextPage = findViewById(R.id.buttonNextPage);
        textViewResult = findViewById(R.id.textViewResult);

        // Opsætter klikhændelser for hver af de matematiske operationer
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult('+');
            }
        });

        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult('-');
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult('*');
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult('/');
            }
        });

        // Åbner SecondActivity, når knappen "Næste side" trykkes
        buttonNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    // Metode til at beregne resultatet baseret på den valgte operation
    private void calculateResult(char operation) {
        double number1 = Double.parseDouble(textNum1.getText().toString());
        double number2 = Double.parseDouble(textNum2.getText().toString());

        double result;
        switch (operation) {
            case '+':
                result = number1 + number2;
                break;
            case '-':
                result = number1 - number2;
                break;
            case '*':
                result = number1 * number2;
                break;
            case '/':
                // Håndterer division med nul og viser en fejlmeddelelse
                if (number2 == 0) {
                    Toast.makeText(MainActivity.this, "Kan ikke dividere med nul", Toast.LENGTH_SHORT).show();
                    return;
                }
                result = number1 / number2;
                break;
            default:
                return;
        }

        // Formaterer resultatet og viser det i tekstfeltet
        String formattedResult = result % 1 == 0 ? String.valueOf((int) result) : String.valueOf(result);
        textViewResult.setText("Resultat: " + formattedResult);
    }
}
