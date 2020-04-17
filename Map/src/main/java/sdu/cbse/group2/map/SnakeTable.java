package sdu.cbse.group2.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.commonsnake.ScoreSPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class SnakeTable {

    private final Map<String, SnakeCell> snakes = new HashMap<>();
    private final ScoreSPI scoreSPI;

    void updateSnake(final CommonSnake commonSnake, final GameData gameData, final World world) {
        snakes.computeIfAbsent(commonSnake.getName(), snakeName -> {
            SnakeCell cell = new SnakeCell(commonSnake, new Entity(commonSnake.getHead().getGameSprite()));
            world.addEntity(cell.getHeadIcon());
            world.addText(cell.getSnakeTextName());
            world.addText(cell.getSnakeTextScore());
            return cell;
        }).setScore(scoreSPI.getScore(commonSnake));
        reposition(gameData);
    }

    private void reposition(final GameData gameData) {
        snakes.values().stream().sorted().forEach(new Consumer<SnakeCell>() {
            private int i;

            @Override
            public void accept(final SnakeCell snakeCell) {
                final int y = (gameData.getDisplayHeight() - 15) - ++i * 50;
                final PositionPart positionPart = snakeCell.getHeadIcon().getPart(PositionPart.class);
                positionPart.setX(gameData.getDisplayWidth() + 30);
                positionPart.setY(y);
                snakeCell.getSnakeTextName().setX(gameData.getDisplayWidth() + 80);
                snakeCell.getSnakeTextName().setY(y + 20);
                snakeCell.getSnakeTextScore().setX(gameData.getDisplayWidth() + 260);
                snakeCell.getSnakeTextScore().setY(y + 20);
            }
        });
    }

    void clear(World world) {
        snakes.values().forEach(snakeCell -> {
            world.removeEntity(snakeCell.getUniqueId());
            world.removeText(snakeCell.getSnakeTextName());
            world.removeText(snakeCell.getSnakeTextScore());
        });
    }

    @Getter
    @Setter
    public static class SnakeCell implements Comparable<SnakeCell> {

        private final UUID uniqueId = UUID.randomUUID();
        private final SnakeText snakeTextScore = new SnakeText();
        private final CommonSnake commonSnake;
        private final SnakeText snakeTextName;
        private final Entity headIcon;
        private int score;

        public SnakeCell(final CommonSnake commonSnake, final Entity headIcon) {
            this.commonSnake = commonSnake;
            snakeTextName = new SnakeText(commonSnake.getName());
            this.headIcon = headIcon;
            headIcon.add(new PositionPart(0, 0, (float) (Math.PI / 2)));
        }

        void setScore(final int score) {
            this.score = score;
            snakeTextScore.setText(String.valueOf(score));
        }

        @Override
        public boolean equals(final Object o) {
            return this == o || (getClass() == o.getClass() && Objects.equals(commonSnake.getName(), ((SnakeCell) o).commonSnake.getName()));
        }

        @Override
        public int hashCode() {
            return Objects.hash(commonSnake.getName());
        }

        @Override
        public int compareTo(final SnakeCell o) {
            return Integer.compare(o.score, score);
        }
    }
}
