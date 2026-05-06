package myportfolio;

public class Category {
	
	

	private int id;
	private String categoryGroup;
	
	//コンストラクター化
	public Category(int id, String categoryGroup) {
		this.id =  id;
		this.categoryGroup=categoryGroup;
		}
	public Category() {
		
	}
	//toStringでオーバーライドメソッドの作成
	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryGroup=" + categoryGroup + "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCategoryGroup() {
		return categoryGroup;
	}

	public void setCategoryGroup(String categoryGroup) {
		this.categoryGroup = categoryGroup;
	}
	
	
}

