package myportfolio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
	// 接続情報
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/test_db?useSSL=false&characterEncoding=UTF-8&serverTimezone=JST";
	private final String USER     = "root";
	private final String PASS     = "ROOT";

	//insertメソッドにてデータの追加を行うクラスを作成。
	
	public void insert(User user) {
		
		//  変数の宣言
		Connection con = null;
		PreparedStatement ps = null;

		try {
			//  接続
			con = DriverManager.getConnection(JDBC_URL, USER, PASS);

			//  SQLの作成
			String sql = "INSERT INTO users (email, password, name) VALUES (?, ?, ?)";

			//  実行準備
			ps = con.prepareStatement(sql);

			//  値のセット
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());

			//  実行
			int result = ps.executeUpdate();

			if (result > 0) {
				System.out.println("ユーザーの登録に成功しました。");
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
	} 
} 
