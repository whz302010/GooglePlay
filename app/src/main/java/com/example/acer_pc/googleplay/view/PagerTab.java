//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.acer_pc.googleplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.acer_pc.googleplay.activity.BaseActivity;
import com.example.acer_pc.googleplay.utils.UIUtils;

public class PagerTab extends ViewGroup {
    private ViewPager mViewPager;
    private PagerTab.PageListener mPageListener;
    private OnPageChangeListener mDelegatePageListener;
    private BaseActivity  mActivity;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mDividerColor;
    private Paint mDividerPaint;
    private int mIndicatorHeight;
    private int mIndicatorWidth;
    private int mIndicatorLeft;
    private int mIndicatorColor;
    private Paint mIndicatorPaint;
    private int mContentWidth;
    private int mContentHeight;
    private int mTabPadding;
    private int mTabTextSize;
    private int mTabBackgroundResId;
    private int mTabTextColorResId;
    private int mTabCount;
    private int mCurrentPosition;
    private float mCurrentOffsetPixels;
    private int mSelectedPosition;
    private boolean mIsBeingDragged;
    private float mLastMotionX;
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mTouchSlop;
    private ScrollerCompat mScroller;
    private int mLastScrollX;
    private int mMaxScrollX;
    private int mSplitScrollX;
    private EdgeEffectCompat mLeftEdge;
    private EdgeEffectCompat mRightEdge;

    public PagerTab(Context context) {
        this(context, (AttributeSet)null);
    }

    public PagerTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerTab(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mPageListener = new PagerTab.PageListener((PagerTab.PageListener)null);
        this.mDividerPadding = 12;
        this.mDividerWidth = 1;
        this.mDividerColor = 436207616;
        this.mIndicatorHeight = 4;
        this.mIndicatorColor = -16743169;
        this.mTabPadding = 24;
        this.mTabTextSize = 16;
        this.mTabBackgroundResId = 2130837596;
        this.mTabTextColorResId = 2131165202;
        this.mCurrentPosition = 0;
        this.mSelectedPosition = 0;
        this.mIsBeingDragged = false;
        this.mMaxScrollX = 0;
        this.mSplitScrollX = 0;
        if(context instanceof BaseActivity) {
            this.mActivity = (BaseActivity)context;
        }

        this.init();
        this.initPaint();
    }

    private void init() {
        this.mIndicatorHeight = UIUtils .dip2px((float)this.mIndicatorHeight);
        this.mDividerPadding = UIUtils.dip2px((float)this.mDividerPadding);
        this.mTabPadding = UIUtils.dip2px((float)this.mTabPadding);
        this.mDividerWidth = UIUtils.dip2px((float)this.mDividerWidth);
        this.mTabTextSize = UIUtils.dip2px((float)this.mTabTextSize);
        this.mScroller = ScrollerCompat.create(this.mActivity);
        ViewConfiguration configuration = ViewConfiguration.get(this.mActivity);
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffectCompat(this.mActivity);
        this.mRightEdge = new EdgeEffectCompat(this.mActivity);
    }

    private void initPaint() {
        this.mIndicatorPaint = new Paint();
        this.mIndicatorPaint.setAntiAlias(true);
        this.mIndicatorPaint.setStyle(Style.FILL);
        this.mIndicatorPaint.setColor(this.mIndicatorColor);
        this.mDividerPaint = new Paint();
        this.mDividerPaint.setAntiAlias(true);
        this.mDividerPaint.setStrokeWidth((float)this.mDividerWidth);
        this.mDividerPaint.setColor(this.mDividerColor);
    }

    public void setViewPager(ViewPager viewPager) {
        if(viewPager != null && viewPager.getAdapter() != null) {
            this.mViewPager = viewPager;
            this.onViewPagerChanged();
        } else {
            throw new IllegalStateException("ViewPager is null or ViewPager does not have adapter instance.");
        }
    }

