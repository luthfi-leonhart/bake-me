package com.upwardproject.bakeme.ui.recipe;

import com.upwardproject.bakeme.model.Recipe;
import com.upwardproject.bakeme.model.RecipeRepository;
import com.upwardproject.bakeme.util.network.RemoteCallback;

import java.util.List;

/**
 * Created by Dark on 03/09/2017.
 */

class RecipeListPresenter implements RecipeContract.RecipeListAction {

    private RecipeContract.RecipeListView view;

    RecipeListPresenter(RecipeContract.RecipeListView view) {
        this.view = view;
    }

    @Override
    public void loadRecipeList() {
        view.setProgressIndicator(true);
        RecipeRepository.list(new RemoteCallback.Load<List<Recipe>>() {
            @Override
            public void onDataLoaded(List<Recipe> data) {
                view.onRecipeListLoaded(data);
                view.setProgressIndicator(false);
            }

            @Override
            public void onFailed(String message) {
                view.showError(message);
                view.setProgressIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String message) {
                view.showEmpty(message);
                view.setProgressIndicator(false);
            }
        });
    }
}
