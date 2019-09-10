/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.Bitmap
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.support.v7.widget.AppCompatImageView
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.ScaleGestureDetector$SimpleOnScaleGestureListener
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnTouchListener
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.OverScroller
 *  android.widget.Scroller
 *  java.lang.Enum
 *  java.lang.Math
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.UnsupportedOperationException
 */
package com.something.boley.personaldeliveryassistant;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.Scroller;

public class TouchImageView
extends AppCompatImageView {
    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private static final float SUPER_MIN_MULTIPLIER = 0.75f;
    private Context context;
    private ZoomVariables delayedZoomVariables;
    private GestureDetector.OnDoubleTapListener doubleTapListener = null;
    private Fling fling;
    private boolean imageRenderedAtLeastOnce;
    private float[] m;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleDetector;
    private ImageView.ScaleType mScaleType;
    private float matchViewHeight;
    private float matchViewWidth;
    private Matrix matrix;
    private float maxScale;
    private float minScale;
    private float normalizedScale;
    private boolean onDrawReady;
    private float prevMatchViewHeight;
    private float prevMatchViewWidth;
    private Matrix prevMatrix;
    private int prevViewHeight;
    private int prevViewWidth;
    private State state;
    private float superMaxScale;
    private float superMinScale;
    private OnTouchImageViewListener touchImageViewListener = null;
    private View.OnTouchListener userTouchListener = null;
    private int viewHeight;
    private int viewWidth;

    public TouchImageView(Context context) {
        super(context);
        this.sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.sharedConstructing(context);
    }

    @TargetApi(value=16)
    private void compatPostOnAnimation(Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.postOnAnimation(runnable);
            return;
        }
        this.postDelayed(runnable, 16L);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void fitImageToView() {
        Drawable drawable2 = this.getDrawable();
        if (drawable2 == null || drawable2.getIntrinsicWidth() == 0 || drawable2.getIntrinsicHeight() == 0 || this.matrix == null || this.prevMatrix == null) {
            return;
        }
        int n = drawable2.getIntrinsicWidth();
        int n2 = drawable2.getIntrinsicHeight();
        float f = (float)this.viewWidth / (float)n;
        float f2 = (float)this.viewHeight / (float)n2;
        switch (1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
            default: {
                throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
            }
            case 1: {
                f = f2 = 1.0f;
                break;
            }
            case 2: {
                f = f2 = Math.max((float)f, (float)f2);
                break;
            }
            case 3: {
                f = f2 = Math.min((float)1.0f, (float)Math.min((float)f, (float)f2));
            }
            case 4: {
                f = f2 = Math.min((float)f, (float)f2);
            }
            case 5: 
        }
        float f3 = (float)this.viewWidth - f * (float)n;
        float f4 = (float)this.viewHeight - f2 * (float)n2;
        this.matchViewWidth = (float)this.viewWidth - f3;
        this.matchViewHeight = (float)this.viewHeight - f4;
        if (!this.isZoomed() && !this.imageRenderedAtLeastOnce) {
            this.matrix.setScale(f, f2);
            this.matrix.postTranslate(f3 / 2.0f, f4 / 2.0f);
            this.normalizedScale = 1.0f;
        } else {
            if (this.prevMatchViewWidth == 0.0f || this.prevMatchViewHeight == 0.0f) {
                this.savePreviousImageValues();
            }
            this.prevMatrix.getValues(this.m);
            this.m[0] = this.matchViewWidth / (float)n * this.normalizedScale;
            this.m[4] = this.matchViewHeight / (float)n2 * this.normalizedScale;
            float f5 = this.m[2];
            float f6 = this.m[5];
            this.translateMatrixAfterRotate(2, f5, this.prevMatchViewWidth * this.normalizedScale, this.getImageWidth(), this.prevViewWidth, this.viewWidth, n);
            this.translateMatrixAfterRotate(5, f6, this.prevMatchViewHeight * this.normalizedScale, this.getImageHeight(), this.prevViewHeight, this.viewHeight, n2);
            this.matrix.setValues(this.m);
        }
        this.fixTrans();
        this.setImageMatrix(this.matrix);
    }

    private void fixScaleTrans() {
        this.fixTrans();
        this.matrix.getValues(this.m);
        if (this.getImageWidth() < (float)this.viewWidth) {
            this.m[2] = ((float)this.viewWidth - this.getImageWidth()) / 2.0f;
        }
        if (this.getImageHeight() < (float)this.viewHeight) {
            this.m[5] = ((float)this.viewHeight - this.getImageHeight()) / 2.0f;
        }
        this.matrix.setValues(this.m);
    }

    private void fixTrans() {
        this.matrix.getValues(this.m);
        float f = this.m[2];
        float f2 = this.m[5];
        float f3 = this.getFixTrans(f, this.viewWidth, this.getImageWidth());
        float f4 = this.getFixTrans(f2, this.viewHeight, this.getImageHeight());
        if (f3 != 0.0f || f4 != 0.0f) {
            this.matrix.postTranslate(f3, f4);
        }
    }

    private float getFixDragTrans(float f, float f2, float f3) {
        if (f3 <= f2) {
            f = 0.0f;
        }
        return f;
    }

    /*
     * Enabled aggressive block sorting
     */
    private float getFixTrans(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f3 <= f2) {
            f4 = 0.0f;
            f5 = f2 - f3;
        } else {
            f4 = f2 - f3;
            f5 = 0.0f;
        }
        if (f < f4) {
            return f4 + -f;
        }
        if (f > f5) {
            return f5 + -f;
        }
        return 0.0f;
    }

    private float getImageHeight() {
        return this.matchViewHeight * this.normalizedScale;
    }

    private float getImageWidth() {
        return this.matchViewWidth * this.normalizedScale;
    }

    private void printMatrixInfo() {
        float[] arrf = new float[9];
        this.matrix.getValues(arrf);
        Log.d((String)DEBUG, (String)("Scale: " + arrf[0] + " TransX: " + arrf[2] + " TransY: " + arrf[5]));
    }

    private void savePreviousImageValues() {
        if (this.matrix != null && this.viewHeight != 0 && this.viewWidth != 0) {
            this.matrix.getValues(this.m);
            this.prevMatrix.setValues(this.m);
            this.prevMatchViewHeight = this.matchViewHeight;
            this.prevMatchViewWidth = this.matchViewWidth;
            this.prevViewHeight = this.viewHeight;
            this.prevViewWidth = this.viewWidth;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void scaleImage(double d, float f, float f2, boolean bl) {
        float f3;
        float f4;
        if (bl) {
            f4 = this.superMinScale;
            f3 = this.superMaxScale;
        } else {
            f4 = this.minScale;
            f3 = this.maxScale;
        }
        float f5 = this.normalizedScale;
        this.normalizedScale = (float)(d * (double)this.normalizedScale);
        if (this.normalizedScale > f3) {
            this.normalizedScale = f3;
            d = f3 / f5;
        } else if (this.normalizedScale < f4) {
            this.normalizedScale = f4;
            d = f4 / f5;
        }
        this.matrix.postScale((float)d, (float)d, f, f2);
        this.fixScaleTrans();
    }

    private void setState(State state) {
        this.state = state;
    }

    private int setViewSize(int n, int n2, int n3) {
        switch (n) {
            default: {
                return n2;
            }
            case 1073741824: {
                return n2;
            }
            case Integer.MIN_VALUE: {
                return Math.min((int)n3, (int)n2);
            }
            case 0: 
        }
        return n3;
    }

    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.context = context;
        this.mScaleDetector = new ScaleGestureDetector(context, (ScaleGestureDetector.OnScaleGestureListener)new ScaleListener());
        this.mGestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)new GestureListener());
        this.matrix = new Matrix();
        this.prevMatrix = new Matrix();
        this.m = new float[9];
        this.normalizedScale = 1.0f;
        if (this.mScaleType == null) {
            this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        }
        this.minScale = 1.0f;
        this.maxScale = 3.0f;
        this.superMinScale = 0.75f * this.minScale;
        this.superMaxScale = 1.25f * this.maxScale;
        this.setImageMatrix(this.matrix);
        this.setScaleType(ImageView.ScaleType.MATRIX);
        this.setState(State.NONE);
        this.onDrawReady = false;
        super.setOnTouchListener((View.OnTouchListener)new PrivateOnTouchListener());
    }

    private PointF transformCoordBitmapToTouch(float f, float f2) {
        this.matrix.getValues(this.m);
        float f3 = this.getDrawable().getIntrinsicWidth();
        float f4 = this.getDrawable().getIntrinsicHeight();
        float f5 = f / f3;
        float f6 = f2 / f4;
        return new PointF(this.m[2] + f5 * this.getImageWidth(), this.m[5] + f6 * this.getImageHeight());
    }

    private PointF transformCoordTouchToBitmap(float f, float f2, boolean bl) {
        this.matrix.getValues(this.m);
        float f3 = this.getDrawable().getIntrinsicWidth();
        float f4 = this.getDrawable().getIntrinsicHeight();
        float f5 = this.m[2];
        float f6 = this.m[5];
        float f7 = f3 * (f - f5) / this.getImageWidth();
        float f8 = f4 * (f2 - f6) / this.getImageHeight();
        if (bl) {
            f7 = Math.min((float)Math.max((float)f7, (float)0.0f), (float)f3);
            f8 = Math.min((float)Math.max((float)f8, (float)0.0f), (float)f4);
        }
        return new PointF(f7, f8);
    }

    private void translateMatrixAfterRotate(int n, float f, float f2, float f3, int n2, int n3, int n4) {
        if (f3 < (float)n3) {
            this.m[n] = 0.5f * ((float)n3 - (float)n4 * this.m[0]);
            return;
        }
        if (f > 0.0f) {
            this.m[n] = -(0.5f * (f3 - (float)n3));
            return;
        }
        float f4 = (Math.abs((float)f) + 0.5f * (float)n2) / f2;
        this.m[n] = -(f4 * f3 - 0.5f * (float)n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean canScrollHorizontally(int n) {
        this.matrix.getValues(this.m);
        float f = this.m[2];
        return !(this.getImageWidth() < (float)this.viewWidth || f >= -1.0f && n < 0) && (!(1.0f + (Math.abs((float)f) + (float)this.viewWidth) >= this.getImageWidth()) || n <= 0);
    }

    public boolean canScrollHorizontallyFroyo(int n) {
        return this.canScrollHorizontally(n);
    }

    public float getCurrentZoom() {
        return this.normalizedScale;
    }

    public float getMaxZoom() {
        return this.maxScale;
    }

    public float getMinZoom() {
        return this.minScale;
    }

    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    public PointF getScrollPosition() {
        Drawable drawable2 = this.getDrawable();
        if (drawable2 == null) {
            return null;
        }
        int n = drawable2.getIntrinsicWidth();
        int n2 = drawable2.getIntrinsicHeight();
        PointF pointF = this.transformCoordTouchToBitmap(this.viewWidth / 2, this.viewHeight / 2, true);
        pointF.x /= (float)n;
        pointF.y /= (float)n2;
        return pointF;
    }

    public RectF getZoomedRect() {
        if (this.mScaleType == ImageView.ScaleType.FIT_XY) {
            throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
        }
        PointF pointF = this.transformCoordTouchToBitmap(0.0f, 0.0f, true);
        PointF pointF2 = this.transformCoordTouchToBitmap(this.viewWidth, this.viewHeight, true);
        float f = this.getDrawable().getIntrinsicWidth();
        float f2 = this.getDrawable().getIntrinsicHeight();
        return new RectF(pointF.x / f, pointF.y / f2, pointF2.x / f, pointF2.y / f2);
    }

    public boolean isZoomed() {
        return this.normalizedScale != 1.0f;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.savePreviousImageValues();
    }

    protected void onDraw(Canvas canvas) {
        this.onDrawReady = true;
        this.imageRenderedAtLeastOnce = true;
        if (this.delayedZoomVariables != null) {
            this.setZoom(this.delayedZoomVariables.scale, this.delayedZoomVariables.focusX, this.delayedZoomVariables.focusY, this.delayedZoomVariables.scaleType);
            this.delayedZoomVariables = null;
        }
        super.onDraw(canvas);
    }

    protected void onMeasure(int n, int n2) {
        Drawable drawable2 = this.getDrawable();
        if (drawable2 == null || drawable2.getIntrinsicWidth() == 0 || drawable2.getIntrinsicHeight() == 0) {
            this.setMeasuredDimension(0, 0);
            return;
        }
        int n3 = drawable2.getIntrinsicWidth();
        int n4 = drawable2.getIntrinsicHeight();
        int n5 = View.MeasureSpec.getSize((int)n);
        int n6 = View.MeasureSpec.getMode((int)n);
        int n7 = View.MeasureSpec.getSize((int)n2);
        int n8 = View.MeasureSpec.getMode((int)n2);
        this.viewWidth = this.setViewSize(n6, n5, n3);
        this.viewHeight = this.setViewSize(n8, n7, n4);
        this.setMeasuredDimension(this.viewWidth, this.viewHeight);
        this.fitImageToView();
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle)parcelable;
            this.normalizedScale = bundle.getFloat("saveScale");
            this.m = bundle.getFloatArray("matrix");
            this.prevMatrix.setValues(this.m);
            this.prevMatchViewHeight = bundle.getFloat("matchViewHeight");
            this.prevMatchViewWidth = bundle.getFloat("matchViewWidth");
            this.prevViewHeight = bundle.getInt("viewHeight");
            this.prevViewWidth = bundle.getInt("viewWidth");
            this.imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", this.normalizedScale);
        bundle.putFloat("matchViewHeight", this.matchViewHeight);
        bundle.putFloat("matchViewWidth", this.matchViewWidth);
        bundle.putInt("viewWidth", this.viewWidth);
        bundle.putInt("viewHeight", this.viewHeight);
        this.matrix.getValues(this.m);
        bundle.putFloatArray("matrix", this.m);
        bundle.putBoolean("imageRendered", this.imageRenderedAtLeastOnce);
        return bundle;
    }

    public void resetZoom() {
        this.normalizedScale = 1.0f;
        this.fitImageToView();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    public void setImageDrawable(Drawable drawable2) {
        super.setImageDrawable(drawable2);
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    public void setImageResource(int n) {
        super.setImageResource(n);
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    public void setMaxZoom(float f) {
        this.maxScale = f;
        this.superMaxScale = 1.25f * this.maxScale;
    }

    public void setMinZoom(float f) {
        this.minScale = f;
        this.superMinScale = 0.75f * this.minScale;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.doubleTapListener = onDoubleTapListener;
    }

    public void setOnTouchImageViewListener(OnTouchImageViewListener onTouchImageViewListener) {
        this.touchImageViewListener = onTouchImageViewListener;
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.userTouchListener = onTouchListener;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == ImageView.ScaleType.FIT_START || scaleType == ImageView.ScaleType.FIT_END) {
            throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        }
        if (scaleType == ImageView.ScaleType.MATRIX) {
            super.setScaleType(ImageView.ScaleType.MATRIX);
            return;
        } else {
            this.mScaleType = scaleType;
            if (!this.onDrawReady) return;
            {
                this.setZoom(this);
                return;
            }
        }
    }

    public void setScrollPosition(float f, float f2) {
        this.setZoom(this.normalizedScale, f, f2);
    }

    public void setZoom(float f) {
        this.setZoom(f, 0.5f, 0.5f);
    }

    public void setZoom(float f, float f2, float f3) {
        this.setZoom(f, f2, f3, this.mScaleType);
    }

    public void setZoom(float f, float f2, float f3, ImageView.ScaleType scaleType) {
        if (!this.onDrawReady) {
            this.delayedZoomVariables = new ZoomVariables(f, f2, f3, scaleType);
            return;
        }
        if (scaleType != this.mScaleType) {
            this.setScaleType(scaleType);
        }
        this.resetZoom();
        this.scaleImage(f, this.viewWidth / 2, this.viewHeight / 2, true);
        this.matrix.getValues(this.m);
        this.m[2] = -(f2 * this.getImageWidth() - 0.5f * (float)this.viewWidth);
        this.m[5] = -(f3 * this.getImageHeight() - 0.5f * (float)this.viewHeight);
        this.matrix.setValues(this.m);
        this.fixTrans();
        this.setImageMatrix(this.matrix);
    }

    public void setZoom(TouchImageView touchImageView) {
        PointF pointF = touchImageView.getScrollPosition();
        this.setZoom(touchImageView.getCurrentZoom(), pointF.x, pointF.y, touchImageView.getScaleType());
    }

    @TargetApi(value=9)
    private class CompatScroller {
        boolean isPreGingerbread;
        OverScroller overScroller;
        Scroller scroller;

        public CompatScroller(Context context) {
            if (Build.VERSION.SDK_INT < 9) {
                this.isPreGingerbread = true;
                this.scroller = new Scroller(context);
                return;
            }
            this.isPreGingerbread = false;
            this.overScroller = new OverScroller(context);
        }

        public boolean computeScrollOffset() {
            if (this.isPreGingerbread) {
                return this.scroller.computeScrollOffset();
            }
            this.overScroller.computeScrollOffset();
            return this.overScroller.computeScrollOffset();
        }

        public void fling(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            if (this.isPreGingerbread) {
                this.scroller.fling(n, n2, n3, n4, n5, n6, n7, n8);
                return;
            }
            this.overScroller.fling(n, n2, n3, n4, n5, n6, n7, n8);
        }

        public void forceFinished(boolean bl) {
            if (this.isPreGingerbread) {
                this.scroller.forceFinished(bl);
                return;
            }
            this.overScroller.forceFinished(bl);
        }

        public int getCurrX() {
            if (this.isPreGingerbread) {
                return this.scroller.getCurrX();
            }
            return this.overScroller.getCurrX();
        }

        public int getCurrY() {
            if (this.isPreGingerbread) {
                return this.scroller.getCurrY();
            }
            return this.overScroller.getCurrY();
        }

        public boolean isFinished() {
            if (this.isPreGingerbread) {
                return this.scroller.isFinished();
            }
            return this.overScroller.isFinished();
        }
    }

    private class DoubleTapZoom
    implements Runnable {
        private static final float ZOOM_TIME = 500.0f;
        private float bitmapX;
        private float bitmapY;
        private PointF endTouch;
        private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        private long startTime;
        private PointF startTouch;
        private float startZoom;
        private boolean stretchImageToSuper;
        private float targetZoom;

        DoubleTapZoom(float f, float f2, float f3, boolean bl) {
            TouchImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = TouchImageView.this.normalizedScale;
            this.targetZoom = f;
            this.stretchImageToSuper = bl;
            PointF pointF = TouchImageView.this.transformCoordTouchToBitmap(f2, f3, false);
            this.bitmapX = pointF.x;
            this.bitmapY = pointF.y;
            this.startTouch = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            this.endTouch = new PointF((float)(TouchImageView.this.viewWidth / 2), (float)(TouchImageView.this.viewHeight / 2));
        }

        private double calculateDeltaScale(float f) {
            return (double)(this.startZoom + f * (this.targetZoom - this.startZoom)) / (double)TouchImageView.this.normalizedScale;
        }

        private float interpolate() {
            float f = Math.min((float)1.0f, (float)((float)(System.currentTimeMillis() - this.startTime) / 500.0f));
            return this.interpolator.getInterpolation(f);
        }

        private void translateImageToCenterTouchPosition(float f) {
            float f2 = this.startTouch.x + f * (this.endTouch.x - this.startTouch.x);
            float f3 = this.startTouch.y + f * (this.endTouch.y - this.startTouch.y);
            PointF pointF = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            TouchImageView.this.matrix.postTranslate(f2 - pointF.x, f3 - pointF.y);
        }

        public void run() {
            float f = this.interpolate();
            double d = this.calculateDeltaScale(f);
            TouchImageView.this.scaleImage(d, this.bitmapX, this.bitmapY, this.stretchImageToSuper);
            this.translateImageToCenterTouchPosition(f);
            TouchImageView.this.fixScaleTrans();
            TouchImageView.this.setImageMatrix(TouchImageView.this.matrix);
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            if (f < 1.0f) {
                TouchImageView.this.compatPostOnAnimation(this);
                return;
            }
            TouchImageView.this.setState(State.NONE);
        }
    }

    private class Fling
    implements Runnable {
        int currX;
        int currY;
        CompatScroller scroller;

        /*
         * Enabled aggressive block sorting
         */
        Fling(int n, int n2) {
            int n3;
            int n4;
            int n5;
            int n6;
            TouchImageView.this.setState(State.FLING);
            this.scroller = new CompatScroller(TouchImageView.this.context);
            TouchImageView.this.matrix.getValues(TouchImageView.this.m);
            int n7 = (int)TouchImageView.this.m[2];
            int n8 = (int)TouchImageView.this.m[5];
            if (TouchImageView.this.getImageWidth() > (float)TouchImageView.this.viewWidth) {
                n3 = TouchImageView.this.viewWidth - (int)TouchImageView.this.getImageWidth();
                n6 = 0;
            } else {
                n6 = n7;
                n3 = n7;
            }
            if (TouchImageView.this.getImageHeight() > (float)TouchImageView.this.viewHeight) {
                n5 = TouchImageView.this.viewHeight - (int)TouchImageView.this.getImageHeight();
                n4 = 0;
            } else {
                n4 = n8;
                n5 = n8;
            }
            this.scroller.fling(n7, n8, n, n2, n3, n6, n5, n4);
            this.currX = n7;
            this.currY = n8;
        }

        public void cancelFling() {
            if (this.scroller != null) {
                TouchImageView.this.setState(State.NONE);
                this.scroller.forceFinished(true);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public void run() {
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            if (this.scroller.isFinished()) {
                this.scroller = null;
                return;
            } else {
                if (!this.scroller.computeScrollOffset()) return;
                {
                    int n = this.scroller.getCurrX();
                    int n2 = this.scroller.getCurrY();
                    int n3 = n - this.currX;
                    int n4 = n2 - this.currY;
                    this.currX = n;
                    this.currY = n2;
                    TouchImageView.this.matrix.postTranslate((float)n3, (float)n4);
                    TouchImageView.this.fixTrans();
                    TouchImageView.this.setImageMatrix(TouchImageView.this.matrix);
                    TouchImageView.this.compatPostOnAnimation(this);
                    return;
                }
            }
        }
    }

    private class GestureListener
    extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onDoubleTap(MotionEvent motionEvent) {
            GestureDetector.OnDoubleTapListener onDoubleTapListener = TouchImageView.this.doubleTapListener;
            boolean bl = false;
            if (onDoubleTapListener != null) {
                bl = TouchImageView.this.doubleTapListener.onDoubleTap(motionEvent);
            }
            if (TouchImageView.this.state != State.NONE) return bl;
            float f = TouchImageView.this.normalizedScale == TouchImageView.this.minScale ? TouchImageView.this.maxScale : TouchImageView.this.minScale;
            DoubleTapZoom doubleTapZoom = new DoubleTapZoom(f, motionEvent.getX(), motionEvent.getY(), false);
            TouchImageView.this.compatPostOnAnimation(doubleTapZoom);
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            if (TouchImageView.this.doubleTapListener != null) {
                return TouchImageView.this.doubleTapListener.onDoubleTapEvent(motionEvent);
            }
            return false;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (TouchImageView.this.fling != null) {
                TouchImageView.this.fling.cancelFling();
            }
            TouchImageView.this.fling = new Fling((int)f, (int)f2);
            TouchImageView.this.compatPostOnAnimation(TouchImageView.this.fling);
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }

        public void onLongPress(MotionEvent motionEvent) {
            TouchImageView.this.performLongClick();
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (TouchImageView.this.doubleTapListener != null) {
                return TouchImageView.this.doubleTapListener.onSingleTapConfirmed(motionEvent);
            }
            return TouchImageView.this.performClick();
        }
    }

    public static interface OnTouchImageViewListener {
        public void onMove();
    }

    private class PrivateOnTouchListener
    implements View.OnTouchListener {
        private PointF last = new PointF();

        private PrivateOnTouchListener() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTouch(View view, MotionEvent motionEvent) {
            TouchImageView.this.mScaleDetector.onTouchEvent(motionEvent);
            TouchImageView.this.mGestureDetector.onTouchEvent(motionEvent);
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            if (TouchImageView.this.state == State.NONE || TouchImageView.this.state == State.DRAG || TouchImageView.this.state == State.FLING) {
                switch (motionEvent.getAction()) {
                    case 0: {
                        this.last.set(pointF);
                        if (TouchImageView.this.fling != null) {
                            TouchImageView.this.fling.cancelFling();
                        }
                        TouchImageView.this.setState(State.DRAG);
                        break;
                    }
                    case 2: {
                        if (TouchImageView.this.state != State.DRAG) break;
                        float f = pointF.x - this.last.x;
                        float f2 = pointF.y - this.last.y;
                        float f3 = TouchImageView.this.getFixDragTrans(f, TouchImageView.this.viewWidth, TouchImageView.this.getImageWidth());
                        float f4 = TouchImageView.this.getFixDragTrans(f2, TouchImageView.this.viewHeight, TouchImageView.this.getImageHeight());
                        TouchImageView.this.matrix.postTranslate(f3, f4);
                        TouchImageView.this.fixTrans();
                        this.last.set(pointF.x, pointF.y);
                        break;
                    }
                    case 1: 
                    case 6: {
                        TouchImageView.this.setState(State.NONE);
                        break;
                    }
                }
            }
            TouchImageView.this.setImageMatrix(TouchImageView.this.matrix);
            if (TouchImageView.this.userTouchListener != null) {
                TouchImageView.this.userTouchListener.onTouch(view, motionEvent);
            }
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            return true;
        }
    }

    private class ScaleListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.scaleImage(scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), true);
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.setState(State.ZOOM);
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            boolean bl;
            super.onScaleEnd(scaleGestureDetector);
            TouchImageView.this.setState(State.NONE);
            float f = TouchImageView.this.normalizedScale;
            if (TouchImageView.this.normalizedScale > TouchImageView.this.maxScale) {
                f = TouchImageView.this.maxScale;
                bl = true;
            } else {
                float f2 = TouchImageView.this.normalizedScale FCMPG TouchImageView.this.minScale;
                bl = false;
                if (f2 < 0) {
                    f = TouchImageView.this.minScale;
                    bl = true;
                }
            }
            if (bl) {
                DoubleTapZoom doubleTapZoom = new DoubleTapZoom(f, TouchImageView.this.viewWidth / 2, TouchImageView.this.viewHeight / 2, true);
                TouchImageView.this.compatPostOnAnimation(doubleTapZoom);
            }
        }
    }

    private static final class State
    extends Enum<State> {
        private static final /* synthetic */ State[] $VALUES;
        public static final /* enum */ State ANIMATE_ZOOM;
        public static final /* enum */ State DRAG;
        public static final /* enum */ State FLING;
        public static final /* enum */ State NONE;
        public static final /* enum */ State ZOOM;

        static {
            NONE = new State();
            DRAG = new State();
            ZOOM = new State();
            FLING = new State();
            ANIMATE_ZOOM = new State();
            State[] arrstate = new State[]{NONE, DRAG, ZOOM, FLING, ANIMATE_ZOOM};
            $VALUES = arrstate;
        }

        public static State valueOf(String string2) {
            return (State)Enum.valueOf(State.class, (String)string2);
        }

        public static State[] values() {
            return (State[])$VALUES.clone();
        }
    }

    private class ZoomVariables {
        public float focusX;
        public float focusY;
        public float scale;
        public ImageView.ScaleType scaleType;

        public ZoomVariables(float f, float f2, float f3, ImageView.ScaleType scaleType) {
            this.scale = f;
            this.focusX = f2;
            this.focusY = f3;
            this.scaleType = scaleType;
        }
    }

}

