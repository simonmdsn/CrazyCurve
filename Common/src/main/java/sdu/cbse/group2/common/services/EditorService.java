package sdu.cbse.group2.common.services;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;

import java.io.File;
import java.util.List;

public interface EditorService {

    void serialize(String name, GameData gameData, World world);

    void deserialize(String name, GameData gameData, World world);

    List<File> getMaps();
}
