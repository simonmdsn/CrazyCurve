package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sdu.cbse.group2.Game;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public abstract class State {

    private final OrthographicCamera cam = new OrthographicCamera();
    private final Vector3 mouse = new Vector3();
    private final Game game;

    protected abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch spriteBatch);

    public abstract void dispose();
}

