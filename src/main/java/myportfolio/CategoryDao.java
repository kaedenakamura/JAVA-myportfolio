package myportfolio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CategoryDao {
	//DB接続に必要な情報を定数として定義
		private final String JDBC_URL = "jdbc:mysql://localhost:3306/test_db?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
		private final String USER     = "root";
		private final String PASS     = "ROOT";

	//接続開通テストははログイン時にするので省略（2重アクセス負担増の為。）
	//===============================================-
	//insertメソッドにてデータの追加を行うクラスを作成。
	//===============================================-
	public void insert(String group , String name) {
		String sql ="INSERT INTO category(category_group,name) VALUES (?,?)";
		
		try(Connection con =DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps =con.prepareStatement(sql)){
			ps.setString(1,group );
			ps.setString(2, name);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		
		
		
	//===============================================
	//findAllメソッドにて全件検索を行いリストに格納する（is_delete追加:0は通常１は削除済）
	//===============================================
	//リストの格納部分の作成
	public List<Category> findAll(){
		List<Category> list = new ArrayList<>();
		
		String sql = "SELECT * FROM category ORDER BY id ASC";

		try (Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps =con.prepareStatement(sql);
				//結果の受け取り
				ResultSet rs =ps.executeQuery()){
			while (rs.next()) {
				Category c =new Category(
						rs.getInt("id"),
						rs.getString("category_group"),
						rs.getString("name")
						);
				list.add(c);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	return list;
}

	//===============================================
	//カテゴリーテーブルの削除(DELETE)
	//===============================================
	public void delete(int id) {
		String sql = "DELETE FROM category WHERE id = ?";
		
		try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, id);
			
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//===============================================
	//カテゴリーのidを１権取得(更新メソッドを使うため）
	//===============================================
	public Category findById(int id) {
		Category category = null;
		String sql ="SELECT * FROM category WHERE id = ?"; 
		
		try(Connection con =DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql);){
			//sqlへidをセット	
			ps.setInt(1,id);
			//SQLを実行して結果を受け取る
			try(ResultSet rs = ps.executeQuery()){
				//rsが１件あればgetIDにて取得
				if(rs.next()) {
					category = new Category(
						 rs.getInt("id"),
						 rs.getString("category_group"),
						 rs.getString("name"));
				 System.out.println(id + "DBよりカテゴリー情報を取得しました");
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
				
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
		
	}
	
	
	
	
	
	//===============================================
	//カテゴリーの更新(UPDATE)
	//===============================================
	public boolean update(Category category) {
		String spl = "UPDATE category SET name =? WHERE id = ?";
		boolean isSuccess =false;
		
		try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps =con.prepareStatement(spl);){
			//SQLの「？」へセット
			ps.setString(1,category.getName());
			ps.setInt(2,category.getId());
			
			int result =ps.executeUpdate();
			if(result > 0) {
				isSuccess=true;
				System.out.println("ID:"+category.getId()+"の更新に成功しました");
			}
		}catch (SQLException e) {
				e.printStackTrace();
			}
		return isSuccess;
		}
		
	}

