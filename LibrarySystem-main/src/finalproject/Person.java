package finalproject;

public abstract class Person {

    protected int id;        // DB primary key
    protected String name;

    // New person from UI (DB generates id)
    protected Person(String name) {
        this.name = name;
    }

    // Person loaded from DB (id already exists)
    protected Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void setId(int id) { this.id = id; }
}
