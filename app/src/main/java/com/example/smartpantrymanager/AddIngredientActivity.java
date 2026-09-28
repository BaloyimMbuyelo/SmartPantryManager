package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {

    EditText etIngredientName;
    EditText etQuantity;
    EditText etUnit;
    EditText etExpiryDate;
    Button btnSaveIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_ingredient);

        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);

        btnSaveIngredient.setOnClickListener(v -> {

            String ingredientName = etIngredientName.getText().toString().trim();

            if (ingredientName.isEmpty()) {
                Toast.makeText(
                        AddIngredientActivity.this,
                        "Please enter an ingredient name",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(
                        AddIngredientActivity.this,
                        "Ingredient saved successfully!",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            }
        });
    }
}