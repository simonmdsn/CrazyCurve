package sdu.cbse.group2.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;

import java.io.File;

@Getter
public class Assets {

    private final Batch batch = new SpriteBatch();
    private final BitmapFont bitmapFont = new BitmapFont();
    private final AssetManager assetManager;


    public Assets() {
        assetManager = new AssetManager();
        loadAssets();
        assetManager.finishLoading();
    }

    public void loadAssets() {
        assetManager.load("player/player.png", Texture.class);
        assetManager.load("player/tail.png", Texture.class);
        assetManager.load("enemy/enemy.png", Texture.class);
        assetManager.load("enemy/tail.png", Texture.class);
        assetManager.load("MenuState/start button.png", Texture.class);
        assetManager.load("MenuState/quit button.PNG", Texture.class);
        assetManager.load("MenuState/settings button.PNG", Texture.class);
        assetManager.load("MenuState/title.png", Texture.class);
        assetManager.load("powerup/speed.png", Texture.class);
        assetManager.load("powerup/turtle.png", Texture.class);
        assetManager.load("powerup/eraser.png", Texture.class);
        assetManager.load("items/tongue-long.png", Texture.class);
        assetManager.load("items/tongue-short.png", Texture.class);
        assetManager.load("round/scoretext_background.png",Texture.class);
        //assetManager.load("obstacles/water/water1.png",Texture.class);
        //assetManager.load("obstacles/water/water2.png",Texture.class);
        //assetManager.load("obstacles/water/water3.png",Texture.class);
    }

    //TODO recursive iteration over the resource directory (should we do it or not).
    private void iterateFiles(File file) {
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                iterateFiles(listFile);
            }
        } else {
            assetManager.load(file.getAbsolutePath(), Texture.class);
        }
    }
}
