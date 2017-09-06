package com.upwardproject.bakeme.ui.recipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.model.Recipe;
import com.upwardproject.bakeme.ui.BaseListAdapter;
import com.upwardproject.bakeme.ui.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dark on 04/03/2017.
 */

class RecipeListAdapter extends BaseListAdapter<Recipe> {
    private List<Recipe> mItemList;

    RecipeListAdapter(List<Recipe> itemList, ItemClickListener listener) {
        mItemList = itemList;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
        return new RecipeViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecipeViewHolder viewHolder = (RecipeViewHolder) holder;
        viewHolder.bindData(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_image_iv)
        ImageView ivImage;
        @BindView(R.id.recipe_name_tv)
        TextView tvName;
        @BindView(R.id.recipe_servings_tv)
        TextView tvServings;
        @BindView(R.id.recipe_total_ingredients_tv)
        TextView tvTotalIngredients;

        private ItemClickListener mListener;

        private Recipe data;

        RecipeViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            mListener = listener;
        }

        void bindData(Recipe data) {
            this.data = data;

            Glide.with(itemView.getContext())
                    .load(data.getImageUrl())
                    .placeholder(R.color.colorPrimaryLight)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivImage);

            tvName.setText(data.getName());

            String servings = String.format(
                    itemView.getContext().getString(R.string.recipe_servings_text_format),
                    data.getServings()
            );
            tvServings.setText(servings);

            String totalIngredients = String.format(
                    itemView.getContext().getString(R.string.recipe_ingredients_text_format),
                    data.getIngredients().size()
            );
            tvTotalIngredients.setText(totalIngredients);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClicked(v, data, getAdapterPosition());
        }
    }
}
