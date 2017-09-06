package com.upwardproject.bakeme.ui.recipe_step;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.model.RecipeStep;
import com.upwardproject.bakeme.model.RecipeStepRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link RecipeStepListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepDetailActivity}
 * on handsets.
 */
public class RecipeStepDetailFragment extends Fragment {
    // TODO add video

    @BindView(R.id.step_image_iv)
    ImageView ivImage;
    @BindView(R.id.step_description_tv)
    TextView tvDescription;

    private Unbinder unbinder;

    private RecipeStep mStep;

    public static RecipeStepDetailFragment newInstance(RecipeStep step) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeStepRepository.PARAM_OBJECT, step);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RecipeStepRepository.PARAM_OBJECT)) {
            mStep = getArguments().getParcelable(RecipeStepRepository.PARAM_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mStep.getThumbnailUrl() != null && !mStep.getThumbnailUrl().isEmpty()) {
            ivImage.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(mStep.getThumbnailUrl())
                    .placeholder(R.color.colorPrimaryLight)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(ivImage);
        } else ivImage.setVisibility(View.GONE);

        tvDescription.setText(mStep.getDescription());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
