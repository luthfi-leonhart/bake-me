package com.upwardproject.bakeme.ui.recipe;

import android.content.ContentResolver;

import com.upwardproject.bakeme.model.Recipe;
import com.upwardproject.bakeme.model.RecipeRepository;
import com.upwardproject.bakeme.ui.SimpleIdlingResource;
import com.upwardproject.bakeme.util.network.RemoteCallback;

import java.util.List;

/**
 * Created by Dark on 03/09/2017.
 */

class RecipeListPresenter implements RecipeContract.RecipeListAction {

    private RecipeContract.RecipeListView view;
    private ContentResolver resolver;

    RecipeListPresenter(RecipeContract.RecipeListView view, ContentResolver resolver) {
        this.view = view;
        this.resolver = resolver;
    }

    @Override
    public void loadRecipeList(final SimpleIdlingResource idlingResource) {
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        view.setProgressIndicator(true);
        RecipeRepository.list(new RemoteCallback.Load<List<Recipe>>() {
            @Override
            public void onDataLoaded(List<Recipe> data) {
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }

                RecipeRepository.saveToLocal(resolver, data);

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
