package sdu.cbse.group2;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import sdu.cbse.group2.assets.Assets;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.gamestates.GameStateManager;
import sdu.cbse.group2.gamestates.MenuState;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Game implements ApplicationListener {

    private final GameData gameData = new GameData();
    private final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private Assets assets;
    private OrthographicCamera cam;
    private World world = new World();
    private GameStateManager gameStateManager = new GameStateManager();
    private List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    public Game() {
        init();
    }

    public void init() {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Crazy Curve";
        cfg.width = 1200;
        cfg.height = 800;
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

        gameStateManager.push(new MenuState(this));
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render((SpriteBatch) assets.getBatch());
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
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }

}
