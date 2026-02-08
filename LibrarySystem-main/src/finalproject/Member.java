package finalproject;

public class Member extends Person {

    private String email;
    private String phone;
    private String membershipType;

    private boolean suspended;
    private int suspensionCount;

    /* ================= CONSTRUCTORS ================= */

    // New member from UI (DB generates ID)
    public Member(String name, String email, String phone, String membershipType) {
        super(name);
        this.email = email;
        this.phone = phone;
        this.membershipType = membershipType;
        this.suspended = false;
        this.suspensionCount = 0;
    }

    // Member loaded from DB
    public Member(int id, String name, String email, String phone,
                  String membershipType, boolean suspended, int suspensionCount) {
        super(id, name);
        this.email = email;
        this.phone = phone;
        this.membershipType = membershipType;
        this.suspended = suspended;
        this.suspensionCount = suspensionCount;
    }

    /* ================= BUSINESS RULES ================= */

    public int getMaxBorrowLimit() {
        return membershipType.equalsIgnoreCase("Premium") ? 5 : 3;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void suspend() {
        this.suspended = true;
        this.suspensionCount++;
    }

    public void reinstate() {
        this.suspended = false;
    }

    public int getSuspensionCount() {
        return suspensionCount;
    }

    /* ================= GETTERS ================= */

    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getMembershipType() { return membershipType; }

    @Override
    public String toString() {
        return getName() + " (" + membershipType + ")" +
               (suspended ? " [Suspended]" : "");
    }
}
