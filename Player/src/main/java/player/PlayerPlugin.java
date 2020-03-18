package player;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.commonsnake.SnakeSPI;

public class PlayerPlugin implements SnakeSPI, IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {

    }

//    private Entity createPlayerSnake() {
//        float maxSpeed = 100;
//        float rotationSpeed = 5;
//        float x,y = 0;
//        float radians = 5;
//
//        //TODO give values
//        //Entity playerSnake = new CommonSnake();
//
//        return
//    }

    @Override
    public CommonSnake create(GameData gameData, World world) {
        return null;
    }
}
