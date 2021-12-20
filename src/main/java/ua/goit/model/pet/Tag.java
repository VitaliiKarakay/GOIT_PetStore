package ua.goit.model.pet;

public class Tag {

    private int id;
    private String name;
    public static int idCounter = 1;

    public Tag() {
        this.id = idCounter;
        counterAdd();
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void counterAdd() {
        idCounter++;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
