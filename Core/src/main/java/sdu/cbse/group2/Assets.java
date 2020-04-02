package sdu.cbse.group2;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;

import java.io.File;

@Getter
public class Assets {

    private final Batch batch = new SpriteBatch();
    private final AssetManager assetManager = new AssetManager();

    public Assets() {
        loadAssets();
        assetManager.finishLoading();
        System.out.println("Assets: " + assetManager.getAssetNames());
    }

    public void loadAssets() {
        assetManager.load("Player/player.png", Texture.class);
        assetManager.load("Player/tail.png", Texture.class);
        assetManager.load("MenuState/start button.png", Texture.class);
        assetManager.load("MenuState/quit button.PNG", Texture.class);
        assetManager.load("MenuState/settings button.PNG", Texture.class);
        assetManager.load("MenuState/title.png", Texture.class);
        assetManager.load("powerup/speed.png", Texture.class);
        assetManager.load("powerup/turtle.png", Texture.class);
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
