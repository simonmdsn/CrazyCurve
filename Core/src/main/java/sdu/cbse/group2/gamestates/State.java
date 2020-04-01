package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import sdu.cbse.group2.Assets;
import sdu.cbse.group2.common.data.GameData;

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    GameStateManager gameStateManager;
    protected GameData gameData;
    protected Assets assets;

    protected State(GameStateManager gameStateManager, GameData gameData, Assets assets){
        this.gameStateManager = gameStateManager;
        this.gameData = gameData;
        this.assets = assets;
        mouse = new Vector3();
        cam = new OrthographicCamera();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch spriteBatch);
    public abstract void dispose();

}

