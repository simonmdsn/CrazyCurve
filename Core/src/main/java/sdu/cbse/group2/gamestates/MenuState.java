package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import sdu.cbse.group2.Game;
import sdu.cbse.group2.gamestates.settings.SettingsState;

import java.util.List;
import java.util.stream.Collectors;

public class MenuState extends State {

    private final Texture startBtnTexture;
    private final Texture settingsBtnTexture;
    private final Texture quitBtnTexture;
    private final Texture titleTexture;
    private final Button startBtn, settingsBtn, quitBtn;
    private final TextField nameTextField;
    private final Stage stage;

    public MenuState(Game game) {
        super(game);
        //Create stage
        stage = new Stage();
        //Create textures from asset manager
        startBtnTexture = getGame().getAssets().getAssetManager().get("textures/MenuState/start button.png");
        settingsBtnTexture = getGame().getAssets().getAssetManager().get("textures/MenuState/settings button.PNG");
        quitBtnTexture = getGame().getAssets().getAssetManager().get("textures/MenuState/quit button.PNG");
        titleTexture = getGame().getAssets().getAssetManager().get("textures/MenuState/title.png");
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
        startBtn.setPosition(((getGame().getGameData().getDisplayWidth() / 2) - startBtn.getWidth() / 2), getGame().getGameData().getDisplayHeight() / 2);
        settingsBtn.setPosition(((getGame().getGameData().getDisplayWidth() / 2) - quitBtnTexture.getWidth() / 2), getGame().getGameData().getDisplayHeight() / 2 - 100);
        quitBtn.setPosition(((getGame().getGameData().getDisplayWidth() / 2) - settingsBtnTexture.getWidth() / 2), getGame().getGameData().getDisplayHeight() / 2 - 200);
        nameTextField = new TextField("", new TextField.TextFieldStyle(new BitmapFont(), Color.WHITE, null, null, new TextureRegionDrawable(new TextureRegion((Texture) getGame().getAssets().getAssetManager().get("textures/MenuState/inputfield.png")))));
        nameTextField.setSize(100, 40);
        nameTextField.setPosition(((getGame().getGameData().getDisplayWidth() / 2) - nameTextField.getWidth() / 2), getGame().getGameData().getDisplayHeight() / 2 + 100);
        nameTextField.setMessageText("Name");
        //Add listeners
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                getGame().getGameData().setPlayerName(nameTextField.getText());
                getGame().getGameStateManager().set(new PlayState(game));
                dispose();
            }
        });
        settingsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                getGame().getTelnetSPI().execute("lb", response -> Gdx.app.postRunnable(() -> {
                    List<String> crazyCurveModules = response.stream().filter(moduleEntry -> moduleEntry.contains("sdu.cbse.group2")).collect(Collectors.toList());
                    getGame().getGameStateManager().set(new SettingsState(game, crazyCurveModules));
                    dispose();
                }));
            }
        });
        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.exit(0);
            }
        });
        //Add to stage, and let stage respond to buttons
        stage.addActor(startBtn);
        stage.addActor(quitBtn);
        stage.addActor(settingsBtn);
        stage.addActor(nameTextField);

        getGame().getGameData().setDisplayWidth(Gdx.graphics.getWidth()); // Quadratic
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
        stage.act();
        spriteBatch.begin();
        spriteBatch.draw(titleTexture, ((getGame().getGameData().getDisplayWidth() / 2) - titleTexture.getWidth() / 2), getGame().getGameData().getDisplayHeight() / 2 + 220);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
    }
}
