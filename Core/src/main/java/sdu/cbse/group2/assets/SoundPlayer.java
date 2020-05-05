package sdu.cbse.group2.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import sdu.cbse.group2.common.services.SoundSPI;

public class SoundPlayer implements SoundSPI {

    @Override
    public void playSound(String soundPath, float volume, boolean autoplay) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
        sound.setVolume(0, volume);
        sound.setLooping(0,autoplay);
        sound.play();
    }

    @Override
    public void playMusic(String soundPath, float volume, boolean autoplay) {
        Music music = Gdx.audio.newMusic(Gdx.files.internal(soundPath));
        music.setVolume(volume);
        music.setLooping(autoplay);
        music.play();
    }
}
