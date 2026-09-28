package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 1;

    // Pantry table
    public static final String TABLE_PANTRY = "pantry_items";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY_DATE = "expiry_date";

    // Recipes table
    public static final String TABLE_RECIPES = "recipes";

    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_METHOD = "method";

    // Recipe ingredients table
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

        // Pantry table
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_QUANTITY + " REAL NOT NULL, " +
                COL_UNIT + " TEXT NOT NULL, " +
                COL_EXPIRY_DATE + " TEXT" +
                ")";

        db.execSQL(createPantryTable);

        // Recipes table
        String createRecipesTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RECIPE_NAME + " TEXT NOT NULL, " +
                RECIPE_METHOD + " TEXT NOT NULL" +
                ")";

        db.execSQL(createRecipesTable);

        // Recipe ingredients table
        String createRecipeIngredientsTable =
                "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                        INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        INGREDIENT_RECIPE_ID + " INTEGER NOT NULL, " +
                        INGREDIENT_NAME + " TEXT NOT NULL, " +
                        INGREDIENT_QUANTITY + " REAL NOT NULL, " +
                        INGREDIENT_UNIT + " TEXT NOT NULL, " +
                        "FOREIGN KEY(" + INGREDIENT_RECIPE_ID +
                        ") REFERENCES " + TABLE_RECIPES +
                        "(" + RECIPE_ID + ")" +
                        ")";

        db.execSQL(createRecipeIngredientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);

        onCreate(db);
    }

    // =========================
    // PANTRY CRUD
    // =========================

    // CREATE
    public long addPantryItem(String name, double quantity,
                              String unit, String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_UNIT, unit);
        values.put(COL_EXPIRY_DATE, expiryDate);

        return db.insert(TABLE_PANTRY, null, values);
    }

    // READ
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

    // UPDATE
    public boolean updatePantryItem(int id, String name,
                                    double quantity,
                                    String unit,
                                    String expiryDate) {

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
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

    // DELETE
    public boolean deletePantryItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_PANTRY,
                COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }
}