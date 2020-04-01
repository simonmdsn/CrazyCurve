package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sdu.cbse.group2.Assets;
import sdu.cbse.group2.Game;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.World;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.IEntityProcessingService;
import sdu.cbse.group2.common.services.IGamePluginService;
import sdu.cbse.group2.common.services.IPostEntityProcessingService;
import sdu.cbse.group2.core.managers.GameInputProcessor;

public class PlayState extends State {

    World world;
    Game game;

    public PlayState(GameStateManager gameStateManager, GameData gameData, Assets assets, World world){
        super(gameStateManager, gameData, assets);
        this.world = world;
        //To enable button pressing
        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        //Check how to entities are initialised etc
        System.out.println("updating play state");
        update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        System.out.println("rendering play state");
        draw(spriteBatch);
    }

    @Override
    public void dispose() {

    }

    private void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Entity entity : world.getEntities()) {
            GameSprite gameSprite = entity.getGameSprite();
            Texture texture = assets.getAssetManager().get(gameSprite.getImagePath(), Texture.class);
            drawSprite(gameSprite, entity.getPart(PositionPart.class), texture);
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
        sprite.draw(assets.getBatch());
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : game.getEntityProcessorList()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : game.getPostEntityProcessorList()) {
            postEntityProcessorService.process(gameData, world);
        }
    }
}