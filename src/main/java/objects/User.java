package objects;

public class User {
	
	private String name;
	private String username;
	private String password; //needs to be hashed
	
	public User() {
		
	}
	
	public User(String name, String user, String pass) {
		this.name = name;
		this.username = user;
		this.password = pass;
	}
	
	@Override
	public String toString() {
		
		return ("\n" +"Name: "+this.name+ "\n"
				+ "Username: " +this.username+ "\n"
				+ "Password:" +this.password);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUsername(String input) {
		this.username = input;
	}
	
	public void setPassword(String input) {
		this.password = input;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
