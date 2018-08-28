package com.brycedai.recipefinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Set;

public class RecipeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        String ingredients = createIngredientString();
        if (ingredients != null) {
            //Makes an API call to find the recipes using the inputted ingredients
            Ion.with(this)
                    .load("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false" +
                            "&ingredients=" + ingredients + "&limitLicense=false&number=20&ranking=2")
                    .setHeader("X-Mashape-Key", BuildConfig.ApiKey)
                    .setHeader("Accept", "application/json")
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            // do stuff with the result or error
                            if (e != null) {
                                e.printStackTrace();
                            } else if (result != null){
                                Log.d("the result", result.toString());
                                fillListView(result);
                            }
                        }
                    });
        }

    }

    //Returns a properly formatted URL string for the ingredients query using all the inputted ingredients
    private String createIngredientString() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        StringBuilder sb = new StringBuilder();
        Set<String> ingredientSet = sp.getStringSet("INGREDIENTS", null);
        String result = null;
        if (ingredientSet != null) {
            for (String s : ingredientSet) {
                sb.append(s);
                sb.append(",");
            }
            try {
                result = URLEncoder.encode(sb.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //Fills a ListView with the found recipes
    private void fillListView(JsonArray result) {
        ListView lv = findViewById(R.id.recipeLV);
        final ArrayList<Recipe> recipeList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            JsonObject recipe = result.get(i).getAsJsonObject();
            Recipe r = new Recipe(recipe.get("id").getAsInt(), recipe.get("title").getAsString(), recipe.get("image").getAsString());
            recipeList.add(r);
        }
        RecipeAdapter mArrayAdapter = new RecipeAdapter(this, recipeList);
        lv.setAdapter(mArrayAdapter);

        //creates OnClickListener for each item in the ListView that when clicked go to a new activity with the recipe on it
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe r = recipeList.get(position);
                Intent intent = new Intent(RecipeListActivity.this, RecipeTextActivity.class);
                intent.putExtra("recipeId", r.getId());
                startActivity(intent);
            }
        });
    }

}
