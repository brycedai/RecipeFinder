package com.brycedai.recipefinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;
import java.util.Set;

public class IngredientAdapter extends ArrayAdapter {

    Context context;
    List<String> ingredientList;

    public IngredientAdapter(Context context, List<String> list) {
        super(context, 0, list);
        this.context = context;
        this.ingredientList = list;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.ingredient_layout, parent, false);
        }
        final String currIngredient = ingredientList.get(pos);


        //Adds the ingredient
        TextView title = listItem.findViewById(R.id.ingredient_list_textView);
        title.setText(currIngredient);

        Button deleteBtn = listItem.findViewById(R.id.ingredient_list_deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                ingredientList.remove(currIngredient);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                Set<String> tempSet = sp.getStringSet("INGREDIENTS", null);
                if (tempSet != null) {
                    tempSet.remove(currIngredient);
                }
                notifyDataSetChanged();
            }
        });

        return listItem;
    }
}
