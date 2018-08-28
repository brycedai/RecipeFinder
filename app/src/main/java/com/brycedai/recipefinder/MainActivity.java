package com.brycedai.recipefinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Goes to the RecipeListActivity when the "Search Recipes" button is pressed
    public void showRecipes(View view) {
        Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
        startActivity(intent);
    }

    //Goes to the AddIngredientActivity when the "Add Ingredient" button is pressed
    public void addIngredients(View view) {
        Intent intent = new Intent(MainActivity.this, AddIngredientActivity.class);
        startActivity(intent);
    }
}
