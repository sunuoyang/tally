package com.mnnyang.tallybook.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mnnyang.tallybook.utils.ScreenUtils;

/**
 * Created by mnnyang on 17-5-16.
 */

public class NumberInputView extends View {

    private Paint buttonPaint;
    private int buttonNormalColor = Color.WHITE;
    private int width;
    private int height;
    private int buttonWidth;//单个按钮宽度
    private int buttonHeight;//单个按钮高度

    private int gap = 1;//按钮间距

    private int downCoordY;
    private int downCoordX;
    private int upCoordY;
    private int upCoordX;

    private static final int STATE_DOWN = 1, STATE_UP = 2, STATE_CANCEL = 3, STATE_NONE = 4;//状态
    private int currentState = STATE_NONE;

    private final char[][] text = {
            {'\0', '\0', '\0', '\0'},
            {'\0', '1', '2', '3'},
            {'\0', '4', '5', '6'},
            {'\0', '7', '8', '9'},
            {'\0', '.', '0', '<'}};

    //简便起见, 从11坐标开始使用
    private float[][] btc = new float[5][4];
    private float[][] blc = new float[5][4];
    private float[][] txc = new float[5][4];
    private float[][] tyc = new float[5][4];

    private RectF rectF[] = new RectF[12];

    private boolean isInit;
    private final String DELETE = "onDelete";
    private int buttonSelectColor = 0xffdddddd;
    private int textColor = 0xff777777;
    private int buttonRadius = 0;


    public NumberInputView(Context context) {
        super(context);
        init();
    }

    public NumberInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        buttonPaint = new Paint();
        buttonPaint.setAntiAlias(true);
        buttonPaint.setDither(true);
        buttonPaint.setColor(buttonNormalColor);
        buttonPaint.setTextSize(ScreenUtils.sp2px(28));
        buttonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        buttonPaint.setTextAlign(Paint.Align.CENTER);

