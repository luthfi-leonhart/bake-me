package com.upwardproject.bakeme.model;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.upwardproject.bakeme.database.DatabaseContract;
import com.upwardproject.bakeme.database.DatabaseProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dark on 02/09/2017.
 */

public class IngredientRepository {
    public static final String PARAM_LIST = "ingredients",
            PARAM_NAME = "ingredient",
            PARAM_QUANTITY = "quantity",
            PARAM_MEASURE = "measure";

    public static List<Ingredient> getListFromJson(JSONArray jsonArray) {
        List<Ingredient> itemList = new ArrayList<>();

        if (jsonArray == null) {
            return itemList;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            itemList.add(getDataFromJson(jsonObject));
        }

        return itemList;
    }

    public static Ingredient getDataFromJson(JSONObject jsonObject) {
        if (jsonObject == null) return null;

        return new Ingredient(jsonObject.optString(PARAM_NAME))
                .setQuantity(jsonObject.optInt(PARAM_QUANTITY))
                .setMeasure(jsonObject.optString(PARAM_MEASURE));
    }

    public static int saveToLocal(ContentResolver resolver, int recipeId, List<Ingredient> ingredients) {
        ContentValues[] values = new ContentValues[ingredients.size()];

        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            values[i] = getContentValues(recipeId, ingredient);
        }

        return resolver.bulkInsert(DatabaseProvider.Ingredients.CONTENT_URI, values);
    }

    private static ContentValues getContentValues(int recipeId, Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.IngredientEntry.INGREDIENT, ingredient.getName());
        values.put(DatabaseContract.IngredientEntry.MEASURE, ingredient.getMeasure());
        values.put(DatabaseContract.IngredientEntry.QUANTITY, ingredient.getQuantity());
        values.put(DatabaseContract.IngredientEntry.RECIPE_ID, recipeId);

        return values;
    }

}
