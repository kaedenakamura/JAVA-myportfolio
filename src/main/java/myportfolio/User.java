package myportfolio;

public class User {
	private int id;
	private String name;
	private String ruby;
	private String email;
	private String password;
	private java.sql.Timestamp createdAt;
	private java.sql.Timestamp updatedAt;
	private java.sql.Timestamp deletedAt;
	private int role;
	private String gender;
    private int age;
    private String bio;
    private String profileImage;
    private int isDeleted;
    //like機能
    private int likeCount;
    
    //非同期処理の為の一時的なlike格納
    private boolean isLiked;
    
    // デフォルトコンストラクタ
    public User() {
    	//コンストラクターなし
    }
    
	public  User(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
	public User(int id, String name, String email, String password) {
		 this.id = id;
		 this.email = email;
	     this.password = password;
	     this.name = name;
	}
	public User(int id, String name, String email) {
		this.id =id;
		this.name = name;
		this.email = email;
	}
	public User(int id, String name,String ruby , String email, String password) {
		 this.id = id;
		 this.email = email;
	     this.password = password;
	     this.name = name;
	     this.setRuby(ruby) ;
	}
	public User(String name,String ruby , String email, String password) {
		 this.email = email;
	     this.password = password;
	     this.name = name;
	     this.setRuby(ruby) ;
	}
	// 全項目入りのコンストラクタ(DAO用)update servlet ProfileEdit servlet
    public User(int id, String name, String email, String password, int role, 
                String ruby, String gender, int age, String bio, String profileImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ruby = ruby;
        this.gender = gender;
        this.age = age;
        this.bio = bio;
        this.profileImage = profileImage;
    }
 // 全項目入りのコンストラクタ(DAO用)+isDaleted追加分
    public User(int id, String name, String email, String password, int role, 
                String ruby, String gender, int age, String bio, String profileImage ,int isDeleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ruby = ruby;
        this.gender = gender;
        this.age = age;
        this.bio = bio;
        this.profileImage = profileImage;
        this.isDeleted = isDeleted;
    }
 // 全項目入りのコンストラクタ(DAO用)+likeCount追加分
    public User(int id, String name, String email, String password, int role, 
                String ruby, String gender, int age, String bio, String profileImage ,int isDeleted, int likeCount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ruby = ruby;
        this.gender = gender;
        this.age = age;
        this.bio = bio;
        this.profileImage = profileImage;
        this.isDeleted = isDeleted;
        this.likeCount = likeCount;
    }

    //toStringオーバーライドメソッドの作成
    @Override
    public String toString() {
    	return "User [id="+id+", name="+name+",email="+email+",role="+role+"]";
    	
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
	public String getPassword() {
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
	public void setUpdetedAt(java.sql.Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public java.sql.Timestamp getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(java.sql.Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}
	public String getRuby() {
		return ruby;
	}
	public void setRuby(String ruby) {
		this.ruby = ruby;
	}
	public int getRole() {
	    return role;
	}
	public void setRole(int role) {
	    this.role = role;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	  
    public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
}

