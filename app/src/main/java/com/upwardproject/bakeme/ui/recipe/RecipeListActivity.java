package com.upwardproject.bakeme.ui.recipe;

import android.os.Bundle;

import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.ui.BaseActivity;
import com.upwardproject.bakeme.util.ActivityUtil;

public class RecipeListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                    new RecipeListFragment(),
                    R.id.container,
                    false);
        }
    }
}
