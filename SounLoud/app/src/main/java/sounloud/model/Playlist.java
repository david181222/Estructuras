package sounloud.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Playlist {
    public static final Logger logger = LogManager.getLogger(Playlist.class);
    private UUID IdPlaylist;
    private String NamePlaylist;
    private String DescriptionPlaylist;
    private List<Song> Songs;

    public Playlist(String NamePlaylist, String DescriptionPlaylist, List<Song> Songs) {
        try {
            if (NamePlaylist == null || NamePlaylist.isEmpty()) {
                throw new IllegalArgumentException("El nombre de la playlist no puede ser nulo o vacío");
            }
            if (DescriptionPlaylist == null || DescriptionPlaylist.isEmpty()) {
                throw new IllegalArgumentException("La descripción de la playlist no puede ser nula o vacía");
            }
            if (Songs == null) {
                throw new IllegalArgumentException("La lista de canciones no puede ser nula");
            }

            logger.info("Creando playlist {}", NamePlaylist);
            this.IdPlaylist = UUID.randomUUID();
            this.NamePlaylist = NamePlaylist;
            this.DescriptionPlaylist = DescriptionPlaylist;
            this.Songs = Songs;
            logger.info("Playlist {} creada con éxito", NamePlaylist);
        } catch (Exception e) {
            logger.error("Fallo al crear playlist {}", NamePlaylist);
            throw e;
        }
    }

    public UUID getIdPlaylist() {
        return IdPlaylist;
    }

    public String getNamePlaylist() {
        return NamePlaylist;
    }

    public String getDescriptionPlaylist() {
        return DescriptionPlaylist;
    }

    public List<Song> getSongs() {
        return Songs;
    }

    public void addSong(Song song) {
        this.Songs.add(song);
    }

    public void removeSong(Song song) {

        for (Song s : this.Songs) {
            logger.info("Buscando canción {} para eliminar", song.getName());
            if (s.getName().equals(song.getName())) {
                this.Songs.remove(song);
                break;
            } else{
                System.out.println("No se encontró la canción"+ song.getName());
            }

        }
    }

    public void showSongs() {
        int i = 1;
        for (Song song : this.Songs) {
            System.out.println(i + ". " + song.getName());
            i++;
        }
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "NamePlaylist='" + NamePlaylist + '\'' +
                ", DescriptionPlaylist='" + DescriptionPlaylist + '\'' +
                ", Songs=" + Songs +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Playlist))
            return false;

        Playlist playlist = (Playlist) obj;

        if (!NamePlaylist.equals(playlist.NamePlaylist))
            return false;
        if (!DescriptionPlaylist.equals(playlist.DescriptionPlaylist))
            return false;
        if (!Songs.equals(playlist.Songs))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(NamePlaylist, DescriptionPlaylist, Songs);
    }
}
