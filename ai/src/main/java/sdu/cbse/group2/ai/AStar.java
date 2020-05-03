package sdu.cbse.group2.ai;

import java.util.*;

public class AStar {

    public static final int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
    public static final int DEFAULT_DIAGONAL_COST = 14;

    private Node[][] searchArea;
    private PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getTotalCost));
    private Set<Node> closedSet = new HashSet<>();
    private Node startNode;
    private Node targetNode;

//    private void setNodes(int rows, int cols) {
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                Node node = new Node(i, j);
//                if (searchArea[i][j].isObstructed()) {
//                    node.setObstructed(true);
//                }
//                node.calculateHeuristic(getTargetNode());
//                searchArea[i][j] = node;
//            }
//        }
//    }

    public List<Node> findPath() {
        openList.add(startNode);
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<>();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int lowerRow = row + 1;
        if (lowerRow < getSearchArea().length) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, lowerRow, DEFAULT_DIAGONAL_COST); // Comment this line if diagonal movements are not allowed
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, lowerRow, DEFAULT_DIAGONAL_COST); // Comment this line if diagonal movements are not allowed
            }
            checkNode(currentNode, col, lowerRow, DEFAULT_HV_COST);
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, middleRow, DEFAULT_HV_COST);
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 1, middleRow, DEFAULT_HV_COST);
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, upperRow, DEFAULT_DIAGONAL_COST); // Comment this if diagonal movements are not allowed
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, upperRow, DEFAULT_DIAGONAL_COST); // Comment this if diagonal movements are not allowed
            }
            checkNode(currentNode, col, upperRow, DEFAULT_HV_COST);
        }
    }

    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node adjacentNode = getSearchArea()[row][col];
        if (!adjacentNode.isObstructed() && !getClosedSet().contains(adjacentNode)) {
            if (!openList.contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                openList.add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    openList.remove(adjacentNode);
                    openList.add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(targetNode);
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.isEmpty();
    }

    private void setObstructed(int row, int col) {
        searchArea[row][col].setObstructed(true);
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setInitialPosition(int x, int y) {
        Node initNode = new Node(x, y);
        setStartNode(initNode);
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }

    public Node[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }
}