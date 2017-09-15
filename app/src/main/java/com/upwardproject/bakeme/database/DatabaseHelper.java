package com.upwardproject.bakeme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.IfNotExists;
import net.simonvt.schematic.annotation.OnConfigure;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Dark on 15/09/2017.
 */
@Database(version = DatabaseHelper.VERSION,
        packageName = "com.upwardproject.bakeme.provider")
public class DatabaseHelper {

    public static final int VERSION = 1;

    public static class Tables {
        @Table(DatabaseContract.RecipeEntry.class)
        @IfNotExists
        public static final String RECIPE = "recipe";

        @Table(DatabaseContract.IngredientEntry.class)
        @IfNotExists
        public static final String INGREDIENT = "ingredient";

        @Table(DatabaseContract.StepEntry.class)
        @IfNotExists
        public static final String RECIPE_STEP = "recipe_step";
    }

    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {
    }

    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
    }

    @OnConfigure
    public static void onConfigure(SQLiteDatabase db) {
    }

}
