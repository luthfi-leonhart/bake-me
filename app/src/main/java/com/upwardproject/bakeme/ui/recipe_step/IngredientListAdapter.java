package com.upwardproject.bakeme.ui.recipe_step;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.model.Ingredient;
import com.upwardproject.bakeme.ui.BaseListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dark on 04/03/2017.
 */

class IngredientListAdapter extends BaseListAdapter<Ingredient> {
    private List<Ingredient> mItemList;

    IngredientListAdapter(List<Ingredient> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IngredientViewHolder viewHolder = (IngredientViewHolder) holder;
        viewHolder.bindData(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name_tv)
        TextView tvName;
        @BindView(R.id.ingredient_quantity_tv)
        TextView tvQuantity;

        IngredientViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bindData(Ingredient data) {
            tvName.setText(data.getName());
            tvQuantity.setText(data.getQuantity() + " " + data.getMeasure());
        }
    }
}
