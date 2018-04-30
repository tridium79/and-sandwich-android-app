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

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownAsTextView;
    private TextView mDescriptionTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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

    private void populateUI(Sandwich sandwich) {
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAsTextView.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        } else {
            mAlsoKnownAsTextView.setText("N/A");
        }

        mDescriptionTextView.setText((sandwich.getDescription() != null && sandwich.getDescription().length() > 0) ?
                sandwich.getDescription() : "N/A");

        if (!sandwich.getIngredients().isEmpty()) {
            for (String ingredient : sandwich.getIngredients()) {
                mIngredientsTextView.append(" - " + ingredient + "\n");
            }
        } else {
            mIngredientsTextView.setText("N/A");
        }

        mPlaceOfOriginTextView.setText((sandwich.getPlaceOfOrigin() != null && sandwich.getPlaceOfOrigin().length() > 0) ?
                sandwich.getPlaceOfOrigin() : "N/A");
    }
}
