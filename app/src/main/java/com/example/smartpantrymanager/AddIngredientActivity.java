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

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_ingredient);

        databaseHelper = new DatabaseHelper(this);

        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);

        btnSaveIngredient.setOnClickListener(v -> {

            String ingredientName =
                    etIngredientName.getText().toString().trim();

            String quantityText =
                    etQuantity.getText().toString().trim();

            String unit =
                    etUnit.getText().toString().trim();

            String expiryDate =
                    etExpiryDate.getText().toString().trim();

            if (ingredientName.isEmpty()) {

                Toast.makeText(
                        AddIngredientActivity.this,
                        "Please enter an ingredient name",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (quantityText.isEmpty()) {

                Toast.makeText(
                        AddIngredientActivity.this,
                        "Please enter a quantity",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (unit.isEmpty()) {

                Toast.makeText(
                        AddIngredientActivity.this,
                        "Please enter a unit",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            double quantity;

            try {

                quantity = Double.parseDouble(quantityText);

            } catch (NumberFormatException e) {

                Toast.makeText(
                        AddIngredientActivity.this,
                        "Please enter a valid quantity",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            long result = databaseHelper.addPantryItem(
                    ingredientName,
                    quantity,
                    unit,
                    expiryDate
            );

            if (result != -1) {

                Toast.makeText(
                        AddIngredientActivity.this,
                        "Ingredient saved successfully!",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        AddIngredientActivity.this,
                        "Failed to save ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}