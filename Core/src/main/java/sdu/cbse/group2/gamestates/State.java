package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import sdu.cbse.group2.Assets;
import sdu.cbse.group2.Game;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.World;

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected Game game;

    protected State(Game game){
        this.game = game;
        mouse = new Vector3();
        cam = new OrthographicCamera();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch spriteBatch);
    public abstract void dispose();

}

