package myportfolio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
	//DB接続に必要な情報を定数として定義
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/test_db?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private final String USER     = "root";
	private final String PASS     = "ROOT";
	
	//===============================================-
	//insertメソッドにてデータの追加を行うクラスを作成。
	//===============================================-
	
	public boolean insert(User user) {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		    e.printStackTrace(); 
		
		try {
		    System.out.println("ドライバ読み込みテスト開始 ");
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    System.out.println("✅ ドライバの読み込みに成功しました！");
		} catch (ClassNotFoundException e) {
		    System.out.println("❌ドライバが見つかりません.JARの配置が間違っています。");
		    e.printStackTrace();
		}
		
		//  変数の宣言
		Connection con = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		

		try {
			//  接続
			con = DriverManager.getConnection(JDBC_URL, USER, PASS);

			//  SQLの作成
			String sql = "INSERT INTO users (email, password, name) VALUES (?, ?, ?)";

			//  実行準備
			ps = con.prepareStatement(sql);

			//  値のセット
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getpassword());
			ps.setString(3, user.getName());

			//  実行
			int result = ps.executeUpdate();

			if (result > 0) {
				System.out.println("ユーザーの登録に成功しました。");
				isSuccess = true;
				
			} else {
				System.out.println("ユーザーの登録に失敗しました。");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			//  クローズ処理
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isSuccess;
	 }
	
	//===============================================
	//findAllメソッドにて全件検索を行いリストに格納する
	//===============================================
	
	public List<User> findAll(){
		//空の「名簿リスト」を用意
		List<User> userList = new ArrayList<>();
		
		String sql = "SELECT * FROM users";
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		    e.printStackTrace(); 
		}
		try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASS);
				PreparedStatement ps = con.prepareStatement(sql);
				// SQLの実行、結果の受け取り 
				ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
						// 1件分のデータ（名前、アドレス、パス）をUserオブジェクトへ
						User user = new User
								(rs.getString("email"),rs.getString("password"),rs.getString("name"));
						// Userオブジェクトをリストに追加
						userList.add(user);}
					}catch (SQLException e) {
						e.printStackTrace();
					}
				
		return userList;
	}
} 
