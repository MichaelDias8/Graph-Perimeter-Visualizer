package com.uwo.graph_perimeter_visualizer;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class FullEdges {
    public boolean on;
    ArrayList<Node> nodes;

    public FullEdges(ArrayList<Node> nodes){
        this.nodes = nodes;
        on = false;
    }

    public void draw(Canvas canvas, Paint paint){
        if(on) {
            for (int i = 0; i < nodes.size(); i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    canvas.drawLine(nodes.get(i).x, nodes.get(i).y, nodes.get(j).x, nodes.get(j).y, paint);
                }
            }
        }
    }
}
