package sdu.cbse.group2.spawn;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;

import java.util.ArrayList;
import java.util.List;

public class Spawn {

    private double degreesToRadians(int i) {
        return i * Math.PI / 180;
    }

    public List<SpawnPoint> createSpawnPoints(int numberOfSpawnPoints, GameData gameData) {
        //TODO consider data structure
        List<SpawnPoint> spawnPoints = new ArrayList();
        int x = gameData.getDisplayWidth() / 2;
        int y = gameData.getDisplayHeight() / 2;
        int radius;
        if (x > y) {
            radius = x / 3;
        } else {
            radius = y / 3;
        }
        int degrees = 360 / numberOfSpawnPoints;
        for (int i = 0; i < numberOfSpawnPoints; i++) {
            float radians = (float) degreesToRadians(degrees * (i + 1));
            float xx = (float) (x + radius * Math.cos(radians));
            float yy = (float) (y + radius * Math.sin(radians));
            spawnPoints.add(new SpawnPoint(xx, yy, radians));
        }
        return spawnPoints;
    }

    public void spawn(List<SpawnPoint> spawnPoints, List<? extends Entity> entities) {
        if(spawnPoints.size() == entities.size()) {
            for (int i = 0; i < spawnPoints.size(); i++) {
                spawnPoints.get(i).setSpawnPointOnEntity(entities.get(i));
            }
        }
    }
}
