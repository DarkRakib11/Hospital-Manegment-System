public class Nurse extends Person {

    private String shift;

    public Nurse(String id, String name, String shift) {

        super(id, name);

        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public String getRoleInfo() {
        return "Nurse: " + getName()
                + " | ID: " + getId()
                + " | Shift: " + shift;
    }

    public String toFileString() {
        return getId() + ","
                + getName() + ","
                + shift;
    }
}