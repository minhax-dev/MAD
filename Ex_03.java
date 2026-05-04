package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Event Listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString();

                // Intent to move to SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                // Passing data
                intent.putExtra("USERNAME", name);

                startActivity(intent);
            }
        });
    }
}