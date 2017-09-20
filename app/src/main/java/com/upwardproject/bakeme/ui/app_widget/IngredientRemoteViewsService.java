package com.upwardproject.bakeme.ui.app_widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Dark on 19/09/2017.
 */

public class IngredientRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientRemoteViewsFactory(getApplicationContext());
    }
}
