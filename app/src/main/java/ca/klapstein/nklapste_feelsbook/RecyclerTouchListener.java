package ca.klapstein.nklapste_feelsbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecyclerTouchListener is based off code provided at:
 * <p>
 * https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 * <p>
 * Provides a {@code ClickListener} interface that then provides stubs for a {@code onClick} and
 * {@code onLongClick} methods for RecyclerViews.
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private static final String TAG = "RecyclerTouchListener";

    private GestureDetector gestureDetector;
    private ClickListener clickListener;

    RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent event) {
                View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent event) {

        View child = view.findChildViewUnder(event.getX(), event.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(event)) {
            clickListener.onClick(child, view.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent event) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}