package com.udacity.sandwichclub.ui.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.udacity.sandwichclub.databinding.ItemAlsoKnownAsBinding;
import com.udacity.sandwichclub.databinding.ItemIngredientBinding;

import java.util.List;

public class AlsoKnownAsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<String> mAlsoKnownAsList;

    public AlsoKnownAsAdapter(Context context, List<String> items) {
        mContext = context;
        updateData(items);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAlsoKnownAsBinding binding =
                ItemAlsoKnownAsBinding.inflate(layoutInflater, parent, false);
        return new AlsoKnownAsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final String ingredientText = mAlsoKnownAsList.get(position);
        AlsoKnownAsViewHolder itemViewHolder = (AlsoKnownAsViewHolder) holder;
        itemViewHolder.binding.textviewNameAlsoKnownAs.setText(ingredientText);
        itemViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mAlsoKnownAsList != null ? mAlsoKnownAsList.size() : 0;
    }

    public void updateData(List<String> ingredients) {
        mAlsoKnownAsList = ingredients;
        notifyDataSetChanged();
    }

    public class AlsoKnownAsViewHolder extends RecyclerView.ViewHolder {

        private final ItemAlsoKnownAsBinding binding;

        public AlsoKnownAsViewHolder(final ItemAlsoKnownAsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
