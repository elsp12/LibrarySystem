package finalproject;

import java.io.Serializable;

public abstract class Person implements Serializable {
	protected String name;
    protected String id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public abstract int maxBorrowLimit();
    public String getName() { return name; }
}
