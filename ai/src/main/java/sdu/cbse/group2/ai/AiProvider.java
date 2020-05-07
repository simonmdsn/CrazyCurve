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
                Node node = new Node(Math.round(positionPart.getX() / Tile.LENGTH), Math.round(positionPart.getY() / Tile.LENGTH));
                if (r == 0 || c == cols - 1 || c == 0 || r == rows - 1) {
                    node.setObstructed(true);
                }
                node.setObstructed(tile.isObstructing());
//                if (node.isObstructed()) {
//                    AiDrawer.getDrawSPI().drawCircle(Math.round(positionPart.getX()),Math.round(positionPart.getY()), 10);
//                }
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
            final Node currentPosition = new Node(Math.round(positionPart.getX() / Tile.LENGTH), Math.round(positionPart.getY() / Tile.LENGTH));
            aStar.setStartNode(currentPosition);
            aStar.setTargetNode(targetNode);
            final List<Node> path = aStar.findPath();
            path.forEach(node -> AiDrawer.getDrawSPI().drawCircle(node.getRow() * Tile.LENGTH, node.getCol() * Tile.LENGTH, 3));
            if (!path.isEmpty()) {
                Node target;
                int i = 0;
                do {
                    target = path.get(i++);
                } while (currentPosition.equals(target) && i < path.size());
                return Optional.of(path.get(Math.min(path.size() - 1, i + 1)));
            }
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
}
