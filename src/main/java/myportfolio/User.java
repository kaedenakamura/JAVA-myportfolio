package myportfolio;

public class User {
	private int id;
	private String email;
	private String password;
	private String name;
	private java.sql.Timestamp createdAt;
	private java.sql.Timestamp updatedAt;
	private java.sql.Timestamp deletedAt;
	
	public  User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getpassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public java.sql.Timestamp getCreatedAt() {
		return createdAt;
	}

	public java.sql.Timestamp getUpdetedAt() {
		return updatedAt;
	}
	public void setUpdetedAt(java.sql.Timestamp updetedAt) {
		this.updatedAt = updetedAt;
	}
	public java.sql.Timestamp getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(java.sql.Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}
	
	
}


