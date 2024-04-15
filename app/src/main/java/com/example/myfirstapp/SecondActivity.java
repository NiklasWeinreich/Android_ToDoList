package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    // Sætter tidslængden for hvor længe splash screen skal vises i millisekunder
    private static final int SPLASH_DISPLAY_LENGTH = 3000; // 3000 ms = 3 sekunder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets.consumeSystemWindowInsets();
        });

        // Starter en ny Handler, som vil starte din MainActivity efter 3 sekunder
        new Handler().postDelayed(() -> {
            // Opretter en Intent, der vil starte din hovedaktivitet
            Intent mainIntent = new Intent(SecondActivity.this, FifthActivity.class);
            SecondActivity.this.startActivity(mainIntent);
            SecondActivity.this.finish(); // Lukker denne Activity, så brugeren ikke kan navigere tilbage til den
        }, SPLASH_DISPLAY_LENGTH);
    }
}
