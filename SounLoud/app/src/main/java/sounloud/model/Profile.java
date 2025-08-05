package sounloud.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profile {
    public static final Logger logger = LogManager.getLogger(Profile.class);
    private UUID IdProfile;
    private String Name;
    private String Description;
    private String FavoriteGenre;
    private List<Playlist> Playlist;

    public Profile(String Name, String Description, String FavoriteGenre) {
        try {
            if (Name == null || Name.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
            }
            if (Description == null || Description.isEmpty()) {
                throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
            }
            if (FavoriteGenre == null || FavoriteGenre.isEmpty()) {
                throw new IllegalArgumentException("El género favorito no puede ser nulo o vacío");
            }
           

            logger.info("Creando perfil {}", Name);
            this.IdProfile = UUID.randomUUID();
            this.Name = Name;
            this.Description = Description;
            this.FavoriteGenre = FavoriteGenre;
            this.Playlist = new ArrayList<>();
            logger.info("Perfil {} creado con éxito", Name);
        } catch (Exception e) {
            logger.error("Fallo al crear perfil {}", Name);
            throw e;
        }

    }

    public UUID getIdProfile() {
        return IdProfile;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getFavoriteGenre() {
        return FavoriteGenre;
    }

    public List<Playlist> getPlaylist() {
        return Playlist;
    }
    public void showPlaylists() {
        int i = 1;
        for (Playlist playlist : this.Playlist) {
            System.out.println(i + ". " + playlist.getNamePlaylist());
            i++;
        }
    }

    public void setPlaylist(Playlist playlist){
        this.Playlist.add(playlist);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", FavoriteGenre='" + FavoriteGenre + '\'' +
                ", Playlist=" + Playlist +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Profile))
            return false;

        Profile profile = (Profile) obj;

        if (!Name.equals(profile.Name))
            return false;
        if (!Description.equals(profile.Description))
            return false;
        if (!FavoriteGenre.equals(profile.FavoriteGenre))
            return false;
        if (!Playlist.equals(profile.Playlist))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Description, FavoriteGenre, Playlist);
    }
}
