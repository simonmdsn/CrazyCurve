package sdu.cbse.group2.ai;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.Tile;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.AiSPI;

import java.util.ArrayList;
import java.util.Comparator;
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
            final PositionPart positionPart = entity.getPart(PositionPart.class);
            Node targetNode = goalList.stream().min(Comparator.comparingDouble(o1 -> Math.sqrt(Math.pow(o1.getRow() - (int) (positionPart.getX() / Tile.LENGTH), 2) + Math.pow(o1.getCol() - (int) (positionPart.getY() / Tile.LENGTH), 2)))).orElse(goalList.get(0));
            aStar.setSearchArea(nodes);
            final Node currentPosition = new Node((int) positionPart.getX() / Tile.LENGTH, (int) positionPart.getY() / Tile.LENGTH);
            aStar.setStartNode(currentPosition);
            aStar.setTargetNode(targetNode);
            final List<Node> path = aStar.findPath();
            Node target;
            int i = 0;
            do {

            } while ();
            System.out.println(target.equals(currentPosition) + "   " + path.size());

            return Optional.of(target);
        } else return Optional.empty();
    }

    @Override
    public void move(Entity entity, World world, int searchRadius) {
        getTarget(entity, world, searchRadius).ifPresent(target -> {
            final Vector targetVector = new Vector(target.getRow() * Tile.LENGTH, target.getCol() * Tile.LENGTH);
            final PositionPart part = entity.getPart(PositionPart.class);
            final Vector startVector = new Vector(part.getX(),  part.getY());
            final Vector inBetween = targetVector.subtract(startVector);
            final Vector direction = new Vector(Math.cos(part.getRadians()) * -1, Math.sin(part.getRadians()) * -1);
            final double cross = inBetween.cross(direction);
            final MovingPart movingPart = entity.getPart(MovingPart.class);
            movingPart.setRight(cross > 0);
            movingPart.setLeft(cross <= 0);
        });
    }
}
