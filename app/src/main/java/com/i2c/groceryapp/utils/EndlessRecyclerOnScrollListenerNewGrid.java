package com.i2c.groceryapp.utils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListenerNewGrid extends RecyclerView.OnScrollListener {
    private int current_page = 1;
    int firstVisibleItem;
    private boolean loading = true;
    private LinearLayoutManager mLinearLayoutManager;
    private int previousTotal = 0;
    int totalItemCount;
    int visibleItemCount;
    private int visibleThreshold = 1;

    public EndlessRecyclerOnScrollListenerNewGrid(LinearLayoutManager paramLinearLayoutManager) {
        mLinearLayoutManager = paramLinearLayoutManager;
    }

    public abstract void onLoadMore(int paramInt);

    public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {
        super.onScrolled(paramRecyclerView, paramInt1, paramInt2);
        visibleItemCount = paramRecyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (visibleItemCount == 0) {
            previousTotal = 0;
            loading = true;
        }
        if ((loading) && (totalItemCount > previousTotal + 1)) {
            loading = false;
            previousTotal = totalItemCount;
        }
        if ((!loading) && (totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold)) {
            current_page += 1;
            onLoadMore(current_page);
            loading = true;
        }
    }
}
