package com.udacity.sandwichclub.ui.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ItemIngredientBinding;
import com.udacity.sandwichclub.databinding.ItemSandwichBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.ui.list.SandwichListViewModel;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<String> mIngredientList;

    public IngredientAdapter(Context context, List<String> ingredients ) {
        mContext = context;
        updateData(ingredients);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemIngredientBinding binding =
                ItemIngredientBinding.inflate(layoutInflater, parent, false);
        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final String ingredientText = mIngredientList.get(position);
        IngredientViewHolder itemViewHolder = (IngredientViewHolder) holder;
        itemViewHolder.binding.textviewNameIngredient.setText(ingredientText);
        itemViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mIngredientList != null ? mIngredientList.size() : 0;
    }

    public void updateData(List<String> ingredients) {
        mIngredientList = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final ItemIngredientBinding binding;

        public IngredientViewHolder(final ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
