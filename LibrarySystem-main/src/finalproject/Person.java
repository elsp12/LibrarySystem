package finalproject;

public abstract class Person {
	protected String name;
    protected String id;

 // Constructor for new person (ID not yet assigned)
    protected Person(String name) {
        this.name = name;
    }

 // Constructor for loading from DB (ID assigned)
    protected Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
