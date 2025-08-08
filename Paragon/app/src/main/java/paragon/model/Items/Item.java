package paragon.model.Items;

//Clase para definir objeto Item
public class Item {
    private String name;
    private int plus;
    private int heal;

    public String getName() {
        return name;
    }

    public int getPlus() {
        return plus;
    }
    public int getHeal() {
        return heal;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", plus=" + plus +
                ", heal=" + heal +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Item))
            return false;
        Item item = (Item) obj;
        if (!name.equals(item.name))
            return false;
        if (plus != item.plus)
            return false;
        if (heal != item.heal)
            return false;
        return true;
    }
}
