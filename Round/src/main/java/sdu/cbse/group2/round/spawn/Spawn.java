package sdu.cbse.group2.round.spawn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class Spawn {

    @Getter
    private final List<SpawnPoint> spawnPoints = new ArrayList<>();
    private final GameData gameData;

    private double degreesToRadians(int i) {
        return i * Math.PI / 180;
    }

    public void computeSpawnPoints(int numberOfSpawnPoints) {
        //TODO consider data structure
        spawnPoints.clear();
        int x = gameData.getDisplayWidth() / 2;
        int y = gameData.getDisplayHeight() / 2;
        int radius;
        if (x > y) {
            radius = x / 3;
        } else radius = y / 3;
        int degrees = 360 / numberOfSpawnPoints;
        for (int i = 0; i < numberOfSpawnPoints; i++) {
            float radians = (float) degreesToRadians(degrees * (i + 1));
            float xx = (float) (x + radius * Math.cos(radians));
            float yy = (float) (y + radius * Math.sin(radians));
            spawnPoints.add(new SpawnPoint(xx, yy, radians));
        }
    }

    public void spawn(List<CommonSnake> entities) {
        if (spawnPoints.size() != entities.size()) computeSpawnPoints(entities.size());
        IntStream.range(0, spawnPoints.size()).forEach(i -> spawnPoints.get(i).setSpawnPointOnEntity(entities.get(i)));
    }
}
