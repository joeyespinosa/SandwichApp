package com.udacity.sandwichclub.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ItemSandwichBinding;
import com.udacity.sandwichclub.model.Sandwich;

import java.util.List;

public class SandwichAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Sandwich> mSandwichList;
    private SandwichListViewModel mViewModel;

    public SandwichAdapter(Context context, List<Sandwich> sandwiches, SandwichListViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;
        updateData(sandwiches);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSandwichBinding binding =
                ItemSandwichBinding.inflate(layoutInflater, parent, false);
        return new SandwichViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Sandwich sandwich = mSandwichList.get(position);
        SandwichViewHolder sandwichViewHolder = (SandwichViewHolder) holder;

        Picasso.get()
                .load(sandwich.getImage())
                .into(sandwichViewHolder.binding.imageviewSandwich);

        sandwichViewHolder.binding.textviewName.setText(sandwich.getMainName());
        sandwichViewHolder.binding.textviewDescription.setText(sandwich.getDescription());
        sandwichViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Log.d("Position clicked: %s", String.valueOf(position));
                mViewModel.getOpenSandwichEvent().setValue(position);
            }
        });

        sandwichViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mSandwichList != null ? mSandwichList.size() : 0;
    }

    public void updateData(List<Sandwich> sandwiches) {
        mSandwichList = sandwiches;
        notifyDataSetChanged();
    }

    public class SandwichViewHolder extends RecyclerView.ViewHolder {

        private final ItemSandwichBinding binding;

        public SandwichViewHolder(final ItemSandwichBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
