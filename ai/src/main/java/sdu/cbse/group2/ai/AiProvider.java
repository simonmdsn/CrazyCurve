package sdu.cbse.group2.ai;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.Tile;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.AiSPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AiProvider implements AiSPI {

    private final AStar aStar = new AStar();
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
                Node node = new Node((int) positionPart.getX() / Tile.LENGTH, (int) positionPart.getY() / Tile.LENGTH);
                node.setObstructed(tile.isObstructing());
                if (!tile.getEntities().isEmpty() && tile.getEntities().stream().noneMatch(Entity::isObstructing)) { // Contains power-up.
                    goalList.add(node);
                }
                //TODO Head goal. Safe Tile goal.
                nodes[r][c] = node;
            }
        }

        if (!goalList.isEmpty()) {
            Node targetNode = goalList.get(0); //TODO Sort by closest coal/heuristic.
            aStar.setSearchArea(nodes);
            final PositionPart positionPart = entity.getPart(PositionPart.class);
            aStar.setStartNode(new Node((int) positionPart.getX() / Tile.LENGTH, (int) positionPart.getY() / Tile.LENGTH));
            aStar.setTargetNode(targetNode);
            final List<Node> path = aStar.findPath();
            return Optional.of(path.get(path.size() - 1));
        } else return Optional.empty();
    }

    @Override
    public void move(Entity entity, World world, int searchRadius) {
        getTarget(entity, world, searchRadius).ifPresent(target -> {
            final Vector targetVector = new Vector(target.getRow() * Tile.LENGTH, target.getCol() * Tile.LENGTH);
            final PositionPart part = entity.getPart(PositionPart.class);
            final Vector startVector = new Vector(part.getX(), part.getY());
            final Vector inBetween = targetVector.subtract(startVector);
            final Vector forward = startVector.getDirection(part.getRadians());
            final MovingPart movingPart = entity.getPart(MovingPart.class);
            final double v = inBetween.angle(forward);
            System.out.println(inBetween.angle(forward));
            if (v > Math.PI / 2) {
                (movingPart).setRight(true);
            }else (movingPart).setLeft(true);

//            double radians = Math.atan2(targetVector.getX() - startVector.getX(), targetVector.getY() - startVector.getY());
//            System.out.println("before " + radians);
//            System.out.println(startVector + "   " + targetVector + "   " + radians);
//            final MovingPart movingPart = entity.getPart(MovingPart.class);
//            if (radians < 0) {
//                (movingPart).setLeft(true);
//            } else (movingPart).setRight(true);
        });

//        final Vector inBetween = targetVector.subtract(startVector);
//        final Vector forward = startVector.getDirection(part.getRadians());
//        forward.angle()
        //targetVector.
        //startVector.
        //tv subtract sv == inBetweenVector
        //sv getDir == forward
        //forward.angle(inBetween) / Math.PI * 180 == angle to dest.
        //shouldTurnLeft = inbetween.crossProduct(forward) > 0;
    }
}
