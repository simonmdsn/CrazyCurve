package sdu.cbse.group2.enemy;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyPlugin implements IGamePluginService {

    private final String[] names = new String[]{"Anaconda", "Artemis", "Bandit", "Bi-Beast", "Black Lama", "Basilisk", "Bob", "Benz56", "Kraken", "Kato", "Adderboi", "Riot", "Rush", "Runner", "Corfixen", "Shrek", "Slipstream", "Speed Demon", "Spirit of 76'", "Beehive", "Queen Blossom", "Witcher's Girl", "Supermor", "Terminator", "Not a Snake", "Golden Age", "Craften", "Bettina", "Lars", "Phillip", "PDF-fil", "dot jpeg", "Knew It", "Byllefar", "Feeder", "Carole Baskin", "Joe Exotic", "Mr. Gadget"};
    private final String[] colors = new String[]{"red", "blue", "yellow"};
    private final Stack<String> colorStack = new Stack();
    private final List<String> snakeNameList = new ArrayList<>();

    private Enemy createEnemySnake() {
        GameSprite[] gameSprites = getSnakeGameSprites();
        return new Enemy(gameSprites[0], gameSprites[1], getRandomName());
    }

    private Stack<String> populateColorStack() {
        colorStack.addAll(Arrays.asList(colors));
        return colorStack;
    }

    private String getRandomName() {
        String name = names[ThreadLocalRandom.current().nextInt(names.length)];
        if (snakeNameList.contains(name)) {
            getRandomName();
        }
        snakeNameList.add(name);
        return name;
    }

    private GameSprite[] getSnakeGameSprites() {
        if (colorStack.isEmpty()) {
            populateColorStack();
        }
        String color = colorStack.pop();
        return new GameSprite[]{new GameSprite("textures/enemy/" + color + "/enemy.png", 30, 30, 1), new GameSprite("textures/enemy/" + color + "/tail.png", 30, 30)};
    }

    @Override
    public void start(GameData gameData, World world) {
        world.addEntity(createEnemySnake());
        world.addEntity(createEnemySnake());
        world.addEntity(createEnemySnake());
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Enemy.class).forEach(world::removeEntity);
    }
}
