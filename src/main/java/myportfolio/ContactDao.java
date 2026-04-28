package myportfolio;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ContactDao {
	
	 private final String JDBC_URL = "jdbc:mysql://localhost:3306/test_db?useSSL=false&serverTimezone=Asia/Tokyo";
	    private final String USER = "root";
	    private final String PASS = "ROOT";

	  //コンストラクターとして最初にDB接続を開通させる
		public ContactDao() {
			try {
			    System.out.println("ドライバ読み込みテスト開始 ");
			    Class.forName("com.mysql.cj.jdbc.Driver");
			    System.out.println("✅ ドライバの読み込みに成功しました！");
			} catch (ClassNotFoundException e) {
			    System.out.println("❌ドライバが見つかりません.JARの配置が間違っています。");
			    e.printStackTrace();
			}
		}
		//===============================================
		//findAllメソッドにて全件検索を行いリストに格納する（is_delete追加:0は通常１は削除済）
		//===============================================
	    
		 public List<Contact> findAll() {
		        List<Contact> list = new ArrayList<>();

		        String sql = "SELECT * FROM contacts ORDER BY id DESC";

		        try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASS);
		             PreparedStatement ps = con.prepareStatement(sql);
		             ResultSet rs = ps.executeQuery()) {

		            while (rs.next()) {
		                Contact c = new Contact(
		                        rs.getInt("id"),
		                        rs.getString("name"),
		                        rs.getString("category"),
		                        rs.getString("body"),
		                        rs.getInt("status")
		                );
		                list.add(c);
		            }

		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return list;
		    }
	    
	  //===============================================
		//1人のIDの全情報を取得するメソッド作成(findById)
		//===============================================
		 public Contact findById(int id) {
		        Contact contact = null;

		        String sql = "SELECT * FROM contacts WHERE id = ?";

		        try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASS);
		             PreparedStatement ps = con.prepareStatement(sql)) {

		            ps.setInt(1, id);
		            ResultSet rs = ps.executeQuery();

		            if (rs.next()) {
		                contact = new Contact(
		                        rs.getInt("id"),
		                        rs.getString("Name"),
		                        rs.getString("category"),
		                        rs.getString("body"),
		                        rs.getInt("status")
		                );
		            }

		        } catch (SQLException e) {
		            e.printStackTrace();
		        }

		        return contact;
		    }
		//===============================================
		//ユーザー情報statusの更新(UPDATE)
		//===============================================	   
		 public boolean updateStatus(int id , int status) {
	    	String sql = "UPDATE contacts SET status = ? WHERE id =?";
	    	try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
	    			PreparedStatement ps =con.prepareStatement(sql)){
	    		ps.setInt(1,status);
	    		ps.setInt(2,id);
	    		
	    		return ps.executeUpdate() > 0;
	    	}catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    	return false;
	    }
		//===============================================
		//insertメソッド（お問い合わせ）機能の新規登録
		//===============================================
		 public void insert(Contact contact) {
			 //SQLの準備:？を使って値を流す
			 //idはauto_increment,created_at/updated_atはNow()にお任せ
			 String sql ="INSERT INTO contacts(name,category,body,status,created_at,updated_at)"
					 	+ "VALUES(?,?,?,?,NOW(),NOW())";
			 //try-with-resoursesを使って接続を自動で閉じる
			 try(Connection con =DriverManager.getConnection(JDBC_URL,USER,PASS);
					 PreparedStatement ps =con.prepareStatement(sql)){
				 //?に値をセットしていく
				 ps.setString(1,contact.getName());
				 ps.setString(2,contact.getCategory());
				 ps.setString(3,contact.getBody());
				 ps.setInt(4,contact.getStatus());
				 //実行
				 int result = ps.executeUpdate();
				 if(result > 0) {
					 System.out.println("お問い合わせの登録に成功しました"); 
				 }else {
					 System.out.println("お問い合わせの登録に失敗しました"); 
				 }
				 
			 	}catch(SQLException e) {
			 		e.printStackTrace();
			 	}
			 
	
			 
		 }
}