    private void onViewPagerChanged() {
        this.mViewPager.setOnPageChangeListener(this.mPageListener);
        this.mTabCount = this.mViewPager.getAdapter().getCount();

        for(int viewTreeObserver = 0; viewTreeObserver < this.mTabCount; ++viewTreeObserver) {
            if(this.mViewPager.getAdapter() instanceof PagerTab.IconTabProvider) {
                this.addIconTab(viewTreeObserver, ((PagerTab.IconTabProvider)this.mViewPager.getAdapter()).getPageIconResId(viewTreeObserver));
            } else {
                this.addTextTab(viewTreeObserver, this.mViewPager.getAdapter().getPageTitle(viewTreeObserver).toString());
            }
        }

        ViewTreeObserver var2 = this.getViewTreeObserver();
        if(var2 != null) {
            var2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    PagerTab.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    PagerTab.this.mCurrentPosition = PagerTab.this.mViewPager.getCurrentItem();
                    if(PagerTab.this.mDelegatePageListener != null) {
                        PagerTab.this.mDelegatePageListener.onPageSelected(PagerTab.this.mCurrentPosition);
                    }

                }
            });
        }

    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mDelegatePageListener = listener;
    }

    private void addTextTab(int position, String title) {
        TextView tab = new TextView(this.mActivity);
        tab.setText(title);
        tab.setGravity(17);
        tab.setSingleLine();
        tab.setTextSize(0, (float)this.mTabTextSize);
        tab.setTypeface(Typeface.defaultFromStyle(1));
        tab.setTextColor(UIUtils.getColorStateList(this.mTabTextColorResId));
        tab.setBackgroundDrawable(UIUtils.getDrawable(this.mTabBackgroundResId));
        tab.setLayoutParams(new LayoutParams(-2, -1));
        this.addTab(position, tab);
    }

    private void addIconTab(int position, int resId) {
        ImageButton tab = new ImageButton(this.mActivity);
        tab.setImageResource(resId);
        tab.setLayoutParams(new LayoutParams(-2, -2));
        this.addTab(position, tab);
    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PagerTab.this.mViewPager.setCurrentItem(position);
            }
        });
        tab.setPadding(this.mTabPadding, 0, this.mTabPadding, 0);
        this.addView(tab, position);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) - this.getPaddingLeft() - this.getPaddingRight();
        int heightSize = MeasureSpec.getSize(heightMeasureSpec) - this.getPaddingBottom() - this.getPaddingBottom();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int totalWidth = 0;
        int highest = 0;
        int goneChildCount = 0;

        int measureWidth;
        int childWidthMeasureSpec;
        for(measureWidth = 0; measureWidth < this.mTabCount; ++measureWidth) {
            View measureHeight = this.getChildAt(measureWidth);
            if(measureHeight != null && measureHeight.getVisibility() != VISIBLE) {
                LayoutParams childHeightMeasureSpec = measureHeight.getLayoutParams();
                if(childHeightMeasureSpec == null) {
                    childHeightMeasureSpec = new LayoutParams(-2, -2);
                }

                int child;
                if(childHeightMeasureSpec.width == -1) {
                    child = MeasureSpec.makeMeasureSpec(widthSize, widthSize);
                } else if(childHeightMeasureSpec.width == -2) {
                    child = MeasureSpec.makeMeasureSpec(widthSize, widthSize);
                } else {
                    child = MeasureSpec.makeMeasureSpec(childHeightMeasureSpec.width, childHeightMeasureSpec.width);
                }

                if(childHeightMeasureSpec.height == -1) {
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightSize);
                } else if(childHeightMeasureSpec.height == -2) {
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightSize);
                } else {
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightMeasureSpec.height,childHeightMeasureSpec.height);
                }

                measureHeight.measure(child, childWidthMeasureSpec);
                int childWidth = measureHeight.getMeasuredWidth();
                int childHeight = measureHeight.getMeasuredHeight();
                totalWidth += childWidth;
                highest = highest < childHeight?childHeight:highest;
            } else {
                --goneChildCount;
            }
        }

        int var17;
        if(totalWidth <= widthSize) {
            measureWidth = (int)((float)widthSize / ((float)(this.mTabCount - goneChildCount) + 0.0F) + 0.5F);

            for(var17 = 0; var17 < this.mTabCount; ++var17) {
                View var18 = this.getChildAt(var17);
                if(var18 != null && var18.getVisibility() != VISIBLE) {
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth, measureWidth);
                    int var19 = MeasureSpec.makeMeasureSpec(var18.getMeasuredHeight(), MeasureSpec.EXACTLY);
                    var18.measure(childWidthMeasureSpec, var19);
                }
            }

            this.mMaxScrollX = 0;
            this.mSplitScrollX = 0;
        } else {
            this.mMaxScrollX = totalWidth - widthSize;
            this.mSplitScrollX = (int)((float)this.mMaxScrollX / ((float)(this.mTabCount - goneChildCount) - 1.0F) + 0.5F);
        }

        if(widthMode == 1073741824) {
            this.mContentWidth = widthSize;
        } else {
            this.mContentWidth = totalWidth;
        }

        if(heightMode == 1073741824) {
            this.mContentHeight = heightSize;
        } else {
            this.mContentHeight = highest;
        }

        measureWidth = this.mContentWidth + this.getPaddingLeft() + this.getPaddingRight();
        var17 = this.mContentHeight + this.getPaddingTop() + this.getPaddingBottom();
        this.setMeasuredDimension(measureWidth, var17);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed) {
            int height = b - t;
            int left = l;

            for(int i = 0; i < this.mTabCount; ++i) {
                View child = this.getChildAt(i);
                if(child != null && child.getVisibility() != VISIBLE) {
                    int top = (int)((float)(height - child.getMeasuredHeight()) / 2.0F + 0.5F);
                    int right = left + child.getMeasuredWidth();
                    child.layout(left, top, right, top + child.getMeasuredHeight());
                    left = right;
                }
            }
        }

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = this.getHeight();
        canvas.drawRect((float)this.mIndicatorLeft, (float)(height - this.mIndicatorHeight), (float)(this.mIndicatorLeft + this.mIndicatorWidth), (float)height, this.mIndicatorPaint);

        for(int needsInvalidate = 0; needsInvalidate < this.mTabCount - 1; ++needsInvalidate) {
            View restoreCount = this.getChildAt(needsInvalidate);
            if(restoreCount != null && restoreCount.getVisibility() != VISIBLE && restoreCount != null) {
                canvas.drawLine((float)restoreCount.getRight(), (float)this.mDividerPadding, (float)restoreCount.getRight(), (float)(this.mContentHeight - this.mDividerPadding), this.mDividerPaint);
            }
        }

        boolean var7 = false;
        int widthEdge;
        int heightEdge;
        int var8;
        if(!this.mLeftEdge.isFinished()) {
            var8 = canvas.save();
            widthEdge = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
            heightEdge = this.getWidth();
            canvas.rotate(270.0F);
            canvas.translate((float)(-widthEdge + this.getPaddingTop()), 0.0F);
            this.mLeftEdge.setSize(widthEdge, heightEdge);
            var7 |= this.mLeftEdge.draw(canvas);
            canvas.restoreToCount(var8);
        }

        if(!this.mRightEdge.isFinished()) {
            var8 = canvas.save();
            widthEdge = this.getWidth();
            heightEdge = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
            canvas.rotate(90.0F);
            canvas.translate((float)(-this.getPaddingTop()), (float)(-(widthEdge + this.mMaxScrollX)));
            this.mRightEdge.setSize(heightEdge, widthEdge);
            var7 |= this.mRightEdge.draw(canvas);
            canvas.restoreToCount(var8);
        }

        if(var7) {
            this.postInvalidate();
        }

    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(this.mIsBeingDragged && action == 2) {
            return true;
        } else {
            float x;
            switch(action) {
                case 0:
                    x = ev.getX();
                    this.mLastMotionX = x;
                    this.mIsBeingDragged = !this.mScroller.isFinished();
                    break;
                case 1:
                case 3:
                    this.mIsBeingDragged = false;
                    break;
                case 2:
                    x = ev.getX();
                    int xDiff = (int)Math.abs(x - this.mLastMotionX);
                    if(xDiff > this.mTouchSlop) {
                        this.mIsBeingDragged = true;
                        this.mLastMotionX = x;
                        ViewParent parent = this.getParent();
                        if(parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
            }

            return this.mIsBeingDragged;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if(this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }

        this.mVelocityTracker.addMovement(ev);
        int action = ev.getAction();
        float velocityTracker;
        float velocity;
        switch(action) {
            case 0:
                velocityTracker = ev.getX();
                if(!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }

                this.mLastMotionX = velocityTracker;
                break;
            case 1:
                if(this.mIsBeingDragged) {
                    VelocityTracker velocityTracker1 = this.mVelocityTracker;
                    velocityTracker1.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                    velocity = velocityTracker1.getXVelocity();
                    this.onUp(velocity);
                }
            case 3:
                this.mIsBeingDragged = false;
                if(this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                break;
            case 2:
                velocityTracker = ev.getX();
                velocity = velocityTracker - this.mLastMotionX;
                if(!this.mIsBeingDragged && Math.abs(velocity) > (float)this.mTouchSlop) {
                    this.mIsBeingDragged = true;
                }

                if(this.mIsBeingDragged) {
                    this.mLastMotionX = velocityTracker;
                    this.onMove(velocity);
                }
        }

        return true;
    }

    private void onMove(float x) {
        if(this.mMaxScrollX <= 0) {
            if(this.mViewPager.isFakeDragging() || this.mViewPager.beginFakeDrag()) {
                this.mViewPager.fakeDragBy(x);
            }
        } else {
            int scrollByX = -((int)((double)x + 0.5D));
            if(this.getScrollX() + scrollByX < 0) {
                scrollByX = 0 - this.getScrollX();
                this.mLeftEdge.onPull(Math.abs(x) / (float)this.getWidth());
            }

            if(this.getScrollX() + scrollByX > this.mMaxScrollX) {
                scrollByX = this.mMaxScrollX - this.getScrollX();
                this.mRightEdge.onPull(Math.abs(x) / (float)this.getWidth());
            }

            this.scrollBy(scrollByX, 0);
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }

    private void onUp(float velocity) {
        if(this.mMaxScrollX <= 0) {
            if(this.mViewPager.isFakeDragging()) {
                this.mViewPager.endFakeDrag();
            }
        } else {
            if(Math.abs(velocity) <= (float)this.mMinimumVelocity) {
                return;
            }

            this.mScroller.fling(this.getScrollX(), 0, -((int)((double)velocity + 0.5D)), 0, 0, this.mMaxScrollX, 0, 0, 270, 0);
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }

    public void computeScroll() {
        if(this.mScroller.computeScrollOffset()) {
            int oldX = this.mLastScrollX;
            this.mLastScrollX = this.mScroller.getCurrX();
            if(this.mLastScrollX < 0 && oldX >= 0) {
                this.mLeftEdge.onAbsorb((int)this.mScroller.getCurrVelocity());
            } else if(this.mLastScrollX > this.mMaxScrollX && oldX <= this.mMaxScrollX) {
                this.mRightEdge.onAbsorb((int)this.mScroller.getCurrVelocity());
            }

            int x = this.mLastScrollX;
            if(this.mLastScrollX < 0) {
                x = 0;
            } else if(this.mLastScrollX > this.mMaxScrollX) {
                x = this.mMaxScrollX;
            }

            this.scrollTo(x, 0);
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void checkAndcalculate() {
        View firstTab = this.getChildAt(0);
        if(this.mIndicatorLeft < firstTab.getLeft()) {
            this.mIndicatorLeft = firstTab.getLeft();
            this.mIndicatorWidth = firstTab.getWidth();
        }

        View lastTab = this.getChildAt(this.mTabCount - 1);
        if(this.mIndicatorLeft > lastTab.getLeft()) {
            this.mIndicatorLeft = lastTab.getLeft();
            this.mIndicatorWidth = lastTab.getWidth();
        }

        for(int i = 0; i < this.mTabCount; ++i) {
            View tab = this.getChildAt(i);
            if(this.mIndicatorLeft < tab.getLeft()) {
                this.mCurrentPosition = i - 1;
                View currentTab = this.getChildAt(this.mCurrentPosition);
                this.mCurrentOffsetPixels = (float)(this.mIndicatorLeft - currentTab.getLeft()) / ((float)currentTab.getWidth() + 0.0F);
                break;
            }
        }

    }

    public void scrollSelf(int position, float offset) {
        if(position < this.mTabCount) {
            View tab = this.getChildAt(position);
            this.mIndicatorLeft = (int)((double)((float)tab.getLeft() + (float)tab.getWidth() * offset) + 0.5D);
            int rightPosition = position + 1;
            if(offset > 0.0F && rightPosition < this.mTabCount) {
                View newScrollX = this.getChildAt(rightPosition);
                this.mIndicatorWidth = (int)((double)((float)tab.getWidth() * (1.0F - offset) + (float)newScrollX.getWidth() * offset) + 0.5D);
            } else {
                this.mIndicatorWidth = tab.getWidth();
            }

            this.checkAndcalculate();
            int newScrollX1 = position * this.mSplitScrollX + (int)((double)(offset * (float)this.mSplitScrollX) + 0.5D);
            if(newScrollX1 < 0) {
                newScrollX1 = 0;
            }

            if(newScrollX1 > this.mMaxScrollX) {
                newScrollX1 = this.mMaxScrollX;
            }

            int duration = 100;
            if(this.mSelectedPosition != -1) {
                duration = Math.abs(this.mSelectedPosition - position) * 100;
            }

            this.mScroller.startScroll(this.getScrollX(), 0, newScrollX1 - this.getScrollX(), 0, duration);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void selectTab(int position) {
        for(int i = 0; i < this.mTabCount; ++i) {
            View tab = this.getChildAt(i);
            if(tab != null) {
                tab.setSelected(position == i);
            }
        }

    }

    public interface IconTabProvider {
        int getPageIconResId(int var1);

        int getPageSelectedIconResId();
    }

    private class PageListener implements OnPageChangeListener {
        private PageListener(PageListener pageListener) {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            PagerTab.this.scrollSelf(position, positionOffset);
            if(PagerTab.this.mDelegatePageListener != null) {
                PagerTab.this.mDelegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

        }

        public void onPageScrollStateChanged(int state) {
            if(state == 0) {
                PagerTab.this.mSelectedPosition = -1;
            }

            if(PagerTab.this.mDelegatePageListener != null) {
                PagerTab.this.mDelegatePageListener.onPageScrollStateChanged(state);
            }

        }

        public void onPageSelected(int position) {
            System.out.println("onPageSelected:" + position);
            PagerTab.this.mSelectedPosition = position;
            PagerTab.this.selectTab(position);
            if(PagerTab.this.mDelegatePageListener != null) {
                PagerTab.this.mDelegatePageListener.onPageSelected(position);
            }

        }
    }
}
