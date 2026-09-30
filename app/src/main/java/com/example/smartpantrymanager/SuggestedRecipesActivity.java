package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewRecipes;
    private TextView tvNoRecipes;

    private ArrayList<Recipe> suggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        databaseHelper = new DatabaseHelper(this);

        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        tvNoRecipes = findViewById(R.id.tvNoRecipes);

        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        suggestedRecipes = new ArrayList<>();

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        suggestedRecipes.clear();

        Cursor cursor = databaseHelper.getSuggestedRecipes();

        while (cursor.moveToNext()) {

            int recipeId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.RECIPE_ID
                    )
            );

            String recipeName = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.RECIPE_NAME
                    )
            );

            String method = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.RECIPE_METHOD
                    )
            );

            suggestedRecipes.add(
                    new Recipe(
                            recipeId,
                            recipeName,
                            method
                    )
            );
        }

        cursor.close();

        displayRecipes();
    }

    private void displayRecipes() {

        if (suggestedRecipes.isEmpty()) {

            recyclerViewRecipes.setVisibility(
                    RecyclerView.GONE
            );

            tvNoRecipes.setVisibility(
                    TextView.VISIBLE
            );

            return;
        }

        recyclerViewRecipes.setVisibility(
                RecyclerView.VISIBLE
        );

        tvNoRecipes.setVisibility(
                TextView.GONE
        );

        RecipeAdapter adapter =
                new RecipeAdapter(
                        this,
                        suggestedRecipes
                );

        recyclerViewRecipes.setAdapter(adapter);
    }
}