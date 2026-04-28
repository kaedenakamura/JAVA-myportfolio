package myportfolio;

public class Contact {
	private int id ;
	private String name;
	private String category;
	private String body;
	private int status; //0未対応、1対応中、２対応済み
	private java.sql.Timestamp createdAt;
	private java.sql.Timestamp updatedAt;
	
	//コンストラクター
	public Contact() {}
	public Contact(int id,String name, String category, String body, int status) {
		this.setId(id);
		this.setName(name);
        this.setCategory(category);
        this.setBody(body);
        this.setStatus(status);
	}
	//コンストラクター
	public Contact(String name,String category, String body) {
		this.name = name;
		this.category =category;
		this.body = body;
		this.status = 0;
		
	}
	//toStringオーバーライドメソッドの作成
	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", category=" + category + ", body=" + body + ", status="
				+ status + "]";
	}
	//contact.java
	//カスタムgetter
	//改行を反映させた本文を返すメソッド
	public String getFormattedBody() {
		if(this.body == null) {
			return "";
		}
		//javaの改行コード(\n)を、HTMLの改行タグ<br>に置き換える
		return this.body.replace("\n","<br>");
	}
	
	//一覧表示用に、本文の先頭１０文字だけを返すメソッド
	public String getShortBody() {
		if(body == null) {
			return "" ;
		}
		//本文が１０文字以下の場合は、そのまま返す（切り取らない）
		if (body.length() <=10 ) {
			return body;
		}
		//0文字目から10文字目まで切り取って[...]をつける
		//subsTring(文字の一部を切り取る部分文字列)(開始位置,終了位置)
		return body.substring(0,10) + "...";
	}
	
	//getcategory nameメソッドの呼び出し
	public String getCategoryName() {
		// categoryは現在の数字String型の１などもらう
		if("1".equals(this.category)) {
			return "機能について";
		}else if ("2".equals(this.category)) {
			return "不具合報告";
		}else if ("3".equals(this.category)) {
			return "その他";
		}
		return "未分類";
	}
	//getStatusメソッドの作成
	public String getStatusName() {
		switch(this.status) {
		case 0 : return "未対応";
		case 1 : return "対応中";
		case 2 : return "対応済み";
		default: return "不明";
		}
	}

	// getter / setter
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public java.sql.Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}


	public java.sql.Timestamp getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(java.sql.Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
