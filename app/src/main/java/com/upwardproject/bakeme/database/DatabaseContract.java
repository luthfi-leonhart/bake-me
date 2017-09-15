package com.upwardproject.bakeme.database;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DataType.Type;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.PrimaryKeyConstraint;
import net.simonvt.schematic.annotation.References;

import static net.simonvt.schematic.annotation.ConflictResolutionType.REPLACE;

/**
 * Created by Dark on 10/09/2017.
 */

public class DatabaseContract {

    @PrimaryKeyConstraint(
            name = "PRIMARY_RECIPE",
            columns = RecipeEntry.ID,
            onConflict = REPLACE
    )
    public interface RecipeEntry {
        @DataType(Type.INTEGER)
        String ID = "id";

        @DataType(Type.TEXT)
        @NotNull
        String NAME = "name";

        @DataType(Type.INTEGER)
        String SERVINGS = "servings";

        @DataType(Type.TEXT)
        String IMAGE_URL = "image_url";
    }

    @PrimaryKeyConstraint(
            name = "PRIMARY_INGREDIENT",
            columns = IngredientEntry.INGREDIENT,
            onConflict = REPLACE
    )
    public interface IngredientEntry {
        @DataType(Type.TEXT)
        String INGREDIENT = "ingredient";

        @DataType(Type.TEXT)
        String MEASURE = "measure";

        @DataType(Type.INTEGER)
        String QUANTITY = "quantity";

        @DataType(Type.INTEGER)
        @References(table = DatabaseHelper.Tables.RECIPE, column = RecipeEntry.ID)
        String RECIPE_ID = "recipe_id";
    }

    @PrimaryKeyConstraint(
            name = "PRIMARY_STEP",
            columns = StepEntry.ID,
            onConflict = REPLACE
    )
    public interface StepEntry {
        @DataType(Type.INTEGER)
        String ID = "id";

        @DataType(Type.TEXT)
        String SHORT_DESCRIPTION = "short_description";

        @DataType(Type.TEXT)
        String DESCRIPTION = "description";

        @DataType(Type.TEXT)
        String THUMBNAIL_URL = "thumbnail_url";

        @DataType(Type.TEXT)
        String VIDEO_URL = "video_url";
    }
}
