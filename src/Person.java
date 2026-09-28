// Parent class
public abstract class Person {

    private String id;
    private String name;

    // Constructor
    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Abstraction
    public abstract String getRoleInfo();
}