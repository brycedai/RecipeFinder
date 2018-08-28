package com.brycedai.recipefinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

//A adapter that is used to show the recipes in a ListView
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private  List<Recipe> recipeList;

    public RecipeAdapter(Context context, List<Recipe> list) {
        super(context, 0, list);
        this.context = context;
        this.recipeList = list;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.content_recipe, parent, false);
        }
        Recipe currRecipe = recipeList.get(pos);

        //Adds a picture of the dish
        ImageView image = listItem.findViewById(R.id.recipe_imageView);
        Picasso.get()
                .load(currRecipe.getImageUrl())
                .into(image);

        //Adds the name of the dish
        TextView title = listItem.findViewById(R.id.recipe_title_textView);
        title.setText(currRecipe.getTitle());

        return listItem;
    }
}
