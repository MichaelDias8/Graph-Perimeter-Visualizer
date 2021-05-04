package com.uwo.graph_perimeter_visualizer;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameButton {
    int x, y, length, height, textStrokeWidth, rectStrokeWidth;
    String text;
    Paint paint;
    int[] colors;
    int selectedColor = 0;

    public GameButton(int x, int y, int length, int height, String text, Paint paint, int textStrokeWidth, int rectStrokeWidth, int[] colors){
        this.x = x;
        this.y = y;
        this.length = length;
        this.height = height;
        this.text = text;
        this.paint = paint;
        this.textStrokeWidth = textStrokeWidth;
        this.rectStrokeWidth = rectStrokeWidth;
        this.colors = colors;
    }

    @SuppressLint("NewApi")
    public void drawButton(Canvas canvas){
        //draw text
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(textStrokeWidth);
        paint.setColor(Color.BLACK);
        canvas.drawText(text, x + length/2, y + height/2 + 25, paint);

        //draw rect
        paint.setColor(colors[selectedColor]);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(rectStrokeWidth);
        canvas.drawRoundRect(x, y, x + length, y + height, 10, 10, paint);

    }

    public boolean buttonTouched(MotionEvent event){
        return (event.getX() > x && event.getX() < x+length && event.getY() > y && event.getY() < y+height);
    }

    public void cycleColors(){
        if(selectedColor == colors.length - 1)
            selectedColor = 0;
        else
            selectedColor++;
    }

}
