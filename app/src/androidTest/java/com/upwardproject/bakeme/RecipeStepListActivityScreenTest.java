package com.upwardproject.bakeme;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.upwardproject.bakeme.model.Ingredient;
import com.upwardproject.bakeme.model.Recipe;
import com.upwardproject.bakeme.model.RecipeRepository;
import com.upwardproject.bakeme.model.RecipeStep;
import com.upwardproject.bakeme.ui.recipe_step.RecipeStepDetailActivity;
import com.upwardproject.bakeme.ui.recipe_step.RecipeStepListActivity;

import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dark on 21/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeStepListActivityScreenTest {
    @Rule
    public IntentsTestRule<RecipeStepListActivity> activityTestRule
            = new IntentsTestRule<>(RecipeStepListActivity.class, false, false);

    @Test
    public void clickStepItem_OpensDetailActivity() {
        Recipe recipe = dummyRecipe();
        Intent intent = new Intent();
        intent.putExtra(RecipeRepository.PARAM_OBJECT, recipe);
        activityTestRule.launchActivity(intent);

        Espresso.onView(ViewMatchers.withId(R.id.recipe_step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        Intents.intended(AllOf.allOf(
                IntentMatchers.hasComponent(RecipeStepDetailActivity.class.getName()),
                IntentMatchers.hasExtraWithKey(RecipeRepository.PARAM_OBJECT)
        ));
    }

    private Recipe dummyRecipe() {
        Recipe recipe = new Recipe(1, "Test cake")
                .setServings(2);

        List<Ingredient> ingredientList = Arrays.asList(
                new Ingredient("ingredient 1")
                        .setMeasure("cup")
                        .setQuantity(2),
                new Ingredient("ingredient 2")
                        .setMeasure("cup")
                        .setQuantity(2),
                new Ingredient("ingredient 3")
                        .setMeasure("cup")
                        .setQuantity(2)
        );
        recipe.setIngredients(ingredientList);

        List<RecipeStep> steps = Arrays.asList(
                new RecipeStep(1)
                        .setDescription("Long description 1")
                        .setShortDescription("Short description 1"),
                new RecipeStep(1)
                        .setDescription("Long description 2")
                        .setShortDescription("Short description 2")
        );
        recipe.setSteps(steps);

        return recipe;
    }
}
