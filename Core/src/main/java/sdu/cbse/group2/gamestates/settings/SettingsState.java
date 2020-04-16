package sdu.cbse.group2.gamestates.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import sdu.cbse.group2.Game;
import sdu.cbse.group2.gamestates.MenuState;
import sdu.cbse.group2.gamestates.State;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SettingsState extends State {

    private static final Set<String> IGNORED_MODULES = new HashSet<>(Arrays.asList("common", "Common", "libgdx", "core", "Telnet"));
    private final List<CrazyCurveModule> crazyCurveModules;
    private final Stage stage = new Stage();

    public SettingsState(Game game, final List<String> crazyCurveModules) {
        super(game);
        this.crazyCurveModules = crazyCurveModules.stream()
                .map(CrazyCurveModule::new)
                .filter(crazyCurveModule -> IGNORED_MODULES.stream().noneMatch(ignored -> crazyCurveModule.getName().contains(ignored)))
                .collect(Collectors.toList());
        init();
    }

    private void init() {
        for (int i = 0; i < crazyCurveModules.size(); i++) {
            final CrazyCurveModule crazyCurveModule = crazyCurveModules.get(i);
            final TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = new BitmapFont();
            textButtonStyle.fontColor = crazyCurveModule.isActive() ? Color.GREEN : Color.RED;
            final TextButton textButton = new TextButton("(" + crazyCurveModule.getId() + ") " + crazyCurveModule.getName(), textButtonStyle);
            textButton.getLabel().setAlignment(Align.left);
            textButton.setSize(200, textButtonStyle.font.getCapHeight());
            textButton.setPosition((int) (getGame().getGameData().getDisplayWidth() / 2.3D), (int) (getGame().getGameData().getDisplayHeight() / 1.3D) - 30 * i);
            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x, final float y) {
                    super.clicked(event, x, y);
                    crazyCurveModule.toggleActive();
                    getGame().getTelnetSPI().execute(crazyCurveModule.isActive() ? "start" : "stop" + " " + crazyCurveModule.getId(), ignored -> {});
                    textButtonStyle.fontColor = crazyCurveModule.isActive() ? Color.GREEN : Color.RED;
                }
            });
            stage.addActor(textButton);
        }
        createBackButton();
        Gdx.input.setInputProcessor(stage);
    }

    private void createBackButton() {
        final Texture backButtonTexture = getGame().getAssets().getAssetManager().get("MenuState/settings button.PNG"); //TODO Back image.
        Drawable backDrawable = new TextureRegionDrawable(new TextureRegion(backButtonTexture));
        final ImageButton backButton = new ImageButton(backDrawable);
        backButton.setSize(200, 80);
        backButton.setPosition((int) (getGame().getGameData().getDisplayWidth() / 2D - backButton.getWidth() / 2D), 150);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                getGame().getGameStateManager().set(new MenuState(getGame()));
                dispose();
            }
        });
        stage.addActor(backButton);
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        stage.draw();
    }

    @Override
    public void dispose() {
    }
}
