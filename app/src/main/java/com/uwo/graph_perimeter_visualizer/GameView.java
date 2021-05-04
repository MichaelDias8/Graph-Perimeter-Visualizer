package com.uwo.graph_perimeter_visualizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.uwo.graph_perimeter_visualizer.FullEdges;
import com.uwo.graph_perimeter_visualizer.GameActivity;
import com.uwo.graph_perimeter_visualizer.GameButton;
import com.uwo.graph_perimeter_visualizer.Graph;
import com.uwo.graph_perimeter_visualizer.Perimeter;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    static int nodeOffset = 105;

    private Thread thread;
    private Random random;

    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private GameActivity activity;
    private SharedPreferences prefs;
    private Paint perimeterPaint;
    private Paint buttonPaint1;
    private Paint buttonPaintToggle;

    private Bitmap background;
    private Graph graph;
    private Perimeter perimeter;
    private FullEdges fullEdgesView;

    private GameButton addNodeButton;
    private GameButton removeNodeButton;
    private GameButton toggleFullEdges;
    private GameButton togglePerimeter;


    private boolean isPlaying = false;
    private Node selectedNode = null;


    public GameView(GameActivity activity, int screenX, int screenY){
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 3200f / screenX;
        screenRatioY = 1440f / screenY;

        Paint buttonPaint1 = new Paint();
        buttonPaint1.setTextSize(64);
        buttonPaint1.setTextAlign(Paint.Align.CENTER);
        int[] buttonColors1 = {Color.WHITE};

        addNodeButton = new GameButton(50, 2000, 465, 120, "ADD NODE", buttonPaint1, 7, 15, buttonColors1);
        removeNodeButton = new GameButton(565, 2000, 465, 120, "REMOVE NODE", buttonPaint1, 7, 15, buttonColors1);

        Paint buttonPaintToggle = new Paint();
        buttonPaintToggle.setTextSize(64);
        buttonPaintToggle.setTextAlign(Paint.Align.CENTER);
        int[] buttonColorsToggle = {Color.GREEN, Color.RED};

        togglePerimeter = new GameButton(100, 100, 500, 100, "PERIMETER", buttonPaintToggle, 5, 10, buttonColorsToggle);
        toggleFullEdges = new GameButton(100, 250, 500, 100, "FULL EDGES", buttonPaintToggle, 5, 10, buttonColorsToggle);
        toggleFullEdges.cycleColors();


        perimeterPaint = new Paint();
        perimeterPaint.setStrokeWidth(5);

        graph = new Graph();
        perimeter = new Perimeter();
        fullEdgesView = new FullEdges(graph.getNodes());

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

    }

    public void resume () {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        graph.addNode();
        graph.addNode();
        graph.addNode();
        graph.addNode();

        while(isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void update(){
        //update perimeter
        perimeter.calculate(graph.getNodes());
    }

    private void draw(){
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            //draw background
            canvas.drawBitmap(background, 0, 0, null);

            //draw buttons
            addNodeButton.drawButton(canvas);
            removeNodeButton.drawButton(canvas);
            togglePerimeter.drawButton(canvas);
            toggleFullEdges.drawButton(canvas);

            //draw perimeter
            perimeterPaint.setColor(Color.WHITE);
            perimeter.draw(canvas, perimeterPaint);

            //draw fullEdgesView
            fullEdgesView.draw(canvas, perimeterPaint);

            perimeterPaint.setColor(Color.BLACK);
            perimeterPaint.setStyle(Paint.Style.STROKE);
            //draw nodes
            for(int i = 0; i < graph.getNodes().size(); i++) {
                canvas.drawCircle(graph.getNodes().get(i).x, graph.getNodes().get(i).y, 65, perimeterPaint);
                perimeterPaint.setTextSize(40);
                canvas.drawText(String.valueOf(graph.getNodes().get(i).id), graph.getNodes().get(i).x, graph.getNodes().get(i).y + 100, perimeterPaint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //check if any buttons pressed
                if(addNodeButton.buttonTouched(event)){
                    graph.addNode();
                }
                if(removeNodeButton.buttonTouched(event)){
                    graph.removeNode();
                }
                if(toggleFullEdges.buttonTouched(event)){
                    toggleFullEdges.cycleColors();
                    if (fullEdgesView.on)
                        fullEdgesView.on = false;
                    else
                        fullEdgesView.on = true;
                }
                if(togglePerimeter.buttonTouched(event)){
                    togglePerimeter.cycleColors();
                    if (perimeter.on)
                        perimeter.on = false;
                    else
                        perimeter.on = true;
                }
                //check if a node has been selected
                for (int i = 0; i < graph.getNodes().size(); i++) {
                    if (Math.pow(event.getX() - graph.getNodes().get(i).x , 2) + Math.pow(event.getY() - graph.getNodes().get(i).y, 2) < Math.pow(70, 2)) {
                        selectedNode = graph.getNodes().get(i);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < graph.getNodes().size(); i++) {
                    selectedNode = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(selectedNode == null){
                    ;
                }else {
                    selectedNode.x = (int) event.getX();
                    selectedNode.y = (int) event.getY();
                }
        }
        return true;
    }
}

