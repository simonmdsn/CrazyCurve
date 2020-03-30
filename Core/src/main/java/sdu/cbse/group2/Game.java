package sdu.cbse.group2;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.core.managers.GameInputProcessor;
import sdu.cbse.group2.gamestates.GameStateManager;
import sdu.cbse.group2.gamestates.MenuState;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Game implements ApplicationListener {

    private Assets assets;

    private static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private GameStateManager gameStateManager = new GameStateManager();
    private SpriteBatch spriteBatch = new SpriteBatch();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    public Game(){
        init();
    }

    public void init() {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Crazy Curve";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {

        assets = new Assets();
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        gameStateManager.push(new MenuState(gameStateManager));

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(spriteBatch);
        update();
        draw();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        assets.getBatch().begin();
        for (Entity entity : world.getEntities()) {
            GameSprite gameSprite = entity.getGameSprite();
            Texture texture = assets.getAssetManager().get(gameSprite.getImagePath(), Texture.class);
            drawSprite(gameSprite,entity.getPart(PositionPart.class), texture);
        }
        assets.getBatch().end();
    }

    private void drawSprite(GameSprite gameSprite, PositionPart positionPart, Texture texture) {
        //TODO sprite needs things
        //                GameImage image = entity.getImage();
        //                Texture tex = assetManager.get(image.getImagePath(), Texture.class);
        //                PositionPart p = entity.getPart(PositionPart.class);
        //                drawSprite(new Sprite(tex), image, p);
        Sprite sprite = new Sprite(texture);
        sprite.setOrigin(gameSprite.getWidth() / 2, gameSprite.getHeight() / 2);
        sprite.rotate((float) Math.toDegrees(positionPart.getRadians()));
        sprite.setX(positionPart.getX());
        sprite.setY(positionPart.getY());
        sprite.setSize(gameSprite.getWidth(), gameSprite.getHeight());
        sprite.draw(assets.getBatch());
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.add(plugin);
        plugin.start(gameData, world);

    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }

}
