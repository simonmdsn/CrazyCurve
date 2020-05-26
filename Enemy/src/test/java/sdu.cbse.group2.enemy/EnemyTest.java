package sdu.cbse.group2.enemy;

import org.junit.jupiter.api.*;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    // TODO
    //  Impl. Enemy Movement
    //  Check tests with Assertions(?)
    //  Fix Manifest file '-osgi.bnd' missing error for Common, common-snake and enemy
    private World world;
    private Stack<String> colors = new Stack<>();
    private static final String[] NAMES = new String[]{"EnemyTestOne", "EnemyTestTwo", "EnemyTestThree", "EnemyTestFour"};
    private final List<String> snakeNameList = new ArrayList<>();

    @BeforeEach
    void init(GameData gameData) {
        this.world = new World(gameData);
    }

    @Test
    void createEnemySnakes() {
        Stream.generate(this::createEnemySnake).limit(3).forEach(world::addEntity);
    }

    @AfterEach
    void tearDown() {
        world.getEntities(Enemy.class).stream().map(Enemy.class::cast).peek(enemy -> enemy.deleteTail(world)).forEach(world::removeEntity);
    }

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
        if (colors.isEmpty()) colors = Stream.of("red", "blue", "yellow").collect(Collectors.toCollection(Stack::new));
        String color = colors.pop();
        return new GameSprite[]{new GameSprite("textures/enemy/" + color + "/enemy.png", 30, 30, 1), new GameSprite("textures/enemy/" + color + "/tail.png", 30, 30)};
    }

}