package vmodev.base.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import vmodev.base.R;


/**
 * Created by thanhle on 3/13/17.
 */

public class SquareCardView extends CardView {
    private boolean isSquareWidth;

    public SquareCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inits(context, attrs, 0);

    }

    public SquareCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inits(context, attrs, defStyleAttr);
    }

    private void inits(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareView, defStyle, 0);
        isSquareWidth = typedArray.getBoolean(R.styleable.SquareView_is_square_width, true);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isSquareWidth) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        } else {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        }
    }

}
