package com.example.CameraDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


public class TriButton extends Button {

    private int state = 0;

    private static final int[] BUTTON_STATE_ONE = {R.attr.state_one};
    private static final int[] BUTTON_STATE_TWO = {R.attr.state_two};
    private static final int[] BUTTON_STATE_THREE = {R.attr.state_three};

    public TriButton(Context context) {
        super(context);
    }

    public TriButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TriButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        int [] baseState = super.onCreateDrawableState(extraSpace + 3);
        switch (state) {
            case 0: mergeDrawableStates(baseState, BUTTON_STATE_ONE); break;
            case 1: mergeDrawableStates(baseState, BUTTON_STATE_TWO); break;
            case 2: mergeDrawableStates(baseState, BUTTON_STATE_THREE); break;
        }

        return baseState;
    }

    @Override
    public boolean performClick() {
        state = ++state % 3;
        return super.performClick();
    }


    public int getState() {
        return state;
    }
}
