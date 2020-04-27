package sdu.cbse.group2.round;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;
import sdu.cbse.group2.commonsnake.ScoreSPI;
import sdu.cbse.group2.round.spawn.Spawn;
import sdu.cbse.group2.round.spawn.SpawnPoint;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Round implements IGamePluginService, IPostEntityProcessingService, ScoreSPI {

    //TODO review correct data structure, stack.
    private final Stack<CommonSnake> positionStack = new Stack<>();
    private final Map<CommonSnake, Integer> pointDistributionMap = new HashMap<>();
    private Spawn spawn;
    private boolean roundEnded;

    private void distributePoints() {
        int size = positionStack.size();
        for (int i = 0; i < size; i++) {
            CommonSnake commonSnake = positionStack.pop();
            int currentPoints = pointDistributionMap.get(commonSnake);
            pointDistributionMap.replace(commonSnake, currentPoints + (size - i));
        }
    }

    private void startNewRound(World world, List<CommonSnake> commonSnakeList) {
        commonSnakeList.forEach(entity -> entity.revive(world));
    }

    @Override
    public void start(GameData gameData, World world) {
        spawn = new Spawn(gameData);
        spawn.spawn(world.getBoundedEntities(CommonSnake.class).stream().map(CommonSnake.class::cast).peek(commonSnake -> pointDistributionMap.putIfAbsent(commonSnake, 0)).collect(Collectors.toList()));
    }

    @Override
    public void stop(GameData gameData, World world) {
        new ArrayList<>(world.getEntities(SpawnPoint.class)).forEach(world::removeEntity);
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
                spawn.spawn(commonSnakeList);
                roundEnded = false;
            }, 5, TimeUnit.SECONDS);
        }
    }

    @Override
    public int getScore(final CommonSnake commonSnake) {
        pointDistributionMap.putIfAbsent(commonSnake, 0);
        return pointDistributionMap.get(commonSnake);
    }
}
