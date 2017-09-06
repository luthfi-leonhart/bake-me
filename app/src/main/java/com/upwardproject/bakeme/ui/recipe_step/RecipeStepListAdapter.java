package com.upwardproject.bakeme.ui.recipe_step;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.model.RecipeStep;
import com.upwardproject.bakeme.ui.BaseListAdapter;
import com.upwardproject.bakeme.ui.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dark on 04/03/2017.
 */

class RecipeStepListAdapter extends BaseListAdapter<RecipeStep> {
    private List<RecipeStep> mItemList;

    RecipeStepListAdapter(List<RecipeStep> itemList, ItemClickListener listener) {
        mItemList = itemList;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_step, parent, false);
        return new StepViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StepViewHolder viewHolder = (StepViewHolder) holder;
        viewHolder.bindData(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.index_tv)
        TextView tvIndex;
        @BindView(R.id.step_description_tv)
        TextView tvName;

        private ItemClickListener mListener;

        private RecipeStep data;

        StepViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mListener = listener;
        }

        void bindData(RecipeStep data) {
            this.data = data;

            tvIndex.setText(String.valueOf(getAdapterPosition() + 1));
            tvName.setText(data.getShortDescription());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClicked(view, data, getAdapterPosition());
        }
    }
}
