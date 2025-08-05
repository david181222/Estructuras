package sounloud.model;

import java.awt.Desktop;
import java.util.List;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Queueplay {
    private static final Logger logger = LogManager.getLogger(Queueplay.class);
    private Queue<Playlist> playlistQueue;

    public Queueplay() {
        playlistQueue = new LinkedList<>();
    }

    public void addPlaylist(Playlist playlist) {
        try {
            playlistQueue.add(playlist);
            logger.info("Playlist agregada a la cola: {}", playlist.getNamePlaylist());
        } catch (Exception e) {
            logger.error("Error al agregar playlist: {}", e.getMessage());
        }
    }

    public void showPlaylists() {
        try {
            int i = 1;
            for (Playlist playlist : playlistQueue) {
                System.out.println(i + ". " + playlist.getNamePlaylist());
                i++;
            }
        } catch (Exception e) {
            logger.error("Error al mostrar playlists: {}", e.getMessage());
        }
    }

    public void showSongs(Playlist playlistIndex) {
        try {
            logger.info("Mostrando canciones...");
            int i = 1;
            for (Song song : playlistIndex.getSongs()) {
                System.out.println(i + ". " + song.getName());
                i++;
            }
            logger.info("Canciones mostradas.");
        } catch (Exception e) {
            logger.error("Error al mostrar canciones de la playlist: {}", e.getMessage());
        }
    }

    public void startListening() {
        double songDuration;
        int timePerSong;

        while (!playlistQueue.isEmpty()) {
            Playlist playlistToPlay = playlistQueue.poll();
            try {
                playlistToPlay.showSongs();
                System.out.println("Reproduciendo playlist: " + playlistToPlay.getNamePlaylist());

                Queue<Song> songsQueue = new LinkedList<>(playlistToPlay.getSongs());
                System.out.println("Cantidad de canciones: " + playlistToPlay.getSongs().size());

                while (!songsQueue.isEmpty()) {
                    Song songToPlay = songsQueue.poll();
                    String url = songToPlay.getUrlYoutube();

                    try {
                        URI uri = new URI(url);
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().browse(uri);
                            logger.info("Reproduciendo canción {}", songToPlay.getName());
                            System.out.println("Reproduciendo: " + songToPlay.getName() + " | " + songToPlay.getAuthor()
                                    + " | " + songToPlay.getDurationMinutes());
                        } else {
                            throw new UnsupportedOperationException("No se reprodujo la canción");
                        }

                        songDuration = songToPlay.getDurationMinutes() * 60;
                        timePerSong = (int) (Math.ceil(songDuration));
                        Thread.sleep(timePerSong * 1000);

                    } catch (Exception e) {
                        logger.error("No se puede abrir navegador o error en reproducción: {}", e.getMessage());
                        
                    }
                }

                logger.info("Acabó la reproducción de la playlist {}", playlistToPlay.getNamePlaylist());
                System.out.println("Fin de la playlist.");

            } catch (Exception e) {
                logger.error("Error al reproducir playlist: {}", e.getMessage());
                throw e;
            }
        }

        logger.info("Acabó la reproducción de la cola");
        System.out.println("Fin de la cola de reproducción.");
    }

    public void startListeningRandom() {
        List<Playlist> playlistList = new ArrayList<>();
        try {
            for (Playlist playlist : this.playlistQueue) {
                playlistList.add(playlist);
            }
            logger.info("Seleccionando playlist y canción randoms...");

            int indexPlaylistRandom = (int) (Math.random() * this.playlistQueue.size());
            Playlist playlistToPlay = playlistList.get(indexPlaylistRandom);
            int indexSongRandom = (int) (Math.random() * playlistToPlay.getSongs().size());
            Song songToPlay = playlistToPlay.getSongs().get(indexSongRandom);
            String url = songToPlay.getUrlYoutube();

            try {
                URI uri = new URI(url);
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(uri);
                    logger.info("Reproduciendo canción {}", songToPlay.getName());
                    System.out.println("Reproduciendo: " + songToPlay.getName() + " | " + songToPlay.getAuthor() + " | "
                            + songToPlay.getDurationMinutes());
                } else {
                    throw new UnsupportedOperationException("No se reprodujo la canción");
                }

            } catch (Exception e) {
                logger.error("No se puede abrir navegador o error en reproducción: {}", e.getMessage());
                
            }

        } catch (Exception e) {
            logger.error("Error al seleccionar playlist o canción aleatoria: {}", e.getMessage());
            throw e;
        }
    }

    public void startListeningSelectedSong(Song song) {
        try {
            String url = song.getUrlYoutube();
            URI uri = new URI(url);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
                logger.info("Reproduciendo canción {}", song.getName());
                System.out.println("Reproduciendo: " + song.getName() + " | " + song.getAuthor() + " | "
                        + song.getDurationMinutes());
            } else {
                throw new UnsupportedOperationException("No se reprodujo la canción");
            }

        } catch (Exception e) {
            logger.error("No se puede abrir navegador o error en reproducción: {}", e.getMessage());
            
        }
    }
}
