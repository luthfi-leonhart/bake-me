package com.upwardproject.bakeme.ui.app_widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.upwardproject.bakeme.R;

/**
 * Created by Dark on 20/09/2017.
 */

public class IngredientWidgetService extends IntentService {
    public IngredientWidgetService() {
        super("IngredientListService");
    }

    public static void startActionUpdateRecipeWidget(Context context) {
        Intent intent = new Intent(context, IngredientWidgetService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list);

        IngredientWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds);
    }
}
