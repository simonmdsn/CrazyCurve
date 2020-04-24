package sdu.cbse.group2.enemy;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnemyPlugin implements IGamePluginService {

    private static final String[] NAMES = new String[]{"Anaconda", "Artemis", "Bandit", "Bi-Beast", "Black Lama", "Basilisk", "Bob", "Benz56", "Kraken", "Kato", "Adderboi", "Riot", "Rush", "Runner", "Corfixen", "Shrek", "Slipstream", "Speed Demon", "Spirit of 76'", "Beehive", "Queen Blossom", "Witcher's Girl", "Supermor", "Terminator", "Not a Snake", "Golden Age", "Craften", "Bettina", "Lars", "Phillip", "PDF-fil", "dot jpeg", "Knew It", "Byllefar", "Feeder", "Carole Baskin", "Joe Exotic", "Mr. Gadget"};
    private static final Stack<String> COLORS = Stream.of("red", "blue", "yellow").collect(Collectors.toCollection(Stack::new));

    private final List<String> snakeNameList = new ArrayList<>();

    private Enemy createEnemySnake() {
        GameSprite[] gameSprites = getSnakeGameSprites();
        return new Enemy(gameSprites[0], gameSprites[1], getRandomName());
    }

    private String getRandomName() {
        String name = NAMES[ThreadLocalRandom.current().nextInt(NAMES.length)];
        if (snakeNameList.contains(name)) {
            getRandomName();
        }
        snakeNameList.add(name);
        return name;
    }

    private GameSprite[] getSnakeGameSprites() {
        String color = COLORS.pop();
        return new GameSprite[]{new GameSprite("textures/enemy/" + color + "/enemy.png", 30, 30, 1), new GameSprite("textures/enemy/" + color + "/tail.png", 30, 30)};
    }

    @Override
    public void start(GameData gameData, World world) {
        Stream.generate(this::createEnemySnake).limit(3).forEach(world::addEntity);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Enemy.class).stream().map(Enemy.class::cast).peek(enemy -> enemy.deleteTail(world)).forEach(world::removeEntity);
    }
}
