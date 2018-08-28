package com.brycedai.recipefinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class RecipeTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_text);
        int recipeId;
        if (getIntent().getExtras() != null) {
            recipeId = getIntent().getExtras().getInt("recipeId");

            String getUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + recipeId + "/information?includeNutrition=false";
            //Makes an API call using the recipeId to get the recipe instructions
            Ion.with(this)
                    .load(getUrl)
                    .setHeader("X-Mashape-Key", "wubjRKtnfgmsh1yZn1tTRwTAyct8p149FZNjsnhnPTzl79APkO")
                    .setHeader("Accept", "application/json")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            // do stuff with the result or error
                            if (e != null) {
                                e.printStackTrace();
                            } else if (result != null) {

                                TextView text = findViewById(R.id.recipe_steps_textView);
                                StringBuilder ingredients = new StringBuilder();
                                ingredients.append("Ingredients: \n\n");
                                JsonArray ingredientsArr = result.get("extendedIngredients").getAsJsonArray();
                                //Adds all the ingredients to the StringBuilder
                                for (JsonElement obj : ingredientsArr) {
                                    ingredients.append(obj.getAsJsonObject().get("originalString").getAsString());
                                    ingredients.append("\n");
                                }
                                ingredients.append("\n");
                                //Adds the instructions to the StringBuilder
                                ingredients.append(result.get("instructions").getAsString());
                                //Sets the TextViews text to show the ingredients and the steps
                                text.setText(ingredients.toString());
                            }
                        }
                    });
        }
    }
}
