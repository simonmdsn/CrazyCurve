package sdu.cbse.group2.common.services;

public interface SoundSPI {

    void playSound(String soundPath, float volume, boolean autoplay);

    void playMusic(String soundPath, float volume, boolean autoplay);
}
