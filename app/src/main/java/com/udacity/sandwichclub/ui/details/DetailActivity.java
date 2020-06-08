package com.udacity.sandwichclub.ui.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.ui.list.SandwichAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding mBinding;
    private IngredientAdapter mIngredientAdapter;
    private AlsoKnownAsAdapter mAlsoKnownAsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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

        setupToolbar();

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication(), position);
        SandwichViewModel mViewModel = ViewModelProviders.of(this, factory).get(SandwichViewModel.class);

        mViewModel.getSandwich().observe(this, new Observer<Sandwich>() {
            @Override
            public void onChanged(@Nullable Sandwich sandwich) {
                if (sandwich != null) {
                    populateUI(sandwich);
                } else {
                    closeOnError();
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.get()
                .load(sandwich.getImage())
                .into(mBinding.imageviewRecipe);

        mBinding.textviewSandwichName.setText(sandwich.getMainName());

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.isEmpty()) {
            mBinding.textviewOrigin.setVisibility(View.GONE);
        } else {
            mBinding.textviewOrigin.setText(placeOfOrigin);
        }

        List<String> names = sandwich.getAlsoKnownAs();
        if (!names.isEmpty()) {
            setupAlsoKnownAsAdapter(names);
        } else {
            mBinding.recyclerviewAlsoKnownAs.setVisibility(View.GONE);
        }

        mBinding.textviewDescription.setText(sandwich.getDescription());

        setupListAdapter( sandwich.getIngredients());

        mBinding.executePendingBindings();
    }

    private void setupListAdapter(List<String> ingredients) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_ingredients);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mIngredientAdapter = new IngredientAdapter(DetailActivity.this, ingredients);

        recyclerView.setAdapter(mIngredientAdapter);
    }

    private void setupAlsoKnownAsAdapter(List<String> items) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_also_known_as);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mAlsoKnownAsAdapter = new AlsoKnownAsAdapter(DetailActivity.this, items);

        recyclerView.setAdapter(mAlsoKnownAsAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