        Typeface font = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
        buttonPaint.setTypeface(font);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isInit) {
            return;
        }
        isInit = true;
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        dimensioning();
    }

    //尺寸坐标计算
    private void dimensioning() {
        buttonWidth = ((width - gap * 2) / 3);
        buttonHeight = ((height - gap * 3) / 4);

        //按钮坐标计算
        blc[1][1] = blc[2][1] = blc[3][1] = blc[4][1] = buttonWidth * 0 + gap * 0;
        blc[1][2] = blc[4][2] = blc[3][2] = blc[2][2] = buttonWidth * 1 + gap * 1;
        blc[1][3] = blc[2][3] = blc[3][3] = blc[4][3] = buttonWidth * 2 + gap * 2;

        btc[1][1] = btc[1][2] = btc[1][3] = 0;
        btc[2][1] = btc[2][2] = btc[2][3] = gap * 1 + buttonHeight * 1;
        btc[3][1] = btc[3][2] = btc[3][3] = gap * 2 + buttonHeight * 2;
        btc[4][1] = btc[4][2] = btc[4][3] = gap * 3 + buttonHeight * 3;

        //文字坐标计算
        Paint.FontMetrics fontMetrics = buttonPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (height / 8 - top / 2 - bottom / 2);

        txc[1][1] = txc[2][1] = txc[3][1] = txc[4][1] = buttonWidth * 0 + buttonWidth / 2;
        txc[1][2] = txc[2][2] = txc[3][2] = txc[4][2] = buttonWidth * 1 + buttonWidth / 2;
        txc[1][3] = txc[2][3] = txc[3][3] = txc[4][3] = buttonWidth * 2 + buttonWidth / 2;

        tyc[1][1] = tyc[1][2] = tyc[1][3] = baseLineY;
        tyc[2][1] = tyc[2][2] = tyc[2][3] = baseLineY + 1 * gap + buttonHeight * 1;
        tyc[3][1] = tyc[3][2] = tyc[3][3] = baseLineY + 2 * gap + buttonHeight * 2;
        tyc[4][1] = tyc[4][2] = tyc[4][3] = baseLineY + 3 * gap + buttonHeight * 3;

        rectF[0] = new RectF(blc[1][1], btc[1][1], blc[1][1] + buttonWidth, btc[1][1] + buttonHeight);
        rectF[1] = new RectF(blc[1][2], btc[1][2], blc[1][2] + buttonWidth, btc[1][2] + buttonHeight);
        rectF[2] = new RectF(blc[1][3], btc[1][3], blc[1][3] + buttonWidth, btc[1][3] + buttonHeight);
        rectF[3] = new RectF(blc[2][1], btc[2][1], blc[2][1] + buttonWidth, btc[2][1] + buttonHeight);
        rectF[4] = new RectF(blc[2][2], btc[2][2], blc[2][2] + buttonWidth, btc[2][2] + buttonHeight);
        rectF[5] = new RectF(blc[2][3], btc[2][3], blc[2][3] + buttonWidth, btc[2][3] + buttonHeight);
        rectF[6] = new RectF(blc[3][1], btc[3][1], blc[3][1] + buttonWidth, btc[3][1] + buttonHeight);
        rectF[7] = new RectF(blc[3][2], btc[3][2], blc[3][2] + buttonWidth, btc[3][2] + buttonHeight);
        rectF[8] = new RectF(blc[3][3], btc[3][3], blc[3][3] + buttonWidth, btc[3][3] + buttonHeight);
        rectF[9] = new RectF(blc[4][1], btc[4][1], blc[4][1] + buttonWidth, btc[4][1] + buttonHeight);
        rectF[10] = new RectF(blc[4][2], btc[4][2], blc[4][2] + buttonWidth, btc[4][2] + buttonHeight);
        rectF[11] = new RectF(blc[4][3], btc[4][3], blc[4][3] + buttonWidth, btc[4][3] + buttonHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawKeyboard(canvas);
        if (currentState == STATE_DOWN) {
            buttonPaint.setColor(buttonSelectColor);
            /*drawRect(canvas, new RectF(blc[downCoordY][downCoordX], btc[downCoordY][downCoordX],
                    blc[downCoordY][downCoordX] + buttonWidth, btc[downCoordY][downCoordX] + buttonHeight));*/
            switch (text[downCoordY][downCoordX]) {
                case '1':
                    drawRect(canvas, rectF[0]);
                    break;
                case '2':
                    drawRect(canvas, rectF[1]);
                    break;
                case '3':
                    drawRect(canvas, rectF[2]);
                    break;
                case '4':
                    drawRect(canvas, rectF[3]);
                    break;
                case '5':
                    drawRect(canvas, rectF[4]);
                    break;
                case '6':
                    drawRect(canvas, rectF[5]);
                    break;
                case '7':
                    drawRect(canvas, rectF[6]);
                    break;
                case '8':
                    drawRect(canvas, rectF[7]);
                    break;
                case '9':
                    drawRect(canvas, rectF[8]);
                    break;
                case '.':
                    drawRect(canvas, rectF[9]);
                    break;
                case '0':
                    drawRect(canvas, rectF[10]);
                    break;
                case '<':
                    drawRect(canvas, rectF[11]);
                    break;

            }


            buttonPaint.setColor(textColor);
            canvas.drawText(String.valueOf(text[downCoordY][downCoordX]),
                    txc[downCoordY][downCoordX], tyc[downCoordY][downCoordX], buttonPaint);
            return;
        }

        if (currentState == STATE_UP) {
            if (downCoordX == upCoordX && downCoordY == upCoordY) {
                char clickText = text[upCoordY][upCoordX];
                Log.i("NumberInputView", clickText + "");
                if (keyboardListener != null) {
                    if (clickText == '<') {
                        keyboardListener.onNumberDelete();
                    } else {
                        keyboardListener.onNumber(clickText + "");
                    }
                }
            }
            return;
        }

        if (currentState == STATE_CANCEL) {

        }

        clearState();

    }

    private void clearState() {
        currentState = STATE_NONE;
        downCoordX = downCoordY = 0;
    }

    private void drawKeyboard(Canvas canvas) {
        buttonPaint.setColor(buttonNormalColor);
        for (int i = 0; i < rectF.length; i++) {
            drawRect(canvas, rectF[i]);
        }


        /**绘制文字*/
        buttonPaint.setColor(textColor);
        canvas.drawText("1", txc[1][1], tyc[1][1], buttonPaint);
        canvas.drawText("2", txc[1][2], tyc[1][2], buttonPaint);
        canvas.drawText("3", txc[1][3], tyc[1][3], buttonPaint);

        canvas.drawText("4", txc[2][1], tyc[2][1], buttonPaint);
        canvas.drawText("5", txc[2][2], tyc[2][2], buttonPaint);
        canvas.drawText("6", txc[2][3], tyc[2][3], buttonPaint);

        canvas.drawText("7", txc[3][1], tyc[3][1], buttonPaint);
        canvas.drawText("8", txc[3][2], tyc[3][2], buttonPaint);
        canvas.drawText("9", txc[3][3], tyc[3][3], buttonPaint);

        canvas.drawText(".", txc[4][1], tyc[4][1], buttonPaint);
        canvas.drawText("0", txc[4][2], tyc[4][2], buttonPaint);
        canvas.drawText("<", txc[4][3], tyc[4][3], buttonPaint);
    }

    private void drawRect(Canvas canvas, RectF rect) {
        canvas.drawRoundRect(rect, buttonRadius, buttonRadius, buttonPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downEvent(x, y);
                return true;
            case MotionEvent.ACTION_UP:
                upEvent(x, y);
                return true;
            case MotionEvent.ACTION_CANCEL:
                cancelEvent();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void cancelEvent() {
        currentState = STATE_CANCEL;
        invalidate();
    }

    private void upEvent(float x, float y) {
        if (x <= buttonWidth) {
            upCoordX = 1;
        } else if (x <= buttonWidth * 2 + gap) {
            upCoordX = 2;
        } else {
            upCoordX = 3;
        }

        if (y <= buttonHeight) {
            upCoordY = 1;
        } else if (y <= buttonHeight * 2 + gap) {
            upCoordY = 2;
        } else if (y <= buttonHeight * 3 + gap * 2) {
            upCoordY = 3;
        } else {
            upCoordY = 4;
        }
        currentState = STATE_UP;
        invalidate();
    }


    private void downEvent(float x, float y) {
        if (x <= buttonWidth) {
            downCoordX = 1;
        } else if (x <= buttonWidth * 2 + gap) {
            downCoordX = 2;
        } else {
            downCoordX = 3;
        }

        if (y <= buttonHeight) {
            downCoordY = 1;
        } else if (y <= buttonHeight * 2 + gap) {
            downCoordY = 2;
        } else if (y <= buttonHeight * 3 + gap * 2) {
            downCoordY = 3;
        } else {
            downCoordY = 4;
        }

        currentState = STATE_DOWN;
        invalidate();
    }


    public NumberInputView setKeyboardListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
        return this;
    }

    KeyboardListener keyboardListener;

    public interface KeyboardListener {
        //回调点击的数字
        void onNumber(String number);

        //删除键的回调
        void onNumberDelete();
    }
}
