package sounloud.model;

import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Clase que define una canción
public class Song {
    public static final Logger logger = LogManager.getLogger(Song.class);
    private String IdSong;
    private String NameSong;
    private String Author;
    private double DurationMinutes;
    private String UrlYoutube;

    public Song(String NameSong, String Author, double DurationMinutes, String UrlYoutube) {
        try {
            if (NameSong == null || NameSong.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
            }
            if (Author == null || Author.isEmpty()) {
                throw new IllegalArgumentException("El autor no puede ser nulo o vacío");
            }
            if (DurationMinutes <= 0) {
                throw new IllegalArgumentException("La duración debe ser mayor a 0");
            }
            if (UrlYoutube == null || UrlYoutube.isEmpty()) {
                throw new IllegalArgumentException("La URL de YouTube no puede ser nula o vacía");
            }

            logger.info("Creando canción {}", NameSong);
            this.IdSong = UUID.randomUUID().toString();
            this.NameSong = NameSong;
            this.Author = Author;
            this.DurationMinutes = DurationMinutes;
            this.UrlYoutube = UrlYoutube;
            logger.info("Canción {} creada con éxito", NameSong);
        } catch (Exception e) {
            logger.error("Fallo al crear canción {}", NameSong);
            throw e;
        }
    }

    public String getIdSong() {
        // Si el ID es null (canción cargada desde JSON), generar uno nuevo
        if (IdSong == null) {
            IdSong = UUID.randomUUID().toString();
            logger.debug("Generando ID para canción cargada desde JSON: {}", NameSong);
        }
        return IdSong;
    }

    public String getName() {
        return NameSong;
    }

    public String getAuthor() {
        return Author;
    }

    public double getDurationMinutes() {
        return DurationMinutes;
    }

    public String getUrlYoutube() {
        return UrlYoutube;
    }

    @Override
    public String toString() {
        return "Song{" +
                "NameSong='" + NameSong + '\'' +
                ", Author='" + Author + '\'' +
                ", DurationMinutes=" + DurationMinutes +
                ", UrlYoutube='" + UrlYoutube + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Song))
            return false;

        Song song = (Song) obj;

        if (!NameSong.equals(song.NameSong))
            return false;
        if (!Author.equals(song.Author))
            return false;
        if (DurationMinutes != song.DurationMinutes)
            return false;
        if (!UrlYoutube.equals(song.UrlYoutube))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(NameSong, Author, DurationMinutes, UrlYoutube);
    }
}
