package paragon.model.Habilities;

import com.google.gson.annotations.SerializedName;
//Clase pare definir objeto Ability
public class Ability {
    @SerializedName("nombre") //Estas anotaciones @SerializedName se emplean ya que en el JSON de habilidades, las variables tiene otros nombres
    private String name;        //Por lo tanto, se debe indicar a gson que ese nombre que lee ahí corresponde a cierto atributo del objeto
    @SerializedName("costo_mana")
    private int manaCost;
    @SerializedName("daño")
    private int damage;

    public String getName() {
        return name;
    }
    public int getManaCost() {
        return manaCost;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "Habilidad{" +
                "name='" + name + '\'' +
                ", manaCost='" + manaCost + '\'' +
                ", damage=" + damage +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Ability))
            return false;
        Ability habilidad = (Ability) obj;
        if (!name.equals(habilidad.name))
            return false;
        if (manaCost != habilidad.manaCost)
            return false;
        return damage == habilidad.damage;
    }

}