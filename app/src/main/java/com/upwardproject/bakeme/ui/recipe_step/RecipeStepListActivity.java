package com.upwardproject.bakeme.ui.recipe_step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.constant.Settings;
import com.upwardproject.bakeme.model.Ingredient;
import com.upwardproject.bakeme.model.Recipe;
import com.upwardproject.bakeme.model.RecipeRepository;
import com.upwardproject.bakeme.model.RecipeStep;
import com.upwardproject.bakeme.ui.BaseActivity;
import com.upwardproject.bakeme.ui.ItemClickListener;
import com.upwardproject.bakeme.ui.app_widget.IngredientWidgetService;
import com.upwardproject.bakeme.util.ActivityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeStepListActivity extends BaseActivity implements ItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ingredient_list)
    RecyclerView rvIngredients;
    @BindView(R.id.recipe_step_list)
    RecyclerView rvSteps;

    MenuItem itemWidget;

    private Recipe mRecipe;

    /*
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
     */
    private boolean mTwoPane;
    /*
     * Flag for ingredient list visibility.
     */
    private boolean mIsIngredientsShown;
    /*
         * Flag for ingredient list visibility.
         */
    private boolean mIsStepsShown;

    public static Intent newInstance(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeStepListActivity.class);
        intent.putExtra(RecipeRepository.PARAM_OBJECT, recipe);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(RecipeRepository.PARAM_OBJECT)) {
            mRecipe = getIntent().getParcelableExtra(RecipeRepository.PARAM_OBJECT);
        } else throw new RuntimeException("Cannot found Recipe data");

        setToolbar(toolbar, mRecipe.getName());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mIsIngredientsShown = rvIngredients.getVisibility() == View.VISIBLE;
        mIsStepsShown = rvSteps.getVisibility() == View.VISIBLE;

        setupIngredientList(rvIngredients, mRecipe.getIngredients());
        setupStepList(rvSteps, mRecipe.getSteps());
    }

    private void setupIngredientList(@NonNull RecyclerView recyclerView, List<Ingredient> ingredients) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(new IngredientListAdapter(ingredients));
    }

    @OnClick(R.id.ingredient_header_tv)
    public void toggleIngredientList(TextView tvHeader) {
        mIsIngredientsShown = !mIsIngredientsShown;

        int iconResId = mIsIngredientsShown ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down;
        tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconResId, 0);

        rvIngredients.setVisibility(mIsIngredientsShown ? View.VISIBLE : View.GONE);
    }

    private void setupStepList(@NonNull RecyclerView recyclerView, List<RecipeStep> steps) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(new RecipeStepListAdapter(steps, this));
    }

    @OnClick(R.id.step_header_tv)
    public void toggleStepList(TextView tvHeader) {
        mIsStepsShown = !mIsStepsShown;

        int iconResId = mIsStepsShown ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down;
        tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconResId, 0);

        rvSteps.setVisibility(mIsStepsShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClicked(View view, Object data, int position) {
        // On step item clicked
        if (mTwoPane) {
            RecipeStep step = (RecipeStep) data;

            ActivityUtil.replaceFragment(getSupportFragmentManager(),
                    RecipeStepDetailFragment.newInstance(step),
                    R.id.detail_container,
                    false);
        } else {
            Intent intent = RecipeStepDetailActivity.newInstance(this, mRecipe, position);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);

        itemWidget = menu.findItem(R.id.ac_widget);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setWidgetMenuState();

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this));
                return true;
            case R.id.ac_widget:
                int widgetRecipeId = Settings.getInstance().getInt(Settings.Key.SELECTED_RECIPE_ID);
                boolean isWidgetRecipe = mRecipe.getId() == widgetRecipeId;

                if (isWidgetRecipe) Settings.getInstance().remove(Settings.Key.SELECTED_RECIPE_ID);
                else Settings.getInstance().put(Settings.Key.SELECTED_RECIPE_ID, mRecipe.getId());

                supportInvalidateOptionsMenu();

                IngredientWidgetService.startActionUpdateRecipeWidget(this);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setWidgetMenuState(){
        int widgetRecipeId = Settings.getInstance().getInt(Settings.Key.SELECTED_RECIPE_ID);
        boolean isWidgetRecipe = mRecipe.getId() == widgetRecipeId;

        int iconResId = isWidgetRecipe ? R.drawable.ic_widgets_selected : R.drawable.ic_widgets;
        int textResId = isWidgetRecipe ? R.string.recipe_widget_remove : R.string.recipe_widget_add;

        itemWidget.setIcon(iconResId);
        itemWidget.setTitle(textResId);
    }

}
