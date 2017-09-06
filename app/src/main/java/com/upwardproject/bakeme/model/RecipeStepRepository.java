package com.upwardproject.bakeme.model;

import com.upwardproject.bakeme.constant.ServerApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dark on 02/09/2017.
 */

public class RecipeStepRepository {
    public static final String PARAM_OBJECT = "step",
            PARAM_LIST = "steps",
            PARAM_SHORT_DESCRIPTION = "shortDescription",
            PARAM_DESCRIPTION = "description",
            PARAM_VIDEO = "videoURL",
            PARAM_THUMBNAIL = "thumbnailURL";

    public static List<RecipeStep> getListFromJson(JSONArray jsonArray) {
        List<RecipeStep> itemList = new ArrayList<>();

        if (jsonArray == null) {
            return itemList;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            itemList.add(getDataFromJson(jsonObject));
        }

        return itemList;
    }

    public static RecipeStep getDataFromJson(JSONObject jsonObject) {
        if (jsonObject == null) return null;

        return new RecipeStep(jsonObject.optInt(ServerApi.PARAM_ID))
                .setShortDescription(jsonObject.optString(PARAM_SHORT_DESCRIPTION))
                .setDescription(jsonObject.optString(PARAM_DESCRIPTION))
                .setVideoUrl(jsonObject.optString(PARAM_VIDEO))
                .setThumbnailUrl(jsonObject.optString(PARAM_THUMBNAIL));
    }

}
