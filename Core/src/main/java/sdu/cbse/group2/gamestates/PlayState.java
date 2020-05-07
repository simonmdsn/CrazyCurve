package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.Game;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameKeys;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.Text;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.core.managers.GameInputProcessor;
import sdu.cbse.group2.gamestates.settings.SettingsState;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class PlayState extends State {

    private boolean paused;

    public PlayState(Game game) {
        super(game);
        //To enable button pressing
        paused = false;
        Gdx.input.setInputProcessor(new GameInputProcessor(getGame().getGameData()));
        getGame().getGameData().setDisplayWidth(getGame().getGameData().getDisplayHeight()); // Quadratic
        getGame().getGamePluginList().forEach(iGamePluginService -> iGamePluginService.start(getGame().getGameData(), getGame().getWorld()));
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        if (!paused) {
            update();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (!paused) {
            getGame().getGameData().getKeys().update();
            draw(spriteBatch);
        }
    }

    @Override
    public void dispose() {
    }

    private void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Entity entity : getGame().getWorld().getEntities().stream().sorted(Comparator.comparingInt(o -> o.getGameSprite().getLayer())).collect(Collectors.toList())) {
            GameSprite gameSprite = entity.getGameSprite();
            Texture texture = getGame().getAssets().getAssetManager().get(gameSprite.getImagePath(), Texture.class);
            drawSprite(gameSprite, entity.getPart(PositionPart.class), texture);
        }
        for (Text text : getGame().getWorld().getTextList()) {
            drawText(text);
        }
        spriteBatch.end();
    }

    private void drawSprite(GameSprite gameSprite, PositionPart positionPart, Texture texture) {
        Sprite sprite = new Sprite(texture);
        sprite.setOrigin(gameSprite.getWidth() / 2, gameSprite.getHeight() / 2);
        sprite.rotate((float) Math.toDegrees(positionPart.getRadians()));
        sprite.setX(positionPart.getX());
        sprite.setY(positionPart.getY());
        sprite.setSize(gameSprite.getWidth(), gameSprite.getHeight());
        sprite.draw(getGame().getAssets().getBatch());
    }

    private void drawText(Text text) {
        getGame().getAssets().getBitmapFont().draw(getGame().getAssets().getBatch(), text.getText(), text.getX(), text.getY());
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getGame().getEntityProcessorList()) {
            entityProcessorService.process(getGame().getGameData(), getGame().getWorld());
        }
        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getGame().getPostEntityProcessorList()) {
            postEntityProcessorService.process(getGame().getGameData(), getGame().getWorld());
        }
        //Check if game is paused
        if (getGame().getGameData().getKeys().isDown(GameKeys.ESCAPE)) {
            this.setPaused(true);
            getGame().getTelnetSPI().execute("lb", response -> Gdx.app.postRunnable(() -> {
                List<String> crazyCurveModules = response.stream().filter(moduleEntry -> moduleEntry.contains("sdu.cbse.group2")).collect(Collectors.toList());
                getGame().getGameStateManager().push(new SettingsState(getGame(), crazyCurveModules));
            }));
        }
    }
}
