/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.elevationdrag;

import android.content.Context;
import android.graphics.Outline;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FrameLayout} that allows the user to drag and reposition child views.
 */
public class DragFrameLayout extends FrameLayout {

    /**
     * The list of {@link View}s that will be draggable.
     */
    public ArrayList<View> mDragViews;

    /**
     * The {@link DragFrameLayoutController} that will be notify on drag.
     */
    private DragFrameLayoutController mDragFrameLayoutController;

    private MyViewDragHelper mDragHelper;

    public DragFrameLayout(Context context) {
        this(context, null, 0, 0);
    }

    public DragFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public DragFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mDragViews = new ArrayList<View>();

        /**
         * Create the {@link ViewDragHelper} and set its callback.
         */
        mDragHelper = MyViewDragHelper.create(this, 10.0f, new MyViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return mDragViews.contains(child);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

                if (mDragFrameLayoutController != null) {
                    mDragFrameLayoutController.onDragging(changedView);
                }
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                if (mDragFrameLayoutController != null) {
                    mDragFrameLayoutController.onDragDrop(capturedChild, true);
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (mDragFrameLayoutController != null) {
                    mDragFrameLayoutController.onDragDrop(releasedChild, false);
                }
            }
        });
    }

    private CircleOutlineProvider mCircleOutlineProviderInstance = null;

    public CircleOutlineProvider getCircleOutlineProvider() {
        if (mCircleOutlineProviderInstance == null)
        {
            mCircleOutlineProviderInstance = new CircleOutlineProvider();
        }

        return mCircleOutlineProviderInstance;
    }

    /**
     * ViewOutlineProvider which sets the outline to be an oval which fits the view bounds.
     */
    public class CircleOutlineProvider extends ViewOutlineProvider {
        @Override
        public void getOutline(View view, Outline outline) {
            //(int)mDragHelper.convertDpToPixel(30)
            outline.setOval(0, 0, view.getWidth(), view.getHeight());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("DragFrameLayout", "onInterceptTouchEvent");
        final int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        return true;
    }

    /**
     * Adds a new {@link View} to the list of views that are draggable within the container.
     * @param dragView the {@link View} to make draggable
     */
    public void addDragView(View dragView) {
        mDragViews.add(dragView);
    }

    /**
     * Sets the {@link DragFrameLayoutController} that will receive the drag events.
     * @param dragFrameLayoutController a {@link DragFrameLayoutController}
     */
    public void setDragFrameController(DragFrameLayoutController dragFrameLayoutController) {
        mDragFrameLayoutController = dragFrameLayoutController;
    }

    /**
     * A controller that will receive the drag events.
     */
    public interface DragFrameLayoutController {

        public void onDragDrop(View capturedChild, boolean captured);

        public void onDragging(View changedView);
    }
}
