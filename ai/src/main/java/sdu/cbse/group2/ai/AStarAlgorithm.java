package sdu.cbse.group2.ai;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;

@Accessors(chain = true)
class AStarAlgorithm {

    private final PriorityQueue<Node> fringe = new PriorityQueue<>(Comparator.comparingInt(Node::getTotalCost));
    private final Set<Node> exploredNodes = new HashSet<>();
    @Setter
    private Node[][] searchSpace;
    @Setter
    private Node start, target;

    List<Node> computePath() {
        fringe.add(start);
        while (!fringe.isEmpty()) {
            Node current = fringe.poll();
            exploredNodes.add(current);
            if (current.equals(target)) {
                return getPath(current);
            } else addAdjacent(current);
        }
        return new ArrayList<>();
    }

    private List<Node> getPath(Node node) {
        List<Node> path = new ArrayList<>(Collections.singletonList(node));
        Node parent;
        while ((parent = node.getParent()) != null) {
            path.add(0, node = parent);
        }
        return path;
    }

    private void addAdjacent(Node node) {
        final int col = node.getCol();
        final int row = node.getRow();

        // Upper
        if (row >= 0) {
            checkNode(node, col, row - 1);
        }

        // Middle
        if (col - 1 >= 0) {
            checkNode(node, col - 1, row);
        }
        if (col + 1 < searchSpace[0].length) {
            checkNode(node, col + 1, row);
        }

        // Lower
        if (row < searchSpace.length) {
            checkNode(node, col, row + 1);
        }
    }


    private void checkNode(Node node, int col, int row) {
        try {
            Optional.of(searchSpace[row][col]).filter(adjacent -> !adjacent.isObstructed() && !exploredNodes.contains(adjacent)).ifPresent(adjacent -> {
                final int horizontalVerticalCost = 10;
                if (!fringe.contains(adjacent)) {
                    adjacent.setNodeData(node, horizontalVerticalCost);
                    fringe.add(adjacent);
                } else if (adjacent.checkBetterPath(node, horizontalVerticalCost)) {
                    fringe.remove(adjacent);
                    fringe.add(adjacent);
                }
            });
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }
}