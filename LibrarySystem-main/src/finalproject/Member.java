package finalproject;

public class Member extends Person {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String membershipType;

    public Member(String name, String id, String membershipType) {
        super(name, id);
        this.membershipType = membershipType;
    }

    @Override
    public int maxBorrowLimit() {
        return membershipType.equalsIgnoreCase("Premium") ? 5 : 3;
    }

    @Override
    public String toString() {
        return name + " (" + membershipType + ")";
    }
}