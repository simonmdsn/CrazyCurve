package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import sdu.cbse.group2.Game;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

public class MapEditorState extends State {
    private Stage stage;
    private ButtonGroup buttonGroup = new ButtonGroup();

    protected MapEditorState(Game game) {
        super(game);
        stage = new Stage();
        buttonGroup.add(createObstacleToggleButton(new Entity(new GameSprite("player/tail.png",30,30)),100,100),
                createObstacleToggleButton(new Entity(new GameSprite("player/player.png",30,30)),200,100)

        );
        buttonGroup.getButtons().forEach(button -> stage.addActor(button));

        Gdx.input.setInputProcessor(stage);
    }

    private Button createObstacleToggleButton(Entity entity, int x, int y) {
        Texture btnTexture = game.getAssets().getAssetManager().get(entity.getGameSprite().getImagePath());
        Button button = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnTexture)));
        button.setSize(50,50);
        button.setPosition(x,y);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(button.isChecked());
            }
        });
        return button;
    }

    private void draw(SpriteBatch spriteBatch) {
        stage.draw();
        spriteBatch.begin();
        for (Entity entity : game.getWorld().getEntities()) {
            GameSprite gameSprite = entity.getGameSprite();
            Texture texture = game.getAssets().getAssetManager().get(gameSprite.getImagePath(), Texture.class);
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
        sprite.draw(game.getAssets().getBatch());
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        draw(spriteBatch);
    }

    @Override
    public void dispose() {
        this.dispose();
    }


}
