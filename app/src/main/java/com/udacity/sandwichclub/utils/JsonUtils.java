package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        JSONObject jsonSandwich = null;
        try {
            jsonSandwich = new JSONObject(json);

            JSONObject names = jsonSandwich.getJSONObject("name");
            if (names != null) {
                String mainName = names.getString("mainName");
                sandwich.setMainName(mainName);

                List<String> alsoKnownAs = new ArrayList<String>();
                JSONArray alsoKnownAsValues = names.getJSONArray("alsoKnownAs");
                for (int i = 0; i < alsoKnownAsValues.length(); i++) {
                    alsoKnownAs.add(alsoKnownAsValues.get(i).toString());
                }
                sandwich.setAlsoKnownAs(alsoKnownAs);
            }

            String placeOfOrigin = jsonSandwich.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            String description = jsonSandwich.getString("description");
            sandwich.setDescription(description);

            String image = jsonSandwich.getString("image");
            sandwich.setImage(image);

            List<String> ingredients = new ArrayList<String>();
            JSONArray ingredientValues = jsonSandwich.getJSONArray("ingredients");
            for (int i = 0; i < ingredientValues.length(); i++) {
                ingredients.add(ingredientValues.get(i).toString());
            }
            sandwich.setIngredients(ingredients);

            return sandwich;
        } catch (JSONException e) {
            Log.e(TAG, "parseSandwichJson: ", e);
        }

        return null;
    }
}
