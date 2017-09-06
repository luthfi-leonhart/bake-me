package com.upwardproject.bakeme.ui.recipe_step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.model.Recipe;
import com.upwardproject.bakeme.model.RecipeRepository;
import com.upwardproject.bakeme.model.RecipeStep;
import com.upwardproject.bakeme.ui.BaseActivity;
import com.upwardproject.bakeme.util.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeStepListActivity}.
 */
public class RecipeStepDetailActivity extends BaseActivity {

    private static final String PARAM_INDEX = "index";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.previous_iv)
    ImageView ivPrevious;
    @BindView(R.id.next_iv)
    ImageView ivNext;
    @BindView(R.id.step_index_info_tv)
    TextView tvIndexInfo;

    private Recipe mRecipe;
    private int mCurrentIndex;

    public static Intent newInstance(Context context, Recipe recipe, int defaultIndex) {
        Intent intent = new Intent(context, RecipeStepDetailActivity.class);
        intent.putExtra(RecipeRepository.PARAM_OBJECT, recipe);
        intent.putExtra(PARAM_INDEX, defaultIndex);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);

        mRecipe = getIntent().getParcelableExtra(RecipeRepository.PARAM_OBJECT);

        if (savedInstanceState == null) {
            mCurrentIndex = getIntent().getIntExtra(PARAM_INDEX, 0);
            replaceFragmentWithStepIndex(mCurrentIndex);
        }

        setToolbar(toolbar, getTitle());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private RecipeStep getStep(int index) {
        return mRecipe.getSteps().get(index);
    }

    private void replaceFragmentWithStepIndex(int index) {
        RecipeStep step = getStep(index);

        ActivityUtil.replaceFragment(getSupportFragmentManager(),
                RecipeStepDetailFragment.newInstance(step),
                R.id.detail_container,
                false);

        setTitle(step.getShortDescription());

        String indexInfoText = String.format(
                getString(R.string.recipe_step_index_info_format),
                index + 1,
                mRecipe.getSteps().size()
        );
        tvIndexInfo.setText(indexInfoText);
    }

    @OnClick(R.id.previous_iv)
    void showPreviousStep() {
        mCurrentIndex--;

        if (mCurrentIndex == 0) ivPrevious.setVisibility(View.INVISIBLE);
        ivNext.setVisibility(View.VISIBLE);

        replaceFragmentWithStepIndex(mCurrentIndex);
    }

    @OnClick(R.id.next_iv)
    void showNextStep() {
        mCurrentIndex++;

        if (mCurrentIndex == mRecipe.getSteps().size() - 1) ivNext.setVisibility(View.INVISIBLE);
        ivPrevious.setVisibility(View.VISIBLE);

        replaceFragmentWithStepIndex(mCurrentIndex);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PARAM_INDEX, mCurrentIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mCurrentIndex = savedInstanceState.getInt(PARAM_INDEX, 0);
    }
}
