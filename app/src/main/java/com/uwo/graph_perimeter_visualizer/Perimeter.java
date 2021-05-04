package com.uwo.graph_perimeter_visualizer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Stack;

public class Perimeter {

    public boolean on;
    Stack<Node> path;

    public Perimeter(){
        path = new Stack<Node>();
        on = true;
    }

    //returns true if can calculate. false otherwise
    public void calculate(ArrayList<Node> nodes) {
        if (on) {
            path.clear();
            if (nodes.size() > 2) {
                Node startingPoint = getLowestNode(nodes);
                path.push(startingPoint);
                Node prevNode = startingPoint;

                while (true) {
                    Node candidate = null;
                    for (Node node : nodes) {
                        if (node == prevNode) continue;
                        if (candidate == null) {
                            candidate = node;
                            continue;
                        }
                        int ccw = GraphUtils.ccw(new Point(prevNode.x, prevNode.y), new Point(candidate.x, candidate.y), new Point(node.x, node.y));
                        if (ccw == 0 && GraphUtils.dist(new Point(prevNode.x, prevNode.y), new Point(candidate.x, candidate.y)) < GraphUtils.dist(new Point(prevNode.x, prevNode.y), new Point(node.x, node.y))) {
                            candidate = node;
                        } else if (ccw < 0) {
                            candidate = node;
                        }
                    }
                    if (candidate == startingPoint) break;

                    path.push(candidate);
                    prevNode = candidate;
                }
                path.push(startingPoint);
            }
        }
    }

    public Node getLowestNode(ArrayList<Node> nodes){
        if (nodes.size() == 0) return null;
        else{
            Node lowestNode = nodes.get(0);
            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i).y > lowestNode.y)
                    lowestNode = nodes.get(i);
            }
            return lowestNode;
        }
    }

    public void draw(Canvas canvas, Paint paint){
        if(on) {
            if (!path.empty()) {
                Stack<Node> unloadStack = new Stack<Node>();
                Node node1 = path.pop();
                while (path.size() > 0) {
                    unloadStack.push(node1);
                    Node node2 = path.pop();

                    canvas.drawLine(node1.x, node1.y, node2.x, node2.y, paint);
                    node1 = node2;
                }
                path.push(node1);
                while (unloadStack.size() > 0)
                    path.push(unloadStack.pop());

            }
        }
    }
}



