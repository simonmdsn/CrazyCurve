package sdu.cbse.group2.common.services;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
