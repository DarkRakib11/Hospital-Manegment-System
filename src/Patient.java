public class Patient extends Person {

    private int age;
    private String disease;
    private double totalBill;
    private double paidAmount;

    public Patient(String id, String name, int age, String disease,
                   double totalBill, double paidAmount) {

        super(id, name);

        this.age = age;
        this.disease = disease;
        this.totalBill = totalBill;
        this.paidAmount = paidAmount;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getDueAmount() {
        return totalBill - paidAmount;
    }

    @Override
    public String getRoleInfo() {
        return "Patient: " + getName()
                + " | ID: " + getId()
                + " | Disease: " + disease;
    }

    public String toFileString() {
        return getId() + ","
                + getName() + ","
                + age + ","
                + disease + ","
                + totalBill + ","
                + paidAmount;
    }
}