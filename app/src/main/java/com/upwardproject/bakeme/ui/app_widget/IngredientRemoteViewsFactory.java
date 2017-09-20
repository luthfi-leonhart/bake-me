package com.upwardproject.bakeme.ui.app_widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.constant.Settings;
import com.upwardproject.bakeme.database.DatabaseContract;
import com.upwardproject.bakeme.database.DatabaseProvider;

import net.simonvt.schematic.Cursors;

/**
 * Created by Dark on 19/09/2017.
 */

class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    public IngredientRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        long recipeId = Settings.getInstance().getLong(Settings.Key.SELECTED_RECIPE_ID);

        Uri ingredientsUri = DatabaseProvider.Ingredients.fromList(recipeId);
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                ingredientsUri,
                null,
                null,
                null,
                DatabaseContract.IngredientEntry.INGREDIENT
        );
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) mCursor.close();
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (mCursor == null || mCursor.getCount() == 0) {
            return null;
        }

        mCursor.moveToPosition(i);

        String name = Cursors.getString(mCursor, DatabaseContract.IngredientEntry.INGREDIENT);
        String measure = Cursors.getString(mCursor, DatabaseContract.IngredientEntry.MEASURE);
        int qty = Cursors.getInt(mCursor, DatabaseContract.IngredientEntry.QUANTITY);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item_ingredient);
        views.setTextViewText(R.id.ingredient_name_tv, name);
        views.setTextViewText(R.id.ingredient_quantity_tv, qty + measure);
        views.setTextColor(R.id.ingredient_quantity_tv, ContextCompat.getColor(mContext, android.R.color.darker_gray));

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
