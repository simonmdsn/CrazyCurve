package sdu.cbse.group2.commonsnake;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;

//TODO
public interface SnakeSPI {

    CommonSnake create(GameData gameData, World world);
}
