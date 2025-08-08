package sounloud.interfaces;

import sounloud.model.Song;

public interface IQueueplay {
    void startListening();
    void startListeningRandom();
    void startListeningSelectedSong(Song song);   
}
