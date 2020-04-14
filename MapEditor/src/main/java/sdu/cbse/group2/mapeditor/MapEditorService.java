package sdu.cbse.group2.mapeditor;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.EditorService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapEditorService implements EditorService {

    private static final String FOLDER_PATH = "/maps";
    private static final String SER_SUFFIX = ".ser";


    public List<File> mapFileList() {
        List<File> mapFileList = new ArrayList<>();
        for (File file : Objects.requireNonNull(new File(FOLDER_PATH).listFiles())) {
            if (file.isFile()) {
                mapFileList.add(file);
            }
        }
        return mapFileList;
    }

    private World loadMap(String mapName, World world) {
        try (FileInputStream fileInputStream = new FileInputStream(mapName + SER_SUFFIX);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
            while (true) {
                Entity entity = (Entity) objectInputStream.readObject();
                world.addEntity(entity);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return world;
    }

    private void saveMap(String mapName, World world) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(mapName + SER_SUFFIX);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            for (Entity entity : world.getEntities()) {
                objectOutputStream.writeObject(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void serialize(String name, GameData gameData, World world) {
        saveMap(name, world);
    }

    @Override
    public void deserialize(String name, GameData gameData, World world) {
        loadMap(name, world);
    }
}
