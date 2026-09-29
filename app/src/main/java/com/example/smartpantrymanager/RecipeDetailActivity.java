package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private TextView tvRecipeName;
    private TextView tvRecipeIngredients;
    private TextView tvRecipeMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        databaseHelper = new DatabaseHelper(this);

        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvRecipeIngredients = findViewById(R.id.tvRecipeIngredients);
        tvRecipeMethod = findViewById(R.id.tvRecipeMethod);

        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        if (recipeId != -1) {
            loadRecipe(recipeId);
        }
    }

    private void loadRecipe(int recipeId) {

        SQLiteData recipeData = getRecipeData(recipeId);

        if (recipeData != null) {

            tvRecipeName.setText(recipeData.name);

            tvRecipeIngredients.setText(
                    "Ingredients\n\n" + recipeData.ingredients
            );

            tvRecipeMethod.setText(
                    "Preparation Method\n\n" + recipeData.method
            );
        }
    }

    private SQLiteData getRecipeData(int recipeId) {

        Cursor recipeCursor = databaseHelper.getRecipeById(recipeId);

        if (recipeCursor == null || !recipeCursor.moveToFirst()) {
            if (recipeCursor != null) {
                recipeCursor.close();
            }
            return null;
        }

        String name = recipeCursor.getString(
                recipeCursor.getColumnIndexOrThrow(DatabaseHelper.RECIPE_NAME)
        );

        String method = recipeCursor.getString(
                recipeCursor.getColumnIndexOrThrow(DatabaseHelper.RECIPE_METHOD)
        );

        recipeCursor.close();

        Cursor ingredientCursor =
                databaseHelper.getRecipeIngredients(recipeId);

        StringBuilder ingredients = new StringBuilder();

        if (ingredientCursor != null) {

            while (ingredientCursor.moveToNext()) {

                String ingredientName = ingredientCursor.getString(
                        ingredientCursor.getColumnIndexOrThrow(
                                DatabaseHelper.INGREDIENT_NAME
                        )
                );

                double quantity = ingredientCursor.getDouble(
                        ingredientCursor.getColumnIndexOrThrow(
                                DatabaseHelper.INGREDIENT_QUANTITY
                        )
                );

                String unit = ingredientCursor.getString(
                        ingredientCursor.getColumnIndexOrThrow(
                                DatabaseHelper.INGREDIENT_UNIT
                        )
                );

                ingredients.append("• ")
                        .append(ingredientName)
                        .append(" - ")
                        .append(quantity)
                        .append(" ")
                        .append(unit)
                        .append("\n");
            }

            ingredientCursor.close();
        }

        return new SQLiteData(
                name,
                ingredients.toString(),
                method
        );
    }

    private static class SQLiteData {

        String name;
        String ingredients;
        String method;

        SQLiteData(
                String name,
                String ingredients,
                String method
        ) {
            this.name = name;
            this.ingredients = ingredients;
            this.method = method;
        }
    }
}