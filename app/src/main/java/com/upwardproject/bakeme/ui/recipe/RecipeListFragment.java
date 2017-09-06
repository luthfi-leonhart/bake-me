package com.upwardproject.bakeme.ui.recipe;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.model.Recipe;
import com.upwardproject.bakeme.ui.BaseActivity;
import com.upwardproject.bakeme.ui.BaseListFragment;
import com.upwardproject.bakeme.ui.ItemClickListener;
import com.upwardproject.bakeme.ui.recipe_step.RecipeStepListActivity;
import com.upwardproject.bakeme.ui.widget.ItemOffsetDecoration;
import com.upwardproject.bakeme.util.network.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeListFragment extends BaseListFragment implements RecipeContract.RecipeListView,
        ItemClickListener {

    private static final String PARAM_LIST = "movie_list";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;

    private ArrayList<Recipe> mItemList;
    private RecipeListPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new RecipeListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        View emptyView = view.findViewById(R.id.empty_view);
        initEmptyView(emptyView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvList.setHasFixedSize(true);
        rvList.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.item_offset));

        ((BaseActivity) getActivity()).setToolbar(toolbar, R.string.app_name);

        if (savedInstanceState != null) {
            mItemList = savedInstanceState.getParcelableArrayList(PARAM_LIST);
            bindData();
        }

        if (mItemList != null) bindData();
        else loadData();
    }

    @Override
    public void loadData() {
        if (!NetworkUtil.isNetworkConnected(getContext())) {
            showConnectionError();
            return;
        }

        mPresenter.loadRecipeList();
    }

    @Override
    public void onRecipeListLoaded(List<Recipe> recipes) {
        if (recipes == null) return;

        if (pageToLoad == 1 || adapter == null) {
            mItemList = new ArrayList<>(recipes);
            bindData();
            return;
        }

        mItemList.addAll(recipes);
        adapter.notifyDataSetChanged();
    }

    private void bindData() {
        adapter = new RecipeListAdapter(mItemList, this);
        rvList.setAdapter(adapter);
        rvList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClicked(View view, Object data, int position) {
        Recipe recipe = (Recipe) data;
        startActivity(RecipeStepListActivity.newInstance(getContext(), recipe));
    }

    @Override
    protected boolean endlessLoaderEnabled() {
        return false;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return new LinearLayoutManager(getContext());
        }

        return new GridLayoutManager(getContext(), 3);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PARAM_LIST, mItemList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
