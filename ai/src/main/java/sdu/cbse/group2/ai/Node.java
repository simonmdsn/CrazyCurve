package sdu.cbse.group2.ai;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

import java.util.Comparator;

@Getter
@Setter
@RequiredArgsConstructor
public class Node {

    private final int row;
    private final int col;
    private int pathCost;
    private int heuristicCost;
    private int totalCost;
    private boolean obstructed;
    private Node parent;
    private double bestDistanceCost;

    public void calculateBestDistanceCost(boolean hasPowerup) {
        if(!hasPowerup) {
            bestDistanceCost += 500;
        }
    }


    public void setNodeData(Node currentNode, int cost) {
        int pathCost = currentNode.pathCost + cost;
        setParent(currentNode);
        setPathCost(pathCost);
        calculateFinalCost();
    }

    public boolean checkBetterPath(Node currentNode, int cost) {
        int gCost = currentNode.pathCost + cost;
        if (gCost < pathCost) {
            setNodeData(currentNode, cost);
        }
        return gCost < pathCost;
    }

    private void calculateFinalCost() {
        int finalCost = pathCost + getHeuristicCost();
        setTotalCost(finalCost);
    }

    @Override
    public boolean equals(Object o) {
        Node other = (Node) o;
        return row == other.row && col == other.col;
    }

    @Override
    public String toString() {
        return "Node [row=" + row + ", col=" + col + "]";
    }

}