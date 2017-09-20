package com.upwardproject.bakeme.ui.app_widget;

import android.content.ContentResolver;
import android.database.Cursor;

import com.upwardproject.bakeme.constant.Settings;
import com.upwardproject.bakeme.database.DatabaseContract;
import com.upwardproject.bakeme.database.DatabaseProvider;
import com.upwardproject.bakeme.model.Recipe;

import net.simonvt.schematic.Cursors;

/**
 * Created by Dark on 19/09/2017.
 */

class IngredientWidgetPresenter {

    long getSelectedRecipeId() {
        return Settings.getInstance().getLong(Settings.Key.SELECTED_RECIPE_ID);
    }

    Recipe getRecipe(ContentResolver contentResolver, long recipeId) {
        Cursor cursor = contentResolver.query(
                DatabaseProvider.Recipe.withId(recipeId),
                null,
                null,
                null,
                null
        );

        if (cursor == null || cursor.getCount() == 0) return null;

        cursor.moveToFirst();

        Recipe recipe = new Recipe(
                Cursors.getInt(cursor, DatabaseContract.RecipeEntry.ID),
                Cursors.getString(cursor, DatabaseContract.RecipeEntry.NAME)
        )
                .setImageUrl(Cursors.getString(cursor, DatabaseContract.RecipeEntry.IMAGE_URL))
                .setServings(Cursors.getInt(cursor, DatabaseContract.RecipeEntry.SERVINGS));

        cursor.close();

        return recipe;
    }
}
