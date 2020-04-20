package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;
import java.util.Stack;
import lombok.Getter;

@Getter
public class GameStateManager {

    private final Stack<State> states = new Stack<>();

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(State state){
        states.pop();
        states.push(state);
    }

    public void render(SpriteBatch spriteBatch){
        states.peek().render(spriteBatch);
    }

    public void update(float dt){
        states.peek().update(dt);
    }
}
