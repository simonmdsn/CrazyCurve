package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import sdu.cbse.group2.Assets;
import sdu.cbse.group2.common.data.GameData;

public class MenuState extends State{

    private Texture startBtnTexture;
    private Texture settingsBtnTexture;
    private Texture quitBtnTexture;
    private Texture titleTexture;
    private Button startBtn, settingsBtn, quitBtn;
    private Stage stage;

    public MenuState(GameStateManager gameStateManager, GameData gameData, Assets assets) {
        super(gameStateManager, gameData, assets);
        //Create stage
        stage = new Stage();
        //Create textures from asset manager
        startBtnTexture = assets.getAssetManager().get("MenuState/start button.png");
        settingsBtnTexture = assets.getAssetManager().get("MenuState/settings button.PNG");
        quitBtnTexture = assets.getAssetManager().get("MenuState/quit button.PNG");
        titleTexture = assets.getAssetManager().get("MenuState/title.png");
        //Initiate buttons
        Drawable startDrawable = new TextureRegionDrawable(new TextureRegion(startBtnTexture));
        Drawable settingsDrawable = new TextureRegionDrawable(new TextureRegion(settingsBtnTexture));
        Drawable quitDrawable = new TextureRegionDrawable(new TextureRegion(quitBtnTexture));
        startBtn = new ImageButton(startDrawable);
        settingsBtn = new ImageButton(settingsDrawable);
        quitBtn = new ImageButton(quitDrawable);
        startBtn.setSize(200, 80);
        settingsBtn.setSize(200, 80);
        quitBtn.setSize(200, 80);
        startBtn.setPosition(((gameData.getDisplayWidth() / 2) - startBtn.getWidth() / 2), gameData.getDisplayHeight() / 2);
        settingsBtn.setPosition(((gameData.getDisplayWidth() / 2) - quitBtnTexture.getWidth() / 2), gameData.getDisplayHeight() / 2 - 100);
        quitBtn.setPosition(((gameData.getDisplayWidth() / 2) - settingsBtnTexture.getWidth() / 2), gameData.getDisplayHeight() / 2 - 200);
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                //push instead?
                gameStateManager.push(new MenuState(gameStateManager, gameData, assets));
                dispose();
            }
        });
        settingsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("Take me to settings!");
            }
        });
        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("I quit >:(!");
            }
        });
        //Add to stage, and let stage respond to buttons
        stage.addActor(startBtn);
        stage.addActor(quitBtn);
        stage.addActor(settingsBtn);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        stage.draw();
        spriteBatch.begin();
        spriteBatch.draw(titleTexture, ((gameData.getDisplayWidth() / 2) - titleTexture.getWidth() / 2), gameData.getDisplayHeight() / 2 + 125);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        titleTexture.dispose();
        startBtnTexture.dispose();
        settingsBtnTexture.dispose();
        quitBtnTexture.dispose();
        }
}
