package sdu.cbse.group2.round;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//TODO create background for scores.
public class Round implements IGamePluginService, IPostEntityProcessingService {

    private static final int[] POINTS_ARRAY = new int[]{10, 6, 4, 3};
    //TODO review correct data structure, stack.
    private final Stack<CommonSnake> positionStack = new Stack<>();
    private final Map<CommonSnake, Integer> pointDistributionMap = new HashMap<>();
    private boolean pointsDrawn;

    private PositionPart getEntityPositionPart(Entity entity) {
        return entity.getPart(PositionPart.class);
    }

    private void setSpawnLocations(List<CommonSnake> commonSnakeList, GameData gameData) {
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

    private void distributePoints() {
        while (!positionStack.isEmpty()) {
            for (int i = 0; i < POINTS_ARRAY.length; i++) {
                CommonSnake commonSnake = positionStack.pop();
                int currentPoints = pointDistributionMap.get(commonSnake);
                System.out.println(commonSnake + " : " + currentPoints + " +" + POINTS_ARRAY[i]);
                pointDistributionMap.replace(commonSnake, currentPoints + POINTS_ARRAY[i]);
            }
        }
    }

    private void drawPointsInOrder(World world, GameData gameData) {
        if (!pointsDrawn) {
            int x = (int) (gameData.getDisplayWidth() / 2);
            int y = (int) (gameData.getDisplayHeight() / 1.5);
            ArrayList<CommonSnake> sortedSnakes = new ArrayList(pointDistributionMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (integer, integer2) -> integer2, LinkedHashMap::new)).keySet());
            for (int i = sortedSnakes.size() - 1; i >= 0; i--) {
                drawScores(x, y, sortedSnakes.get(i), world);
                y -= 20;
            }
            pointsDrawn = true;
        }
    }

    private void populateMap(CommonSnake commonSnake) {
        if (!pointDistributionMap.containsKey(commonSnake)) {
            pointDistributionMap.put(commonSnake, 0);
        }
    }

    private void drawScores(int x, int y, CommonSnake commonSnake, World world) {
        world.draw(new ScoreText(pointDistributionMap.get(commonSnake).toString() + " : " + commonSnake.toString(), x, y));
    }

    private void drawScoresBackground(int x, int y, World world) {
        Entity entity = new Entity(new GameSprite("round/scoretext_background.png", 30, 30));
        entity.add(new PositionPart(x, y, 0));
        world.addEntity(entity);
    }

    private void removeScores(World world) {
        world.boundedTextClear(ScoreText.class);
        pointsDrawn = false;
    }

    private void startNewRound(World world, List<CommonSnake> commonSnakeList) {
        commonSnakeList.forEach(entity -> {
            entity.revive(world);
        });
    }

    @Override
    public void start(GameData gameData, World world) {
        List<CommonSnake> commonSnakeList = world.getBoundedEntities(CommonSnake.class).stream().map(entity -> (CommonSnake) entity).collect(Collectors.toList());
        setSpawnLocations(commonSnakeList, gameData);
        commonSnakeList.forEach(this::populateMap);
    }

    @Override
    public void stop(GameData gameData, World world) {
    }

    @Override
    public void process(GameData gameData, World world) {
        List<CommonSnake> commonSnakeList = world.getBoundedEntities(CommonSnake.class).stream().map(entity -> (CommonSnake) entity).collect(Collectors.toList());

        //Add snake to stack if dead
        for (CommonSnake snake : commonSnakeList) {
            if (!positionStack.contains(snake) && !snake.isAlive() && !pointsDrawn) {
                positionStack.push(snake);
            }
        }

        //Starts new round if all snakes are dead.
        if (commonSnakeList.stream().allMatch(entity -> !entity.isAlive()) && !pointsDrawn) {
            System.out.println(Arrays.asList(positionStack));
            distributePoints();
            drawPointsInOrder(world, gameData);
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                startNewRound(world, commonSnakeList);
                setSpawnLocations(commonSnakeList, gameData);
                removeScores(world);
            }, 5, TimeUnit.SECONDS);
        }

    }
}
