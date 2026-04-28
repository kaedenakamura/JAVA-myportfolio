package myportfolio;

public class Category {
	
	

	private int id;
	private String categoryGroup;
	private String name;
	
	//コンストラクター化
	public Category(int id, String categoryGroup ,String name) {
		this.id =  id;
		this.categoryGroup=categoryGroup;
		this.name = name;
		}
	//toStringでオーバーライドメソッドの作成
	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryGroup=" + categoryGroup + ", name=" + name + "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryGroup() {
		return categoryGroup;
	}

	public void setCategoryGroup(String categoryGroup) {
		this.categoryGroup = categoryGroup;
	}

	
}

