package com.supcon.mes.middleware.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.supcon.mes.mbap.utils.ViewUtil;

/**
 * Created by Xushiyun on 2018/5/24.
 * Email:ciruy_victory@gmail.com
 */

public class PinyinSearchBar extends View {
    /*绘制的列表导航字母*/
    private String words[] = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    /*字母画笔*/
    private Paint wordsPaint;
    /*字母背景画笔*/
    private Paint bgPaint;
    /*每一个字母的宽度*/
    private int itemWidth;
    /*每一个字母的高度*/
    private int itemHeight;
    /*手指按下的字母索引*/
    private int touchIndex = 0;
    /*手指按下的字母改变接口*/
    private OnWordsChangeListener listener;

    public PinyinSearchBar(Context context) {
        this(context, null);
    }

    public PinyinSearchBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinyinSearchBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //得到画布的宽度和每一个字母所占的高度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        //使得边距好看一些
        int height = getMeasuredHeight();
        itemHeight = height / 27;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (wordsPaint == null) {
            wordsPaint = new Paint();
            wordsPaint.setAntiAlias(true);
        }
        if (bgPaint == null) {
            bgPaint = new Paint();
            bgPaint.setAntiAlias(true);
        }
        for (int i = 0; i < words.length; i++) {
            //判断是不是我们按下的当前字母
//            if (touchIndex == i) {
//                //绘制文字圆形背景
//                canvas.drawCircle(itemWidth / 2, itemHeight / 2 + i * itemHeight, 23, bgPaint);
//                wordsPaint.setColor(Color.WHITE);
//            } else {
            wordsPaint.setColor(Color.GRAY);
//            }
            //获取文字的宽高
            Rect rect = new Rect();
            wordsPaint.setTextSize(ViewUtil.dpToPx(getContext(), 10));
            wordsPaint.getTextBounds(words[i], 0, 1, rect);
            int wordWidth = rect.width();
            //绘制字母
            float wordX = itemWidth / 2 - wordWidth / 2;
            float wordY = i * itemHeight + itemHeight / 2 + wordWidth / 2;
            canvas.drawText(words[i], wordX, wordY, wordsPaint);
        }
    }

    /**
     * 当手指触摸按下的时候改变字母背景颜色
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                //关键点===获得我们按下的是那个索引(字母)
                int index = (int) (y / itemHeight);
                if (index != touchIndex)
                    touchIndex = index;
                //防止数组越界
                if (listener != null && 0 <= touchIndex && touchIndex <= words.length - 1) {
                    //回调按下的字母
                    listener.wordsChange(words[touchIndex]);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起,不做任何操作
                break;
            default:
        }
        return true;
    }

    /*手指按下了哪个字母的回调接口*/
    public interface OnWordsChangeListener {
        void wordsChange(String words);
    }

    /*设置手指按下字母改变监听*/
    public void setOnWordsChangeListener(OnWordsChangeListener listener) {
        this.listener = listener;
    }
}
