package com.uwo.graph_perimeter_visualizer;

public class Node {

    int x, y, id;
    boolean selected = false;

    public Node (int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
