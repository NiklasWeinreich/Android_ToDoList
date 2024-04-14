package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FifthActivity extends AppCompatActivity {

    // Deklaration af variabler til brugeroplysninger og UI-komponenter
    private EditText userIdEditText, passwordEditText;
    private Button loginButton;

    // Brugeroplysninger
    private static final String USERNAME = "Admin1";
    private static final String PASSWORD = "Passw0rd";

    private static final String USERNAME1 = "Admin2";
    private static final String PASSWORD1 = "Passw0rd1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        // Indstiller layoutet for aktiviteten
        setContentView(R.layout.activity_fifth);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userIdEditText = findViewById(R.id.userId);
        passwordEditText = findViewById(R.id.passwordId);
        loginButton = findViewById(R.id.buttonLoginId);

        //klik på login-knappen
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClicked();
            }
        });
    }

    // Metode til at håndtere klik på login-knappen
    private void onLoginClicked() {
        // Henter brugerinput fra tekstfelterne
        String userId = userIdEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Kontrollerer om brugeroplysningerne
        if ((userId.equals(USERNAME) && password.equals(PASSWORD)) || (userId.equals(USERNAME1) && password.equals(PASSWORD1)))  {
            // Viser en meddelelse om vellykket login
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            //Opretter en ny intent til at starte den næste aktivitet
            Intent intent = new Intent(FifthActivity.this, ThirdActivity.class);
            intent.putExtra("USERNAME" + "USERNAME1", userId);
            startActivity(intent);
            finish(); // Afslutter den nuværende aktivitet
        } else {
            //meddelelse om forkert brugernavn eller adgangskode
            Toast.makeText(this, "Wrong Password or username!", Toast.LENGTH_SHORT).show();
        }
    }
}
