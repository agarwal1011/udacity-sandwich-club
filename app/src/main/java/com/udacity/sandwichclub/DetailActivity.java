package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String NEWLINE_CHARACTER = "\n";

    private TextView mAlsoKnownAsTv;
    private TextView mPlaceOfOriginTv;
    private TextView mIngredientsTv;
    private TextView mDescriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAsTv = findViewById(R.id.also_known_tv);
        mPlaceOfOriginTv = findViewById(R.id.detail_place_of_origin_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mDescriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //If the value is not present in object, set error message to the text view else show the data
    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs() == null || sandwich.getAlsoKnownAs().isEmpty()) {
            setErrorMessage(mAlsoKnownAsTv, getString(R.string.also_known_as_not_found));
        } else {
            StringBuilder alsoKnowsAsList = new StringBuilder();
            for (String alsoKnownAs : sandwich.getAlsoKnownAs()) {
                alsoKnowsAsList.append(alsoKnownAs + NEWLINE_CHARACTER);
            }
            mAlsoKnownAsTv.setText(alsoKnowsAsList);
        }

        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            setErrorMessage(mPlaceOfOriginTv, getString(R.string.place_of_origin_not_found));
        } else {
            mPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin() + NEWLINE_CHARACTER);
        }

        if (sandwich.getIngredients() == null || sandwich.getIngredients().isEmpty()) {
            setErrorMessage(mIngredientsTv, getString(R.string.ingredients_not_found));
        } else {
            StringBuilder ingredientsList = new StringBuilder();
            for (String ingredient : sandwich.getIngredients()) {
                ingredientsList.append(ingredient + NEWLINE_CHARACTER);
            }
            mIngredientsTv.setText(ingredientsList);
        }

        if (TextUtils.isEmpty(sandwich.getDescription())) {
            setErrorMessage(mDescriptionTv, getString(R.string.description_not_found));
        } else {
            mDescriptionTv.setText(sandwich.getDescription());
        }
    }

    //sets the data and color of the text view to accent color
    private void setErrorMessage(TextView textView, String errorText) {
        textView.setText(errorText);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
    }
}
