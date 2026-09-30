# Smart Pantry Manager

## Application Description

Smart Pantry Manager is a Java Android application developed to help users reduce food waste by managing pantry ingredients and suggesting recipes that can be prepared using ingredients they already have.

The application uses a strict recipe-matching rule. A recipe is only suggested when the user has every required ingredient in the required quantity.

## Main Features

- Add pantry ingredients
- View pantry ingredients
- Edit pantry ingredients
- Delete pantry ingredients
- Store ingredient quantities and units
- Store optional expiry dates
- View suggested recipes
- Strict recipe matching
- View recipe ingredients and preparation methods
- Settings screen
- SQLite local data persistence

## Database

The application uses **SQLite with SQLiteOpenHelper** for local data persistence.

SQLite was selected because Smart Pantry Manager is designed as a single-user mobile application and does not require cloud synchronisation. SQLite provides reliable local storage on the Android device and allows pantry and recipe data to remain available after the application is closed and reopened.

The database contains three main tables:

- `pantry_items` - stores the user's pantry ingredients
- `recipes` - stores the seeded recipes
- `recipe_ingredients` - stores the ingredients required by each recipe

## CRUD Functionality

The pantry management functionality demonstrates full CRUD operations:

- **Create:** Add a new pantry ingredient.
- **Read:** View existing pantry ingredients in the pantry list.
- **Update:** Edit an existing pantry ingredient.
- **Delete:** Delete an existing pantry ingredient.

The pantry data is stored in SQLite and persists after the application is closed and reopened.

## Strict Recipe Matching

The Suggested Recipes feature checks every ingredient required by a recipe against the user's pantry.

A recipe is displayed only when:

1. Every required ingredient exists in the pantry.
2. The pantry contains at least the required quantity.

Recipes with missing ingredients or insufficient quantities are excluded from the Suggested Recipes list.

## Technologies Used

- Java
- Android Studio
- Android SDK
- SQLite
- SQLiteOpenHelper
- RecyclerView
- Android Activities
- XML layouts
- Git
- GitHub

## How to Run

1. Clone the Smart Pantry Manager repository from GitHub.
2. Open the project in Android Studio.
3. Allow Gradle to synchronise.
4. Start an Android Emulator or connect an Android device.
5. Run the application.
6. Add ingredients to the pantry.
7. Open Suggested Recipes to see recipes that can currently be prepared.

## Project Structure

Java source files are located under:

`app/src/main/java/com/example/smartpantrymanager`

XML layouts are located under:

`app/src/main/res/layout`

The SQLite database implementation is contained in:

`DatabaseHelper.java`

## Strict-Matching Algorithm

The application uses SQLite queries to determine which recipes can be prepared from the current pantry.

For each recipe, the system checks its required ingredients. An ingredient is considered available when the ingredient name matches a pantry item and the pantry quantity is greater than or equal to the required quantity.

The application uses a `NOT EXISTS` query to exclude any recipe that contains a required ingredient that is missing from the pantry or does not have enough quantity.

This ensures that recipes are only suggested when all required ingredients are available.

## CRUD Functionality

The application supports full CRUD functionality for pantry ingredients:

- Create: Add a new pantry ingredient.
- Read: View saved ingredients in the pantry list.
- Update: Edit an existing ingredient.
- Delete: Remove an ingredient from the pantry.

Pantry data is stored locally using SQLite through SQLiteOpenHelper, so the data persists when the application is closed and reopened.

## Database Choice

SQLite was selected because it provides local on-device persistence without requiring an internet connection or external server. The application uses SQLiteOpenHelper to create and manage the database.