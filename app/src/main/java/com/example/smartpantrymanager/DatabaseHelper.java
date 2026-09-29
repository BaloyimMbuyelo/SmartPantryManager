package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_PANTRY = "pantry_items";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY_DATE = "expiry_date";

    public static final String TABLE_RECIPES = "recipes";

    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_METHOD = "method";

    public static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    public static final String INGREDIENT_ID = "id";
    public static final String INGREDIENT_RECIPE_ID = "recipe_id";
    public static final String INGREDIENT_NAME = "ingredient_name";
    public static final String INGREDIENT_QUANTITY = "required_quantity";
    public static final String INGREDIENT_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createPantryTable(db);
        createRecipeTables(db);
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {
        createPantryTable(db);
        createRecipeTables(db);

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_RECIPES,
                null
        );

        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();

        if (count == 0) {
            seedRecipes(db);
        }
    }

    private void createPantryTable(SQLiteDatabase db) {

        String sql =
                "CREATE TABLE IF NOT EXISTS " + TABLE_PANTRY + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " TEXT NOT NULL, " +
                        COL_QUANTITY + " REAL NOT NULL, " +
                        COL_UNIT + " TEXT NOT NULL, " +
                        COL_EXPIRY_DATE + " TEXT" +
                        ")";

        db.execSQL(sql);
    }

    private void createRecipeTables(SQLiteDatabase db) {

        String recipeTable =
                "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" +
                        RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RECIPE_NAME + " TEXT NOT NULL, " +
                        RECIPE_METHOD + " TEXT NOT NULL" +
                        ")";

        db.execSQL(recipeTable);

        String ingredientTable =
                "CREATE TABLE IF NOT EXISTS " +
                        TABLE_RECIPE_INGREDIENTS + " (" +
                        INGREDIENT_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        INGREDIENT_RECIPE_ID +
                        " INTEGER NOT NULL, " +
                        INGREDIENT_NAME +
                        " TEXT NOT NULL, " +
                        INGREDIENT_QUANTITY +
                        " REAL NOT NULL, " +
                        INGREDIENT_UNIT +
                        " TEXT NOT NULL, " +
                        "FOREIGN KEY(" +
                        INGREDIENT_RECIPE_ID +
                        ") REFERENCES " +
                        TABLE_RECIPES +
                        "(" +
                        RECIPE_ID +
                        ")" +
                        ")";

        db.execSQL(ingredientTable);
    }

    private void seedRecipes(SQLiteDatabase db) {

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_RECIPES,
                null
        );

        int recipeCount = 0;

        if (cursor.moveToFirst()) {
            recipeCount = cursor.getInt(0);
        }

        cursor.close();

        if (recipeCount > 0) {
            return;
        }

        long recipeId;

        recipeId = addRecipe(
                db,
                "Spaghetti Bolognese",
                "Cook spaghetti. Fry onion and garlic. Add mince and cook until brown. Add tomato and simmer. Serve the sauce over spaghetti."
        );

        addRecipeIngredient(db, recipeId, "spaghetti", 200, "g");
        addRecipeIngredient(db, recipeId, "mince", 250, "g");
        addRecipeIngredient(db, recipeId, "tomato", 2, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "garlic", 2, "pcs");

        recipeId = addRecipe(
                db,
                "Chicken Stir Fry",
                "Cut chicken and vegetables into small pieces. Fry chicken until cooked. Add vegetables and stir fry. Season and serve."
        );

        addRecipeIngredient(db, recipeId, "chicken", 250, "g");
        addRecipeIngredient(db, recipeId, "carrot", 1, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "pepper", 1, "pcs");
        addRecipeIngredient(db, recipeId, "oil", 2, "tbsp");

        recipeId = addRecipe(
                db,
                "Pancakes",
                "Mix flour, milk, egg and sugar into a smooth batter. Heat a pan and add a little oil. Cook pancakes on both sides until golden."
        );

        addRecipeIngredient(db, recipeId, "flour", 200, "g");
        addRecipeIngredient(db, recipeId, "milk", 250, "ml");
        addRecipeIngredient(db, recipeId, "egg", 2, "pcs");
        addRecipeIngredient(db, recipeId, "sugar", 2, "tbsp");
        addRecipeIngredient(db, recipeId, "oil", 1, "tbsp");

        recipeId = addRecipe(
                db,
                "Vegetable Omelette",
                "Beat the eggs. Chop the vegetables. Fry vegetables lightly, add eggs and cook until set."
        );

        addRecipeIngredient(db, recipeId, "egg", 3, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "tomato", 1, "pcs");
        addRecipeIngredient(db, recipeId, "pepper", 1, "pcs");
        addRecipeIngredient(db, recipeId, "oil", 1, "tbsp");

        recipeId = addRecipe(
                db,
                "Chicken Curry",
                "Fry onion and garlic. Add curry powder and chicken. Cook until browned. Add tomato and simmer until the chicken is cooked."
        );

        addRecipeIngredient(db, recipeId, "chicken", 500, "g");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "garlic", 2, "pcs");
        addRecipeIngredient(db, recipeId, "tomato", 2, "pcs");
        addRecipeIngredient(db, recipeId, "curry powder", 2, "tbsp");

        recipeId = addRecipe(
                db,
                "Tomato Pasta",
                "Cook pasta. Fry onion and garlic. Add tomatoes and simmer. Mix the sauce with cooked pasta."
        );

        addRecipeIngredient(db, recipeId, "pasta", 200, "g");
        addRecipeIngredient(db, recipeId, "tomato", 3, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "garlic", 2, "pcs");
        addRecipeIngredient(db, recipeId, "oil", 2, "tbsp");

        recipeId = addRecipe(
                db,
                "Grilled Cheese Sandwich",
                "Place cheese between bread slices. Butter the outside and grill in a pan until golden and the cheese melts."
        );

        addRecipeIngredient(db, recipeId, "bread", 2, "slices");
        addRecipeIngredient(db, recipeId, "cheese", 50, "g");
        addRecipeIngredient(db, recipeId, "butter", 1, "tbsp");

        recipeId = addRecipe(
                db,
                "French Toast",
                "Beat eggs with milk and sugar. Dip bread into the mixture. Fry in butter until golden on both sides."
        );

        addRecipeIngredient(db, recipeId, "bread", 4, "slices");
        addRecipeIngredient(db, recipeId, "egg", 2, "pcs");
        addRecipeIngredient(db, recipeId, "milk", 100, "ml");
        addRecipeIngredient(db, recipeId, "sugar", 1, "tbsp");
        addRecipeIngredient(db, recipeId, "butter", 1, "tbsp");

        recipeId = addRecipe(
                db,
                "Mashed Potatoes",
                "Boil potatoes until soft. Drain and mash with butter and milk. Season to taste."
        );

        addRecipeIngredient(db, recipeId, "potato", 500, "g");
        addRecipeIngredient(db, recipeId, "butter", 2, "tbsp");
        addRecipeIngredient(db, recipeId, "milk", 100, "ml");

        recipeId = addRecipe(
                db,
                "Chicken Sandwich",
                "Cook the chicken. Slice it and place it between bread with tomato and lettuce."
        );

        addRecipeIngredient(db, recipeId, "chicken", 200, "g");
        addRecipeIngredient(db, recipeId, "bread", 4, "slices");
        addRecipeIngredient(db, recipeId, "tomato", 1, "pcs");
        addRecipeIngredient(db, recipeId, "lettuce", 50, "g");

        recipeId = addRecipe(
                db,
                "Beef Burger",
                "Shape mince into burger patties. Fry until cooked. Place on a bun with cheese, tomato and lettuce."
        );

        addRecipeIngredient(db, recipeId, "mince", 300, "g");
        addRecipeIngredient(db, recipeId, "bread", 2, "pcs");
        addRecipeIngredient(db, recipeId, "cheese", 50, "g");
        addRecipeIngredient(db, recipeId, "tomato", 1, "pcs");
        addRecipeIngredient(db, recipeId, "lettuce", 30, "g");

        recipeId = addRecipe(
                db,
                "Egg Fried Rice",
                "Cook rice. Fry onion and vegetables. Add beaten eggs and scramble. Add rice and stir fry together."
        );

        addRecipeIngredient(db, recipeId, "rice", 250, "g");
        addRecipeIngredient(db, recipeId, "egg", 2, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "carrot", 1, "pcs");
        addRecipeIngredient(db, recipeId, "oil", 2, "tbsp");

        recipeId = addRecipe(
                db,
                "Tuna Pasta",
                "Cook pasta. Mix tuna with tomato and onion. Add cooked pasta and stir together."
        );

        addRecipeIngredient(db, recipeId, "pasta", 200, "g");
        addRecipeIngredient(db, recipeId, "tuna", 1, "can");
        addRecipeIngredient(db, recipeId, "tomato", 2, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");

        recipeId = addRecipe(
                db,
                "Vegetable Rice",
                "Cook rice. Fry onion, carrot and pepper. Add cooked rice and stir fry until well combined."
        );

        addRecipeIngredient(db, recipeId, "rice", 250, "g");
        addRecipeIngredient(db, recipeId, "carrot", 1, "pcs");
        addRecipeIngredient(db, recipeId, "pepper", 1, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "oil", 2, "tbsp");

        recipeId = addRecipe(
                db,
                "Tomato Soup",
                "Fry onion and garlic. Add tomatoes and water. Simmer until soft, then blend until smooth."
        );

        addRecipeIngredient(db, recipeId, "tomato", 5, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "garlic", 2, "pcs");
        addRecipeIngredient(db, recipeId, "oil", 1, "tbsp");

        recipeId = addRecipe(
                db,
                "Chicken and Rice",
                "Cook the rice. Season and fry the chicken until fully cooked. Serve the chicken with rice."
        );

        addRecipeIngredient(db, recipeId, "chicken", 300, "g");
        addRecipeIngredient(db, recipeId, "rice", 250, "g");
        addRecipeIngredient(db, recipeId, "oil", 2, "tbsp");

        recipeId = addRecipe(
                db,
                "Egg Sandwich",
                "Boil the eggs and slice them. Place eggs between bread with tomato and lettuce."
        );

        addRecipeIngredient(db, recipeId, "egg", 2, "pcs");
        addRecipeIngredient(db, recipeId, "bread", 4, "slices");
        addRecipeIngredient(db, recipeId, "tomato", 1, "pcs");
        addRecipeIngredient(db, recipeId, "lettuce", 30, "g");

        recipeId = addRecipe(
                db,
                "Banana Pancakes",
                "Mash the banana. Mix with egg, flour and milk. Cook spoonfuls of batter in a lightly oiled pan."
        );

        addRecipeIngredient(db, recipeId, "banana", 2, "pcs");
        addRecipeIngredient(db, recipeId, "egg", 2, "pcs");
        addRecipeIngredient(db, recipeId, "flour", 150, "g");
        addRecipeIngredient(db, recipeId, "milk", 150, "ml");

        recipeId = addRecipe(
                db,
                "Beef Pasta",
                "Cook pasta. Fry mince with onion and garlic. Add tomato and simmer. Mix with pasta."
        );

        addRecipeIngredient(db, recipeId, "pasta", 200, "g");
        addRecipeIngredient(db, recipeId, "mince", 250, "g");
        addRecipeIngredient(db, recipeId, "tomato", 2, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "garlic", 2, "pcs");

        recipeId = addRecipe(
                db,
                "Potato Omelette",
                "Boil and slice potatoes. Beat eggs. Fry potatoes and add eggs. Cook until the eggs are set."
        );

        addRecipeIngredient(db, recipeId, "potato", 300, "g");
        addRecipeIngredient(db, recipeId, "egg", 3, "pcs");
        addRecipeIngredient(db, recipeId, "onion", 1, "pcs");
        addRecipeIngredient(db, recipeId, "oil", 1, "tbsp");
    }

    private long addRecipe(
            SQLiteDatabase db,
            String name,
            String method
    ) {

        ContentValues values = new ContentValues();

        values.put(RECIPE_NAME, name);
        values.put(RECIPE_METHOD, method);

        return db.insert(
                TABLE_RECIPES,
                null,
                values
        );
    }

    private long addRecipeIngredient(
            SQLiteDatabase db,
            long recipeId,
            String ingredientName,
            double quantity,
            String unit
    ) {

        ContentValues values = new ContentValues();

        values.put(INGREDIENT_RECIPE_ID, recipeId);
        values.put(INGREDIENT_NAME, ingredientName);
        values.put(INGREDIENT_QUANTITY, quantity);
        values.put(INGREDIENT_UNIT, unit);

        return db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
    }

    public long addPantryItem(
            String name,
            double quantity,
            String unit,
            String expiryDate
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_UNIT, unit);
        values.put(COL_EXPIRY_DATE, expiryDate);

        return db.insert(
                TABLE_PANTRY,
                null,
                values
        );
    }

    public Cursor getAllPantryItems() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                COL_NAME + " ASC"
        );
    }

    public boolean updatePantryItem(
            int id,
            String name,
            double quantity,
            String unit,
            String expiryDate
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_UNIT, unit);
        values.put(COL_EXPIRY_DATE, expiryDate);

        int result = db.update(
                TABLE_PANTRY,
                values,
                COL_ID + "=?",
                new String[]{
                        String.valueOf(id)
                }
        );

        return result > 0;
    }

    public boolean deletePantryItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_PANTRY,
                COL_ID + "=?",
                new String[]{
                        String.valueOf(id)
                }
        );

        return result > 0;
    }

    public Cursor getAllRecipes() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                RECIPE_NAME + " ASC"
        );
    }

    public Cursor getRecipeById(int recipeId) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_RECIPES,
                null,
                RECIPE_ID + "=?",
                new String[]{
                        String.valueOf(recipeId)
                },
                null,
                null,
                null
        );
    }

    public Cursor getRecipeIngredients(int recipeId) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_RECIPE_INGREDIENTS,
                null,
                INGREDIENT_RECIPE_ID + "=?",
                new String[]{
                        String.valueOf(recipeId)
                },
                null,
                null,
                INGREDIENT_ID + " ASC"
        );
    }

    public Cursor getSuggestedRecipes() {

        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT r." + RECIPE_ID + ", " +
                        "r." + RECIPE_NAME + ", " +
                        "r." + RECIPE_METHOD +
                        " FROM " + TABLE_RECIPES + " r " +
                        "WHERE NOT EXISTS (" +
                        "SELECT 1 FROM " +
                        TABLE_RECIPE_INGREDIENTS + " ri " +
                        "WHERE ri." + INGREDIENT_RECIPE_ID +
                        " = r." + RECIPE_ID +
                        " AND NOT EXISTS (" +
                        "SELECT 1 FROM " + TABLE_PANTRY + " p " +
                        "WHERE LOWER(TRIM(p." + COL_NAME + ")) = " +
                        "LOWER(TRIM(ri." + INGREDIENT_NAME + ")) " +
                        "AND p." + COL_QUANTITY +
                        " >= ri." + INGREDIENT_QUANTITY +
                        ")" +
                        ")" +
                        " ORDER BY r." + RECIPE_NAME + " ASC";

        return db.rawQuery(query, null);
    }

    public int getRecipeCount() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_RECIPES,
                null
        );

        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();

        return count;
    }
}