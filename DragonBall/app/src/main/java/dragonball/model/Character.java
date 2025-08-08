package dragonball.model;

public class Character {
    private int id;
    private String name;
    private String ki;
    private String maxKi;
    private String race;
    private String gender;
    private String description;
    private String image;
    private String affiliation;
    private String deletedAt;

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKi() {
        return ki;
    }

    public String getMaxKi() {
        return maxKi;
    }

    public String getRace() {
        return race;
    }

    public String getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    // Setters (opcional, si los necesitas)
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKi(String ki) {
        this.ki = ki;
    }

    public void setMaxKi(String maxKi) {
        this.maxKi = maxKi;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    // toString()
    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ki='" + ki + '\'' +
                ", maxKi='" + maxKi + '\'' +
                ", race='" + race + '\'' +
                ", gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
