package com.upwardproject.bakeme.ui.recipe;

import com.upwardproject.bakeme.model.Recipe;
import com.upwardproject.bakeme.ui.BaseContract;
import com.upwardproject.bakeme.ui.SimpleIdlingResource;

import java.util.List;

/**
 * Created by Dark on 02/09/2017.
 */

public class RecipeContract {
    interface RecipeListView extends BaseContract.RemoteView {
        void onRecipeListLoaded(List<Recipe> recipes);
    }

    interface RecipeListAction {
        void loadRecipeList(SimpleIdlingResource idlingResource);
    }
}
