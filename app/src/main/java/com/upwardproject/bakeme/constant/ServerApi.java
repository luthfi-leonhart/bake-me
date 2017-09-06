package com.upwardproject.bakeme.constant;

import android.net.Uri;

import com.upwardproject.bakeme.BuildConfig;

/**
 * Created by Dark on 02/09/2017.
 */

public class ServerApi {
    public static final String PARAM_ID = "id";

    public static final String RECIPES_URL = new Uri.Builder()
            .scheme("https")
            .authority(BuildConfig.AUTHORITY)
            .appendPath("topher")
            .appendPath("2017")
            .appendPath("May")
            .appendPath("59121517_baking")
            .appendPath("baking.json")
            .build()
            .toString();

}
