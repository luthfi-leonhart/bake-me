package com.upwardproject.bakeme.ui.recipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.ui.BaseActivity;
import com.upwardproject.bakeme.ui.SimpleIdlingResource;
import com.upwardproject.bakeme.util.ActivityUtil;

public class RecipeListActivity extends BaseActivity {

    protected SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIdlingResource = (SimpleIdlingResource) getIdlingResource();

        if (savedInstanceState == null) {
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    new RecipeListFragment(),
                    RecipeListFragment.TAG,
                    R.id.container,
                    false);
        }
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
