package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sdu.cbse.group2.common.data.GameData;

public class MenuState extends State{

    private Texture background;
    private Texture playBtn;
    private Texture creditsBtn;
    private Texture exitButton;
    private GameData gameData;

    public MenuState(GameStateManager gameStateManager, GameData gameData) {
        super(gameStateManager);
        background = new Texture("/MenuState/background.png");
        playBtn = new Texture("/MenuState/play button.png");
        this.gameData = gameData;
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, gameData.getDisplayWidth(), gameData.getDisplayHeight());
        spriteBatch.draw(playBtn, ((gameData.getDisplayWidth() / 2) - playBtn.getWidth() / 2), gameData.getDisplayHeight() / 2);
    }
}
