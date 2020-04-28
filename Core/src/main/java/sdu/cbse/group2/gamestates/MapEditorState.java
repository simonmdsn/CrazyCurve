package sdu.cbse.group2.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import sdu.cbse.group2.Game;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.Text;
import sdu.cbse.group2.common.data.Tile;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.common.services.ObstacleService;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapEditorState extends State {
    private Stage stage;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private Map<Button, ObstacleService> buttonObstacleService = new HashMap<>();
    private Entity selectedEntity;
    private Drawable rectangleWithWhiteCorners = new TextureRegionDrawable(new TextureRegion((Texture) getGame().getAssets().getAssetManager().get("textures/menustate/inputfield.png")));
    private String selectedMap;
    private boolean isGridToggled;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();


    protected MapEditorState(Game game) {
        super(game);
        stage = new Stage();
//        Entity entity = new Entity(new GameSprite("textures/map/background.png", 1000, 1300, -1));
//        entity.add((new PositionPart(0, 0, 0)));
//        getGame().getWorld().addEntity(entity);
        buttonGroup.setMinCheckCount(0);
        for (int i = 0; i < game.getObstacleServiceList().size(); i++) {
            buttonGroup.add(createObstacleToggleButton(game.getObstacleServiceList().get(i), getGame().getGameData().getDisplayWidth() - 250, getGame().getGameData().getDisplayHeight() - 50 * (i + 1)));
        }
        buttonGroup.getButtons().forEach(button -> stage.addActor(button));

        Button playMapBtn = new TextButton("Play map", new TextButton.TextButtonStyle(rectangleWithWhiteCorners, rectangleWithWhiteCorners, rectangleWithWhiteCorners, new BitmapFont()));
        playMapBtn.setSize(200, 30);
        playMapBtn.setPosition(getGame().getGameData().getDisplayWidth() - 250, 50);

        TextField nameTextField = new TextField("", new TextField.TextFieldStyle(new BitmapFont(), Color.WHITE, null, null, rectangleWithWhiteCorners));
        nameTextField.getStyle().background.setLeftWidth(5);
        nameTextField.setSize(200, 30);
        nameTextField.setPosition(getGame().getGameData().getDisplayWidth() - 250, 150);
        nameTextField.setMessageText("Map name");

        Button saveMapBtn = new TextButton("Save map", new TextButton.TextButtonStyle(rectangleWithWhiteCorners, rectangleWithWhiteCorners, rectangleWithWhiteCorners, new BitmapFont()));
        saveMapBtn.setSize(200, 30);
        saveMapBtn.setPosition(getGame().getGameData().getDisplayWidth() - 250, 120);

        Button loadMapBtn = new TextButton("Load map", new TextButton.TextButtonStyle(rectangleWithWhiteCorners, rectangleWithWhiteCorners, rectangleWithWhiteCorners, new BitmapFont()));
        loadMapBtn.setSize(200, 30);
        loadMapBtn.setPosition(getGame().getGameData().getDisplayWidth() - 250, 200);

        Table container = new Table();
        Table mapsTable = new Table();
        ScrollPane mapsScrollPane = new ScrollPane(mapsTable, new ScrollPane.ScrollPaneStyle(rectangleWithWhiteCorners, null, null, null, null));
        container.add(mapsScrollPane).width(200).height(200);
        container.row();
        container.setBounds(getGame().getGameData().getDisplayWidth() - 250, 230, 200, 200);

        Button clearMapBtn = new TextButton("Clear map", new TextButton.TextButtonStyle(rectangleWithWhiteCorners, rectangleWithWhiteCorners, rectangleWithWhiteCorners, new BitmapFont()));
        clearMapBtn.setSize(200, 30);
        clearMapBtn.setPosition(getGame().getGameData().getDisplayWidth() - 250, 20);

        Button toggleGridBtn = new TextButton("Toggle grid", new TextButton.TextButtonStyle(rectangleWithWhiteCorners, rectangleWithWhiteCorners, rectangleWithWhiteCorners, new BitmapFont()));
        toggleGridBtn.setSize(200, 30);
        toggleGridBtn.setPosition(getGame().getGameData().getDisplayWidth() - 250, 430);

        //Button listeners...
        saveMapBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().getEditorService().serialize(nameTextField.getText(), getGame().getGameData(), getGame().getWorld());
                loadMaps(mapsTable);
            }
        });
        loadMapBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedMap != null) {
                    getGame().getWorld().getEntities().clear();
                    getGame().getEditorService().deserialize(selectedMap, getGame().getGameData(), getGame().getWorld());
                }
            }
        });

        playMapBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                getGame().getGameData().setPlayerName("Player");
                getGame().getWorld().getTextList().clear();
                stage.dispose();
                getGame().getGameStateManager().set(new PlayState(game));
            }
        });
        clearMapBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getGame().getWorld().getEntities().forEach(entity -> getGame().getWorld().removeEntity(entity));
            }
        });
        toggleGridBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isGridToggled = !isGridToggled;
            }
        });

        loadMaps(mapsTable);

        stage.addActor(playMapBtn);
        stage.addActor(loadMapBtn);
        stage.addActor(saveMapBtn);
        stage.addActor(nameTextField);
        stage.addActor(container);
        stage.addActor(clearMapBtn);
        stage.addActor(toggleGridBtn);

        Gdx.input.setInputProcessor(stage);
    }

    private void loadMaps(Table table) {
        table.clear();
        for (File file : getGame().getEditorService().getMaps()) {
            TextButton button = new TextButton(file.getName().substring(0, file.getName().length() - 4), new TextButton.TextButtonStyle(rectangleWithWhiteCorners, rectangleWithWhiteCorners, rectangleWithWhiteCorners, new BitmapFont()));
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Loaded map: " + button.getText().toString());
                    selectedMap = button.getText().toString();
                }
            });
            table.add(button).width(190).height(20);
            table.row();
        }
    }

    private Button createObstacleToggleButton(ObstacleService obstacleService, float x, float y) {
        Texture btnTexture = getGame().getAssets().getAssetManager().get(obstacleService.getGameSprite().getImagePath());
        Button button = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnTexture)));
        button.setSize(50, 50);
        button.setPosition(x, y);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedEntity = obstacleService.create(0, 0);
            }
        });
        buttonObstacleService.putIfAbsent(button, obstacleService);
        getGame().getWorld().addText(new Text(obstacleService.getObstacleName(), x + 80, y + 30));
        return button;
    }

    private void draw(SpriteBatch spriteBatch) {
        stage.draw();
        spriteBatch.begin();
        for (Entity entity : getGame().getWorld().getEntities().stream().sorted(Comparator.comparingInt(o -> o.getGameSprite().getLayer())).collect(Collectors.toList())) {
            GameSprite gameSprite = entity.getGameSprite();
            Texture texture = getGame().getAssets().getAssetManager().get(gameSprite.getImagePath(), Texture.class);
            drawSprite(gameSprite, entity.getPart(PositionPart.class), texture);
        }
        if (mouseInPlayWindow() && selected()) {
            displaySelectedObstacle();
        }
        for (Text text : getGame().getWorld().getTextList()) {
            drawText(text);
        }
        spriteBatch.end();
    }

    private void drawText(Text text) {
        getGame().getAssets().getBitmapFont().draw(getGame().getAssets().getBatch(), text.getText(), text.getX(), text.getY());
    }

    private void displaySelectedObstacle() {
        PositionPart part = selectedEntity.getPart(PositionPart.class);
        part.setX(Gdx.input.getX());
        part.setY(getGame().getGameData().getDisplayHeight() - Gdx.input.getY());
    }

    private boolean selected() {
        return buttonGroup.getAllChecked().size == 1;
    }

    private boolean mouseInPlayWindow() {
        if (Gdx.input.getX() < getGame().getGameData().getDisplayHeight() + 10) {
            if (selectedEntity != null && !getGame().getWorld().getEntities().contains(selectedEntity)) {
                getGame().getWorld().addEntity(selectedEntity);
            }
            return true;
        } else {
            if (selectedEntity != null && getGame().getWorld().getEntities().contains(selectedEntity)) {
                getGame().getWorld().removeEntity(selectedEntity);
            }
            return false;
        }
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
        sprite.draw(getGame().getAssets().getBatch());
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
        //Check if mouse is pressed, creates new entity based on the selected obstacle toggle button, uses the selected tile to specify the position part of the newly added obstacle.
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && mouseInPlayWindow() && selected()) {
            Entity newEntity = new Entity(buttonObstacleService.get(buttonGroup.getChecked()).getGameSprite());
            Tile nearestTile = getGame().getWorld().getNearestTile(Gdx.input.getX(), getGame().getGameData().getDisplayHeight() - Gdx.input.getY(),getGame().getGameData());
            if (nearestTile != null) {
                newEntity.add(nearestTile.getPositionPart());
                getGame().getWorld().getTilesEntityMap().put(nearestTile, newEntity);
                getGame().getWorld().addEntity(newEntity);
            }
        }
        if (isGridToggled) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.WHITE);
            for (Tile[] tileRow : getGame().getWorld().getTiles()) {
                for (Tile tile : tileRow) {
                    shapeRenderer.line(tile.getPositionPart().getX(), tile.getPositionPart().getY(), tile.getPositionPart().getX(), tile.getPositionPart().getY() + Tile.length);
                    shapeRenderer.line(tile.getPositionPart().getX(), tile.getPositionPart().getY(), tile.getPositionPart().getX() + Tile.length, tile.getPositionPart().getY());
                }
            }
            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {
        this.dispose();
    }
}
