package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditingIngredientActivity
        extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;

    private Button btnUpdateIngredient;

    private DatabaseHelper databaseHelper;

    private int ingredientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_edit_ingredient
        );

        databaseHelper =
                new DatabaseHelper(this);

        etIngredientName =
                findViewById(
                        R.id.etIngredientName
                );

        etQuantity =
                findViewById(
                        R.id.etQuantity
                );

        etUnit =
                findViewById(
                        R.id.etUnit
                );

        etExpiryDate =
                findViewById(
                        R.id.etExpiryDate
                );

        btnUpdateIngredient =
                findViewById(
                        R.id.btnUpdateIngredient
                );

        // Get ID
        ingredientId =
                getIntent().getIntExtra(
                        "id",
                        -1
                );

        // Get existing information
        String name =
                getIntent().getStringExtra(
                        "name"
                );

        double quantity =
                getIntent().getDoubleExtra(
                        "quantity",
                        0
                );

        String unit =
                getIntent().getStringExtra(
                        "unit"
                );

        String expiryDate =
                getIntent().getStringExtra(
                        "expiryDate"
                );

        // Display existing information
        etIngredientName.setText(name);

        etQuantity.setText(
                String.valueOf(quantity)
        );

        etUnit.setText(unit);

        if (expiryDate != null) {

            etExpiryDate.setText(
                    expiryDate
            );
        }

        // Update button
        btnUpdateIngredient.setOnClickListener(
                v -> updateIngredient()
        );
    }

    private void updateIngredient() {

        String name =
                etIngredientName
                        .getText()
                        .toString()
                        .trim();

        String quantityText =
                etQuantity
                        .getText()
                        .toString()
                        .trim();

        String unit =
                etUnit
                        .getText()
                        .toString()
                        .trim();

        String expiryDate =
                etExpiryDate
                        .getText()
                        .toString()
                        .trim();

        // Validate name
        if (name.isEmpty()) {

            etIngredientName.setError(
                    "Enter ingredient name"
            );

            etIngredientName.requestFocus();

            return;
        }

        // Validate quantity
        if (quantityText.isEmpty()) {

            etQuantity.setError(
                    "Enter quantity"
            );

            etQuantity.requestFocus();

            return;
        }

        // Validate unit
        if (unit.isEmpty()) {

            etUnit.setError(
                    "Enter unit"
            );

            etUnit.requestFocus();

            return;
        }

        double quantity;

        try {

            quantity =
                    Double.parseDouble(
                            quantityText
                    );

        } catch (NumberFormatException e) {

            etQuantity.setError(
                    "Enter a valid number"
            );

            etQuantity.requestFocus();

            return;
        }

        // Update database
        boolean updated =
                databaseHelper.updatePantryItem(
                        ingredientId,
                        name,
                        quantity,
                        unit,
                        expiryDate
                );

        if (updated) {

            Toast.makeText(
                    this,
                    "Ingredient updated successfully!",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to update ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}