package com.yeputra.moviecatalogue.utils;

import com.google.android.material.appbar.AppBarLayout;

public class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
    // State
    public enum State {
        EXPANDED,
        COLLAPSED,
        SWIPING
    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged(State state);
    }

    private State mCurrentState = State.SWIPING;

    private int latestPos = 0;

    private OnOffsetChangedListener listener;

    public AppBarStateChangeListener(OnOffsetChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int pos = Math.abs(i);

        if (i == 0) {
            updateState(State.EXPANDED);
        } else if (pos == appBarLayout.getTotalScrollRange()) {
            updateState(State.COLLAPSED);
        } else {
            mCurrentState = State.EXPANDED;
            updateState(State.SWIPING);
        }
        latestPos = pos;
    }

    void updateState(State newState) {
        if (mCurrentState != newState) {
            listener.onOffsetChanged(newState);
        }
        mCurrentState = newState;
    }
}