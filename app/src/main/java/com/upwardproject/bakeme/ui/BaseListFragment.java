package com.upwardproject.bakeme.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.upwardproject.bakeme.R;
import com.upwardproject.bakeme.ui.widget.EmptyRecyclerView;
import com.upwardproject.bakeme.ui.widget.EmptyViewHolder;
import com.upwardproject.bakeme.ui.widget.EndlessRecyclerViewOnScrollListener;

import butterknife.BindView;

public abstract class BaseListFragment extends Fragment implements BaseContract.RemoteView, SwipeRefreshLayout.OnRefreshListener {
    private final String PARAM_PAGE = "page";

    @BindView(R.id.refresh)
    protected SwipeRefreshLayout srlRefresh;
    @BindView(R.id.list)
    protected EmptyRecyclerView rvList;
    protected EmptyViewHolder emptyViewHolder;

    protected int pageToLoad = 1;
    protected BaseListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) pageToLoad = savedInstanceState.getInt(PARAM_PAGE);
        else pageToLoad = 1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        srlRefresh.setColorSchemeResources(R.color.colorAccent);
        srlRefresh.setOnRefreshListener(this);

        rvList.setLayoutManager(getLayoutManager());
        if (endlessLoaderEnabled()) {
            rvList.addOnScrollListener(new EndlessRecyclerViewOnScrollListener(rvList.getLayoutManager()) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    pageToLoad = page + 1;
                    loadData();
                }
            });
        }

        emptyViewHolder.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyViewHolder.hide();
                loadData();
            }
        });
    }

    @Override
    public void onRefresh() {
        pageToLoad = 1;
        loadData();
    }

    @Override
    public void setProgressIndicator(final boolean active) {
        srlRefresh.post(new Runnable() {
            @Override
            public void run() {
                srlRefresh.setRefreshing(active);
            }
        });
    }

    protected void showConnectionError() {
        if (isAdded()) {
            emptyViewHolder.setMessage(R.string.error_network_unavailable);
            emptyViewHolder.setIcon(R.drawable.ic_network_off);
            rvList.setAsEmpty();
            setProgressIndicator(false);
        }
    }

    @Override
    public void showEmpty(String message) {
        if (isAdded() && pageToLoad == 1) {
            emptyViewHolder.setMessage(message != null ? message : getString(R.string.error_data_not_found));
            emptyViewHolder.setIcon(R.drawable.ic_empty);
            rvList.setAsEmpty();
        }
    }

    @Override
    public void showError(String message) {
        if (isAdded()) {
            emptyViewHolder.setMessage(message != null ? message : getString(R.string.error_loading_failed));
            emptyViewHolder.setIcon(R.drawable.ic_error);
            rvList.setAsEmpty();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PARAM_PAGE, pageToLoad);
    }

    protected void initEmptyView(View v) {
        if (v != null) {
            rvList.setEmptyView(v);
            emptyViewHolder = new EmptyViewHolder(v);
        }
    }

    public abstract void loadData();

    protected abstract boolean endlessLoaderEnabled();

    protected abstract RecyclerView.LayoutManager getLayoutManager();
}
