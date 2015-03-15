package data;

public class User {
	private int userId;
	private String loginName;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String userType;
	private String userManager;
	private String billingManager;
	private String inventoryManager;
	private String onlineLogin;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserManager() {
		return userManager;
	}
	public void setUserManager(String userManager) {
		this.userManager = userManager;
	}
	public String getBillingManager() {
		return billingManager;
	}
	public void setBillingManager(String billingManager) {
		this.billingManager = billingManager;
	}
	public String getInventoryManager() {
		return inventoryManager;
	}
	public void setInventoryManager(String inventoryManager) {
		this.inventoryManager = inventoryManager;
	}
	public String getOnlineLogin() {
		return onlineLogin;
	}
	public void setOnlineLogin(String onlineLogin) {
		this.onlineLogin = onlineLogin;
	}
	
}
