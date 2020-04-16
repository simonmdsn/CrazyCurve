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
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import sdu.cbse.group2.Game;
import sdu.cbse.group2.gamestates.MenuState;
import sdu.cbse.group2.gamestates.State;

import java.util.*;
import java.util.function.Function;
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
        final Table table = new Table();
        table.setFillParent(true);
        for (final CrazyCurveModule crazyCurveModule : crazyCurveModules) {
            final TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(null, null, null, new BitmapFont());
            textButtonStyle.fontColor = crazyCurveModule.isActive() ? Color.GREEN : Color.RED;
            final TextButton textButton = new TextButton("(" + crazyCurveModule.getId() + ") " + crazyCurveModule.getName(), textButtonStyle);
            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(final InputEvent event, final float x, final float y) {
                    crazyCurveModule.toggleActive();
                    getGame().getTelnetSPI().execute(crazyCurveModule.isActive() ? "start" : "stop" + " " + crazyCurveModule.getId(), ignored -> {});
                    textButtonStyle.fontColor = crazyCurveModule.isActive() ? Color.GREEN : Color.RED;
                }
            });
            table.add(textButton).center().row();
        }
        final Function<TextButton, Float> textWidth = textButton -> textButton.getStyle().font.getBounds(textButton.getText()).width;
        final float greatestWidth = Arrays.stream(table.getCells().toArray()).map(Cell::getActor).map(TextButton.class::cast).map(textWidth).max(Comparator.comparingDouble(Float::doubleValue)).orElse(0F);
        Arrays.stream(table.getCells().toArray()).map(Cell::getActor).map(TextButton.class::cast).forEach(textButton -> textButton.padRight(greatestWidth - textWidth.apply(textButton)));
        table.add(createBackButton());
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private ImageButton createBackButton() {
        final Texture backButtonTexture = getGame().getAssets().getAssetManager().get("MenuState/settings button.PNG"); //TODO Back image.
        Drawable backDrawable = new TextureRegionDrawable(new TextureRegion(backButtonTexture));
        final ImageButton backButton = new ImageButton(backDrawable);
        backButton.setSize(200, 80);
        backButton.padTop(30);
        backButton.setPosition((int) (getGame().getGameData().getDisplayWidth() / 2D - backButton.getWidth() / 2D), 150);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                getGame().getGameStateManager().set(new MenuState(getGame()));
                dispose();
            }
        });
        return backButton;
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
