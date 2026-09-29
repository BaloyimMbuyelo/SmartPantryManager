package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewRecipes;
    private TextView tvNoRecipes;

    private ArrayList<Recipe> suggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

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

        Set<String> pantryIngredients = getPantryIngredients();

        Cursor recipeCursor = databaseHelper.getReadableDatabase().query(
                DatabaseHelper.TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.RECIPE_NAME + " ASC"
        );

        while (recipeCursor.moveToNext()) {

            int recipeId = recipeCursor.getInt(
                    recipeCursor.getColumnIndexOrThrow(
                            DatabaseHelper.RECIPE_ID
                    )
            );

            String recipeName = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow(
                            DatabaseHelper.RECIPE_NAME
                    )
            );

            String method = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow(
                            DatabaseHelper.RECIPE_METHOD
                    )
            );

            if (canMakeRecipe(recipeId, pantryIngredients)) {

                suggestedRecipes.add(
                        new Recipe(
                                recipeId,
                                recipeName,
                                method
                        )
                );
            }
        }

        recipeCursor.close();

        displayRecipes();
    }

    private Set<String> getPantryIngredients() {

        Set<String> pantryIngredients = new HashSet<>();

        Cursor cursor = databaseHelper.getAllPantryItems();

        while (cursor.moveToNext()) {

            String ingredientName = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.COL_NAME
                    )
            );

            pantryIngredients.add(
                    ingredientName.trim().toLowerCase()
            );
        }

        cursor.close();

        return pantryIngredients;
    }

    private boolean canMakeRecipe(
            int recipeId,
            Set<String> pantryIngredients
    ) {

        Cursor ingredientCursor = databaseHelper.getReadableDatabase().query(
                DatabaseHelper.TABLE_RECIPE_INGREDIENTS,
                null,
                DatabaseHelper.INGREDIENT_RECIPE_ID + "=?",
                new String[]{
                        String.valueOf(recipeId)
                },
                null,
                null,
                null
        );

        boolean canMake = true;

        while (ingredientCursor.moveToNext()) {

            String requiredIngredient =
                    ingredientCursor.getString(
                            ingredientCursor.getColumnIndexOrThrow(
                                    DatabaseHelper.INGREDIENT_NAME
                            )
                    );

            requiredIngredient =
                    requiredIngredient.trim().toLowerCase();

            if (!pantryIngredients.contains(requiredIngredient)) {
                canMake = false;
                break;
            }
        }

        ingredientCursor.close();

        return canMake;
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