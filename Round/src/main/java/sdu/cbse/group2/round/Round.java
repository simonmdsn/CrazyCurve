package sdu.cbse.group2.round;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.spawn.Spawn;
import sdu.cbse.group2.spawn.SpawnPoint;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Round implements IGamePluginService, IPostEntityProcessingService {

    //TODO review correct data structure, stack.
    private final Stack<CommonSnake> positionStack = new Stack<>();
    private final Map<CommonSnake, Integer> pointDistributionMap = new HashMap<>();
    private final Spawn spawn = new Spawn();
    private boolean roundEnded;
    private List<SpawnPoint> spawnPoints;

    private void distributePoints() {
        while (!positionStack.isEmpty()) {
            int size = positionStack.size();
            for (int i = 0; i < size; i++) {
                CommonSnake commonSnake = positionStack.pop();
                int currentPoints = pointDistributionMap.get(commonSnake);
                pointDistributionMap.replace(commonSnake, currentPoints + (size - i));
            }
        }
    }

    private void populateMap(CommonSnake commonSnake) {
        if (!pointDistributionMap.containsKey(commonSnake)) {
            pointDistributionMap.put(commonSnake, 0);
        }
    }

    private void drawScores(int x, int y, CommonSnake commonSnake, World world) {
        world.addText(new ScoreText(pointDistributionMap.get(commonSnake).toString() + " : " + commonSnake.toString(), x, y));
    }

    private void removeScores(World world) {
        world.boundedTextClear(ScoreText.class);
        roundEnded = false;
    }

    private void startNewRound(World world, List<CommonSnake> commonSnakeList) {
        commonSnakeList.forEach(entity -> {
            entity.revive(world);
        });
    }

    @Override
    public void start(GameData gameData, World world) {
        List<CommonSnake> commonSnakeList = world.getBoundedEntities(CommonSnake.class).stream().map(entity -> (CommonSnake) entity).collect(Collectors.toList());
        if (world.getEntities(SpawnPoint.class).isEmpty()) {
            spawnPoints = spawn.createSpawnPoints(commonSnakeList.size(), gameData);
        }
        commonSnakeList.forEach(this::populateMap);
        spawn.spawn(spawnPoints, commonSnakeList);
    }

    @Override
    public void stop(GameData gameData, World world) {
    }

    @Override
    public void process(GameData gameData, World world) {
        List<CommonSnake> commonSnakeList = world.getBoundedEntities(CommonSnake.class).stream().map(CommonSnake.class::cast).collect(Collectors.toList());

        //Add snake to stack if dead
        commonSnakeList.stream().filter(snake -> !positionStack.contains(snake) && !snake.isAlive() && !roundEnded).forEach(positionStack::push);

        //Starts new round if all snakes are dead.
        if (commonSnakeList.stream().noneMatch(CommonSnake::isAlive) && !roundEnded) {
            roundEnded = true;
            distributePoints();
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                startNewRound(world, commonSnakeList);
                spawn.spawn(spawnPoints, commonSnakeList);
                roundEnded = false;
            }, 5, TimeUnit.SECONDS);
        }
    }
}
