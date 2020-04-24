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
        iterateFiles(new File("../LibGDX/src/main/resources/textures/"));
    }

    //Loads all texture assets at "../LibGDX/src/main/resources/textures"
    private void iterateFiles(File file) {
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                iterateFiles(listFile);
            }
        } else {
            assetManager.load(file.getPath().substring("../LibGDX/src/main/resources/".length()), Texture.class);
        }
    }
}
