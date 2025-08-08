package sounloud.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Clase que define al usuario
public class User {
    public static final Logger logger = LogManager.getLogger(User.class);

    private String IdUser;
    private String NameUser;
    private List<Profile> UserProfiles;

    
    public User(String NameUser) {
        try {
            if (NameUser == null || NameUser.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de usuario no puede ser nulo o vacío");
            }

            logger.info("Creando usuario {}", NameUser);
            this.IdUser = UUID.randomUUID().toString();
            this.NameUser = NameUser;
            this.UserProfiles = new ArrayList<>();
            logger.info("Usuario {} creado con éxito", NameUser);
        } catch (Exception e) {
            logger.error("Fallo al crear usuario {}", NameUser);
            throw e;
        }
    }

    public String getIdUser() {
        return IdUser;
    }

    public String getNameUser() {
        return NameUser;
    }

    public List<Profile> getUserProfiles() {
        return UserProfiles;
    }

    public void setProfile(Profile profile) {
        this.UserProfiles.add(profile);
    }

    public void showProfiles() {
        int i = 1;
        for (Profile profile : this.UserProfiles) {
            System.out.println(i + ". " + profile.getName());
            i++;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "NameUser='" + NameUser + '\'' +
                ", UserProfiles=" + UserProfiles +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof User))
            return false;

        User user = (User) obj;

        if (!NameUser.equals(user.NameUser))
            return false;
        if (!UserProfiles.equals(user.UserProfiles))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(NameUser, UserProfiles);
    }
}
