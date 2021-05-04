package com.uwo.graph_perimeter_visualizer;

import java.util.ArrayList;
import java.util.Random;

public class Graph {
    int nextNodeId;
    private ArrayList<Node> nodes;
    private Random random;

    public Graph (){
        nodes = new ArrayList<Node>();
        random = new Random();
    }

    public boolean addNode(){
        if(nodes.size() < 20) {
            //add new node
            nextNodeId++;
            Node newNode = new Node(50 + random.nextInt(980), 250 + random.nextInt(1700), nextNodeId);
            nodes.add(newNode);
            return true;
        }
        else{
            //Display "can not add more than 10 nodes"
            return false;
        }
    }

    public void removeNode(){
        if(nextNodeId != 0)
        nodes.remove(--nextNodeId);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
