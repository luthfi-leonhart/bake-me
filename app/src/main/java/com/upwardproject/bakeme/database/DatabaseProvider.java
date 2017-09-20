package com.upwardproject.bakeme.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Dark on 15/09/2017.
 */

@ContentProvider(
        authority = DatabaseProvider.AUTHORITY,
        database = DatabaseHelper.class,
        packageName = "com.upwardproject.bakeme.provider"
)
public final class DatabaseProvider {
    public static final String AUTHORITY = "com.upwardproject.bakeme.provider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String RECIPES = "recipes";
        String INGREDIENTS = "ingredients";
        String FROM_RECIPE = "fromRecipe";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = DatabaseHelper.Tables.RECIPE)
    public static class Recipe {

        @ContentUri(
                path = Path.RECIPES,
                type = "vnd.android.cursor.dir/recipes",
                defaultSort = DatabaseContract.RecipeEntry.NAME + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.RECIPES);

        @InexactContentUri(
                path = Path.RECIPES + "/#",
                name = "RECIPE_ID",
                type = "vnd.android.cursor.item/recipe",
                whereColumn = DatabaseContract.RecipeEntry.ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.RECIPES, String.valueOf(id));
        }
    }

    @TableEndpoint(table = DatabaseHelper.Tables.INGREDIENT)
    public static class Ingredients {

        @ContentUri(
                path = Path.INGREDIENTS,
                type = "vnd.android.cursor.dir/ingredients")
        public static final Uri CONTENT_URI = buildUri(Path.INGREDIENTS);

        @InexactContentUri(
                name = "FROM_RECIPE",
                path = Path.INGREDIENTS + "/" + Path.FROM_RECIPE + "/#",
                type = "vnd.android.cursor.dir/recipes",
                whereColumn = DatabaseContract.IngredientEntry.RECIPE_ID,
                pathSegment = 2)
        public static Uri fromList(long listId) {
            return buildUri(Path.INGREDIENTS, Path.FROM_RECIPE, String.valueOf(listId));
        }
    }
}
