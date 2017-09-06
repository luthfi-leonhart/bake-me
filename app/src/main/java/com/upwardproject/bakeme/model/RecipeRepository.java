package com.upwardproject.bakeme.model;

import com.upwardproject.bakeme.constant.ServerApi;
import com.upwardproject.bakeme.util.network.JsonDataReceiver;
import com.upwardproject.bakeme.util.network.RemoteCallback;
import com.upwardproject.bakeme.util.network.ServerRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dark on 02/09/2017.
 */

public class RecipeRepository {
    public static final String PARAM_OBJECT = "recipe",
            PARAM_NAME = "name",
            PARAM_SERVINGS = "servings",
            PARAM_IMAGE = "image";

    public static void list(final RemoteCallback.Load<List<Recipe>> callback) {
        ServerRestClient.get(ServerApi.RECIPES_URL, null, new JsonDataReceiver() {
            @Override
            public void onLoadingSucceed(Object data) {
                JSONArray jsonArray = (JSONArray) data;
                List<Recipe> itemList = getListFromJson(jsonArray);

                if (itemList.size() > 0) callback.onDataLoaded(itemList);
                else onLoadingEmpty("No recipes available");
            }

            @Override
            public void onLoadingEmpty(String message) {
                callback.onDataNotAvailable(message);
            }

            @Override
            public void onLoadingFailed(String message) {
                callback.onFailed(message);
            }
        });
    }

    public static List<Recipe> getListFromJson(JSONArray jsonArray) {
        List<Recipe> itemList = new ArrayList<>();

        if (jsonArray == null) {
            return itemList;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            itemList.add(getDataFromJson(jsonObject));
        }

        return itemList;
    }

    public static Recipe getDataFromJson(JSONObject jsonObject) {
        if (jsonObject == null) return null;

        Recipe recipe = new Recipe(jsonObject.optInt(ServerApi.PARAM_ID), jsonObject.optString(PARAM_NAME))
                .setServings(jsonObject.optInt(PARAM_SERVINGS))
                .setImageUrl(jsonObject.optString(PARAM_IMAGE));

        JSONArray ingredientArrays = jsonObject.optJSONArray(IngredientRepository.PARAM_LIST);
        List<Ingredient> ingredients = IngredientRepository.getListFromJson(ingredientArrays);
        recipe.setIngredients(ingredients);

        JSONArray stepArrays = jsonObject.optJSONArray(RecipeStepRepository.PARAM_LIST);
        List<RecipeStep> steps = RecipeStepRepository.getListFromJson(stepArrays);
        recipe.setSteps(steps);

        return recipe;
    }

}
