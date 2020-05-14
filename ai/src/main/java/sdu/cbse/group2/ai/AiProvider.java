package sdu.cbse.group2.ai;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.Tile;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.AiSPI;

import java.util.*;
import java.util.stream.Collectors;

public class AiProvider implements AiSPI {

    private final AStarAlgorithm aStar = new AStarAlgorithm();
    private Node[][] nodes;

    private Optional<Node> getTarget(Entity entity, World world, int searchRadius) {
        final Tile[][] tiles = world.getTiles();
        int rows = tiles.length;
        int cols = tiles[0].length;

        if (nodes == null) nodes = new Node[rows][cols];

        // FIXME: 28-04-2020 Take searchRadius into account.

        List<Node> goalList = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Tile tile = tiles[r][c];
                final PositionPart positionPart = tile.getPositionPart();
                Node node = new Node(Math.round(positionPart.getX() / Tile.LENGTH), Math.round(positionPart.getY() / Tile.LENGTH));
                node.setObstructed(tile.isObstructing());
                if (r == 0 || c == cols - 1 || c == 0 || r == rows - 1) {
                    node.setObstructed(true);
                }
//                if (node.isObstructed()) {
//                    AiDrawer.getDrawSPI().drawCircle(Math.round(positionPart.getX()),Math.round(positionPart.getY()), 10);
//                }
                //TODO Head goal. Safe Tile goal.
                nodes[r][c] = node;
            }
        }
        //Quad loop to set how close each node is to an obstructing entity and to the entity
        Node entityNode = getNearestNode((int)((PositionPart)entity.getPart(PositionPart.class)).getX(), (int)(((PositionPart) entity.getPart(PositionPart.class)).getY()));
        final PositionPart entityPositionPart = entity.getPart(PositionPart.class);
        for (int r = entityNode.getRow() - 5; r < ; entityNode.getRow()++) {
            for (int c = 0; c < cols; c++) {
                //If it is obstructed it is not a potential goal tile, and we can skip it
                if (!nodes[r][c].isObstructed()) {
                    //For each non-obstructing node we need to check the distance to each other obstructing node and set the lowest one as closestObstructing
                    double penalty = 0;
                    double closestDistanceToObstructing = Double.MAX_VALUE;
                    PositionPart mainTilePosPart = tiles[r][c].getPositionPart();;
                    for (int checkR = 0; checkR < rows; checkR++) {
                        for (int checkC = 0; checkC < cols; checkC++) {
                            //We want to find the closest obstructing, so if it isn't obstructing, we can skip it
                            if (nodes[checkR][checkC].isObstructed()) {
                                PositionPart checkTilePosPart = tiles[checkR][checkC].getPositionPart();
                                //Finding the distance by calculating the length of the hypotenuse of the triangle formed by the differences in x and y using pythagorean theorem (a^2 + b^2 = c^2)
                                double distanceToObstructing = Math.sqrt(Math.pow(mainTilePosPart.getX() - checkTilePosPart.getX(), 2) + Math.pow(mainTilePosPart.getY() - checkTilePosPart.getY(), 2));
                                if(distanceToObstructing < closestDistanceToObstructing){
                                    closestDistanceToObstructing = distanceToObstructing;
                                }
                            }
                        }
                    }

                    double distanceToCurrentLocation = Math.sqrt(Math.pow(mainTilePosPart.getX() - entityPositionPart.getX(), 2) + Math.pow(mainTilePosPart.getY() - entityPositionPart.getY(), 2));
                    double bestDistanceCost = (distanceToCurrentLocation - 1.10 * closestDistanceToObstructing) + penalty;

                    nodes[r][c].setBestDistanceCost(bestDistanceCost);
                    //Now calculate the heuristic so we can later compare on this to find the best target goal
                    nodes[r][c].calculateBestDistanceCost(!tiles[r][c].getEntities().isEmpty() && tiles[r][c].getEntities().stream().noneMatch(Entity::isObstructing));
                    //Add this potential goal to the goalList
                    goalList.add(nodes[r][c]);

                }
            }
        }

            Node targetNode = goalList.stream().min(Comparator.comparingDouble(Node::getBestDistanceCost)).orElse(goalList.get(0));
            aStar.setSearchSpace(nodes);
            final Node currentPosition = new Node(Math.round(entityPositionPart.getX() / Tile.LENGTH), Math.round(entityPositionPart.getY() / Tile.LENGTH));
            aStar.setStart(currentPosition);
            aStar.setTarget(targetNode);
            final List<Node> path = aStar.computePath();
            path.forEach(node -> AiDrawer.getDrawSPI().drawCircle(node.getRow() * Tile.LENGTH, node.getCol() * Tile.LENGTH, 3));
            if (!path.isEmpty()) {
                Node target;
                int i = 0;
                do {
                    target = path.get(i++);
                } while (currentPosition.equals(target) && i < path.size());
                return Optional.of(path.get(Math.min(path.size() - 1, i + 1)));
            }

        return Optional.empty();
    }

    @Override
    public void move(Entity entity, World world, int searchRadius) {
        getTarget(entity, world, searchRadius).ifPresent(target -> {
            final Vector targetVector = new Vector(target.getRow() * Tile.LENGTH, target.getCol() * Tile.LENGTH);
            final PositionPart part = entity.getPart(PositionPart.class);
            final Vector startVector = new Vector(part.getX(), part.getY());
            final Vector inBetween = targetVector.subtract(startVector);
            final Vector direction = new Vector(Math.cos(part.getRadians()) * -1, Math.sin(part.getRadians()) * -1);
            final double cross = inBetween.cross(direction);
            final MovingPart movingPart = entity.getPart(MovingPart.class);
            movingPart.setRight(cross > 0);
            movingPart.setLeft(cross <= 0);
        });
    }

    public Node getNearestNode(int x, int y) {
        try {
            return nodes[Math.max(0, Math.round((float) x / ((float) Tile.LENGTH)))][Math.max(0, Math.round(((float) y / ((float) Tile.LENGTH))))];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
