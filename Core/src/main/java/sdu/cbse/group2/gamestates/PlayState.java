package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sdu.cbse.group2.Game;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.Text;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.core.managers.GameInputProcessor;

public class PlayState extends State {

    public PlayState(Game game) {
        super(game);
        //To enable button pressing
        Gdx.input.setInputProcessor(new GameInputProcessor(game.getGameData()));
        game.getGamePluginList().forEach(iGamePluginService -> iGamePluginService.start(game.getGameData(), game.getWorld()));
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        game.getGameData().getKeys().update();
        draw(spriteBatch);
    }

    @Override
    public void dispose() {
        //Reset everything in the game
    }

    private void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Entity entity : game.getWorld().getEntities()) {
            GameSprite gameSprite = entity.getGameSprite();
            Texture texture = game.getAssets().getAssetManager().get(gameSprite.getImagePath(), Texture.class);
            drawSprite(gameSprite, entity.getPart(PositionPart.class), texture);
        }
        for (Text text :  game.getWorld().getTextList()) {
            drawText(text);
        }
        spriteBatch.end();
    }

    private void drawSprite(GameSprite gameSprite, PositionPart positionPart, Texture texture) {
        //TODO sprite needs things
        //                GameImage image = entity.getImage();
        //                Texture tex = assetManager.get(image.getImagePath(), Texture.class);
        //                PositionPart p = entity.getPart(PositionPart.class);
        //                drawSprite(new Sprite(tex), image, p);
        Sprite sprite = new Sprite(texture);
        sprite.setOrigin(gameSprite.getWidth() / 2, gameSprite.getHeight() / 2);
        sprite.rotate((float) Math.toDegrees(positionPart.getRadians()));
        sprite.setX(positionPart.getX());
        sprite.setY(positionPart.getY());
        sprite.setSize(gameSprite.getWidth(), gameSprite.getHeight());
        sprite.draw(game.getAssets().getBatch());
    }

    private void drawText(Text text) {
        BitmapFont.TextBounds bounds = game.getAssets().getBitmapFont().getBounds(text.getText());
        game.getAssets().getBitmapFont().draw(game.getAssets().getBatch(),text.getText(),text.getX() - bounds.width / 2,text.getY());
    }

    private void update() {
        // Update

        for (IEntityProcessingService entityProcessorService : game.getEntityProcessorList()) {
            entityProcessorService.process(game.getGameData(), game.getWorld());
        }
        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : game.getPostEntityProcessorList()) {
            postEntityProcessorService.process(game.getGameData(), game.getWorld());
        }
    }
}
