package com.upwardproject.bakeme;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.upwardproject.bakeme.model.RecipeRepository;
import com.upwardproject.bakeme.ui.recipe.RecipeListActivity;
import com.upwardproject.bakeme.ui.recipe_step.RecipeStepListActivity;

import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Dark on 21/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityScreenTest {
    @Rule
    public IntentsTestRule<RecipeListActivity> activityTestRule
            = new IntentsTestRule<>(RecipeListActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = activityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickGridItem_OpensStepListActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        Intents.intended(AllOf.allOf(
                IntentMatchers.hasComponent(RecipeStepListActivity.class.getName()),
                IntentMatchers.hasExtraWithKey(RecipeRepository.PARAM_OBJECT)
        ));

        Espresso.onView(AllOf.allOf(
                Matchers.instanceOf(TextView.class),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.toolbar))))
                .check(ViewAssertions.matches(ViewMatchers.withText("Nutella Pie")));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
