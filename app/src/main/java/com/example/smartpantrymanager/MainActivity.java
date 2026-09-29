package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewPantry;
    private PantryAdapter pantryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);

        recyclerViewPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        Button btnAddIngredient = findViewById(R.id.btnAddIngredient);

        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddIngredientActivity.class
            );

            startActivity(intent);
        });

        Button btnSettings = findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });

        Button btnSuggestedRecipes =
                findViewById(R.id.btnSuggestedRecipes);

        btnSuggestedRecipes.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);
        });

        loadPantryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPantryItems();
    }

    public void loadPantryItems() {

        Cursor cursor = databaseHelper.getAllPantryItems();

        pantryAdapter = new PantryAdapter(this, cursor);

        recyclerViewPantry.setAdapter(pantryAdapter);
    }

    public void openEditIngredient(
            int id,
            String name,
            double quantity,
            String unit,
            String expiryDate
    ) {

        Intent intent = new Intent(
                MainActivity.this,
                EditingIngredientActivity.class
        );

        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("quantity", quantity);
        intent.putExtra("unit", unit);
        intent.putExtra("expiryDate", expiryDate);

        startActivity(intent);
    }
}