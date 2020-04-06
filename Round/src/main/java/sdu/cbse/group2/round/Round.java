package sdu.cbse.group2.round;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.List;

public class Round implements IGamePluginService, IPostEntityProcessingService {

    private PositionPart getEntityPositionPart(Entity entity) {
        return entity.getPart(PositionPart.class);
    }

    private void setSpawnLocations(List<Entity> commonSnakeList, GameData gameData) {
        int x = gameData.getDisplayWidth() / 4;
        int y = gameData.getDisplayHeight() / 4;
        int xx = x;
        int yy = y;
        for (int i = 0; i < commonSnakeList.size(); i++) {
            PositionPart positionPart = getEntityPositionPart(commonSnakeList.get(i));
            positionPart.setX(x);
            positionPart.setY(y);
            if (i % 2 == 0) {
                y += yy;
            } else {
                x += xx;
            }
        }
    }

    @Override
    public void start(GameData gameData, World world) {
        setSpawnLocations(world.getBoundedEntities(CommonSnake.class), gameData);
    }

    @Override
    public void stop(GameData gameData, World world) {
    }

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> commonSnakeList = world.getBoundedEntities(CommonSnake.class);
        if (commonSnakeList.stream().allMatch(entity -> !((CommonSnake) entity).isAlive())) {
            commonSnakeList.forEach(entity -> {
                ((CommonSnake) entity).deleteAndEmptyTails(world);
                ((CommonSnake) entity).setAlive(true);
                ((CommonSnake) entity).setMaxSpeed(100);
                ((CommonSnake) entity).setRotationSpeed(3);
                System.out.println(((CommonSnake) entity).getMaxSpeed());
            });
            setSpawnLocations(commonSnakeList, gameData);
        }
    }
}
