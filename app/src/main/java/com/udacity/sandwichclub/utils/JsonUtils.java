package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    static final String NAME = "name";
    static final String MAIN_NAME  = "mainName";
    static final String ALSO_KNOWN_AS = "alsoKnownAs";
    static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    static final String DESCRIPTION = "description";
    static final String IMAGE = "image";
    static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //Create Sandwich JSON object
        JSONObject sandwichObject = new JSONObject(json);

        JSONObject sandwichName = sandwichObject.getJSONObject(NAME);
        //Get  main name
        String mainName = sandwichName.getString(MAIN_NAME);

        //Get the list of also known as
        JSONArray alsoKnownAsJsonArray = sandwichName.getJSONArray(ALSO_KNOWN_AS);
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
            alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
        }

        //Get the place of origin
        String placeOfOrigin = sandwichObject.getString(PLACE_OF_ORIGIN);

        //Get the description
        String description = sandwichObject.getString(DESCRIPTION);

        //Get the image URL
        String image = sandwichObject.getString(IMAGE);

        //Get the list of ingredients
        JSONArray ingredientsJsonArray = sandwichObject.getJSONArray(INGREDIENTS);
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            ingredients.add(ingredientsJsonArray.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
