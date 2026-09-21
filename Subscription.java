

public class Subscription {
	private String subCode;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	
	
	public Subscription(String subCode, String firstName, String lastName, String address, String phone) {
		super();
		this.subCode = subCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Subscription [subCode=" + subCode + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", phone=" + phone + "]";
	}
	
	

}
