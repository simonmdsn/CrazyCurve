package sdu.cbse.group2.enemy;

import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyPlugin implements IGamePluginService {

    private final String[] names = new String[]{"Anaconda", "Artemis", "Bandit", "Bi-Beast", "Black Lama", "Basilisk", "Bob", "Benz56", "Kraken", "Kato", "Adderboi", "Riot", "Rush", "Runner", "Corfixen", "Shrek", "Slipstream", "Speed Demon", "Spirit of 76'", "Beehive", "Queen Blossom", "Witcher's Girl", "Supermor", "Terminator", "Not a Snake", "Golden Age", "Craften", "Bettina", "Lars", "Phillip", "PDF-fil", "dot jpeg", "Knew It", "Byllefar", "Feeder", "Carole Baskin", "Joe Exotic", "Mr. Gadget"};
    private final List<String> snakeNameList = new ArrayList<>();

    @Override
    public void start(GameData gameData, World world) {
        world.addEntity(createEnemySnake());
        world.addEntity(createEnemySnake());
        world.addEntity(createEnemySnake());
    }

    // TODO Add Random Spawn
    private Enemy createEnemySnake() {
        return new Enemy(new GameSprite("textures/enemy/enemy.png", 30, 30, 1), new GameSprite("textures/enemy/tail.png", 30, 30), getRandomName());
    }

    private String getRandomName() {
        String name = names[ThreadLocalRandom.current().nextInt(names.length)];
        if (snakeNameList.contains(name)) {
            getRandomName();
        }
        snakeNameList.add(name);
        return name;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Enemy.class).forEach(world::removeEntity);
    }
}
