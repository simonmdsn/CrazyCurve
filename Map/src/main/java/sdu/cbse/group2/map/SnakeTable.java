package sdu.cbse.group2.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SnakeTable {

    private final Map<String, SnakeCell> snakes = new HashMap<>();

    public void updateSnake(final CommonSnake commonSnake, final GameData gameData, final World world) {
        snakes.computeIfAbsent(String.valueOf(commonSnake), snakeName -> { //TODO CommonSnake.getName();
            SnakeCell cell = new SnakeCell(commonSnake.getHead().getGameSprite(), snakeName);
            Entity snakeHead = new Entity(commonSnake.getHead().getGameSprite());
            int y = (gameData.getDisplayHeight() - 15) - (snakes.size() + 1) * 50;
            snakeHead.add(new PositionPart(gameData.getDisplayWidth() + 30, y, (float) (Math.PI / 2)));
            // TODO                              Remove substring part.
            cell.setSnakeTextName(new SnakeText(snakeName.substring(snakeName.lastIndexOf(".")), gameData.getDisplayWidth() + 80, y + 20));
            cell.setSnakeTextScore(new SnakeText("0", gameData.getDisplayWidth() + 260, y + 20));
            world.addEntity(snakeHead);
            world.addText(cell.getSnakeTextName());
            world.addText(cell.getSnakeTextScore());
            return cell;
        });
    }

    public void clear(World world) {
        snakes.values().forEach(snakeCell -> {
            world.removeEntity(snakeCell.getUniqueId());
            world.removeText(snakeCell.getSnakeTextName());
        });
    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class SnakeCell {

        private final UUID uniqueId = UUID.randomUUID();
        private final GameSprite head;
        private final String snakeName;
        private SnakeText snakeTextName;
        private SnakeText snakeTextScore;
        private int score;

        public void setScore(final int score) {
            this.score = score;
            snakeTextScore.setText(String.valueOf(score));
        }

        @Override
        public boolean equals(final Object o) {
            return this == o || (getClass() == o.getClass() && Objects.equals(snakeName, ((SnakeCell) o).snakeName));
        }

        @Override
        public int hashCode() {
            return Objects.hash(snakeName);
        }
    }
}
