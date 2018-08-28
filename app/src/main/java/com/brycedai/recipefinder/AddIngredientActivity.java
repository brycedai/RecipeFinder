package com.brycedai.recipefinder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Set;
import java.util.TreeSet;

public class AddIngredientActivity extends AppCompatActivity {

    TreeSet<String> ingredientSet;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        FloatingActionButton fab = findViewById(R.id.fab);
        //The fab opens a popup to input the new ingredient
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflatePopup(view);
            }
        });
        showIngredients();

    }

    //Shows all the added ingredients in a textview in the activity
    private void showIngredients() {
        //gets any previously added ingredients
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> tempSet = sp.getStringSet("INGREDIENTS", null);

        if (tempSet != null) {
            ingredientSet = new TreeSet<>(tempSet);

            StringBuilder sb = new StringBuilder();
            for (String s : ingredientSet) {
                sb.append(s.trim());
                sb.append("\n");
            }
            TextView tv = findViewById(R.id.add_ingredient_textView);
            tv.setText(sb.toString());
        }
    }


    private void inflatePopup(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.add_ingredient_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Cancel button closes popup
        Button cancel = popupView.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener(){

            public void onClick(View popupView) {
                popupWindow.dismiss();
            }
        });

        //The add button adds the new ingredient to the set of all ingredients and commits in SharedPreferences
        Button addButton = popupView.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditText et = popupView.findViewById(R.id.add_ingredient_editText);
                String newIngredient = et.getText().toString().trim();
                if (ingredientSet == null) {
                    ingredientSet = new TreeSet<>();
                }
                ingredientSet.add(newIngredient);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(AddIngredientActivity.this);
                SharedPreferences.Editor editor = sp.edit();
                editor.putStringSet("INGREDIENTS", ingredientSet);
                editor.commit();
                popupWindow.dismiss();

                //shows the newly added ingredient
                showIngredients();
            }
        });
    }

}
