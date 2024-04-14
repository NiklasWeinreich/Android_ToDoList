package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sætter layoutet for aktiviteten
        setContentView(R.layout.activity_third);

        // Finder knapper og tekstfelt i layoutet
        Button buttonCalcu = findViewById(R.id.buttonCalcu);
        Button buttonToDo = findViewById(R.id.buttonTodo);
        TextView welcomeTextView = findViewById(R.id.welcomeTextViewId);

        // Henter brugernavnet fra Intent og viser en velkomstbesked
        String userName = getIntent().getStringExtra("USERNAME" + "USERNAME1" );
        welcomeTextView.setText("Welcome back! " + userName);

        // Opsætter klikhændelser for knapperne
        buttonCalcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starter MainActivity
                Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Lukker ThirdActivity når du navigerer tilbage til MainActivity
            }
        });

        buttonToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starter FourthActivity
                Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
                startActivity(intent);
                finish(); // Lukker ThirdActivity når du navigerer tilbage til MainActivity
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
