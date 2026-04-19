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
	
	//コンストラクターとして最初にDB接続を開通させる
	public UserDao() {
		try {
		    System.out.println("ドライバ読み込みテスト開始 ");
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    System.out.println("✅ ドライバの読み込みに成功しました！");
		} catch (ClassNotFoundException e) {
		    System.out.println("❌ドライバが見つかりません.JARの配置が間違っています。");
		    e.printStackTrace();
		}
	}
	
	//===============================================-
	//insertメソッドにてデータの追加を行うクラスを作成。
	//===============================================-
	
	public boolean insert(User user) {
		
		//  変数の宣言
		Connection con = null;
		PreparedStatement ps = null;
		boolean isSuccess = false;
		

		try {
			//  接続
			con = DriverManager.getConnection(JDBC_URL, USER, PASS);

			//  SQLの作成
			String sql = "INSERT INTO users (name, ruby, email, password, role, gender, age, bio, profile_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			//  実行準備
			ps = con.prepareStatement(sql);

			//  値のセット
			ps.setString(1, user.getName());
	        ps.setString(2, user.getRuby());
	        ps.setString(3, user.getEmail());
	        ps.setString(4, user.getPassword());
	        ps.setInt(5, user.getRole());
	        ps.setString(6, user.getGender());
	        ps.setInt(7, user.getAge());
	        ps.setString(8, user.getBio());
	        ps.setString(9, user.getProfileImage());
	        
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
	//findAllメソッドにて全件検索を行いリストに格納する（is_delete追加:0は通常１は削除済）
	//===============================================
	
	public List<User> findAll(){
		//空の「名簿リスト」を用意
		List<User> userList = new ArrayList<>();
		
		String sql = "SELECT * FROM users WHERE is_deleted = 0";

		try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASS);
				PreparedStatement ps = con.prepareStatement(sql);
				// SQLの実行、結果の受け取り 
				ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
						// 1件分のデータ（名前、アドレス、パス）をUserオブジェクトへ
						User user = new User(
								rs.getInt("id"),
						        rs.getString("name"),
						        rs.getString("email"),
						        rs.getString("password"),
						        rs.getInt("role"),
						        rs.getString("ruby"),
						        rs.getString("gender"),
						        rs.getInt("age"),
						        rs.getString("bio"),
						        rs.getString("profile_image"),
						        rs.getInt("is_deleted"));
						
						// Userオブジェクトをリストに追加
						userList.add(user);}
					}catch (SQLException e) {
						e.printStackTrace();
					}
				
		return userList;
	}
	

	//===============================================
	//isEmailExsisメソッドにてDBへemailの２重登録を防ぐメソッドを作成
	//===============================================
	public boolean isEmailExists(String email) {
		String sql ="SELECT COUNT(*) FROM users WHERE email = ? ";
		
		try(Connection con = DriverManager.getConnection(JDBC_URL ,USER ,PASS);
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1,email);
			try(ResultSet rs = ps.executeQuery()){
				if (rs.next()) {
					return rs.getInt(1)>0;
					}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	//===============================================
	//ログイン画面よりアクセスしたときのDBへemailとpasswordを探すそして見つかればセットする
	//===============================================
	public User findUser(String email , String password) {
		User user = null;
		String sql ="SELECT * FROM users WHERE email = ? AND password = ?";
		
		try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps =con.prepareStatement(sql)){
			//select1番目と２番目の「?」へセット
			ps.setString(1,email);
			ps.setString(2,password);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					//見つかったら、そのデータでUserオブジェクトを作る
					user = new User();
					user.setId(rs.getInt("id"));
					user.setEmail(rs.getString("email"));
					user.setName(rs.getString("name"));
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	return user;
	}
	
	//===============================================
	//1人のIDの全情報を取得するメソッド作成(findById)
	//===============================================
	public User findById(int ids) {
		User user = null;
		
		String sql = "SELECT * FROM users WHERE id =?";
		
		try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps =con.prepareStatement(sql)){
				//select「？」へセット
				ps.setInt(1, ids);
				
				try(ResultSet rs = ps.executeQuery()){
					if(rs.next()) {
					//SQLを実行して結果を受け取る 
					int id =rs.getInt("id");
					user = new User(
							rs.getInt("id"),
		                    rs.getString("name"),
		                    rs.getString("email"),
		                    rs.getString("password"),
		                    rs.getInt("role"),
		                    rs.getString("ruby"),
		                    rs.getString("gender"),
		                    rs.getInt("age"),
		                    rs.getString("bio"),
		                    rs.getString("profile_image"),
		                    rs.getInt("is_deleted")
							);
					System.out.println(id +"DBより全情報を取得しました");
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return user;
				
		
	}
	

	
	//===============================================
	//ダッシュボードよりユーザー情報の更新(UPDATE)
	//===============================================
	public boolean update(User user) {
		String sql ="UPDATE users SET name=?, ruby=?, email=?, role=?, gender=?, age=?, bio=?, profile_image=? WHERE id=?";
		boolean isSuccess = false;
		
		try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql)){
			//SQLの「‽」へセット
			ps.setString(1, user.getName());
	        ps.setString(2, user.getRuby());
	        ps.setString(3, user.getEmail());
	        ps.setInt(4, user.getRole());
	        ps.setString(5, user.getGender());
	        ps.setInt(6, user.getAge());
	        ps.setString(7, user.getBio());
	        ps.setString(8, user.getProfileImage());
	        ps.setInt(9, user.getId());
			
			int result =ps.executeUpdate();
			if(result > 0) {
				System.out.println("ID:"+user.getId()+"の更新に成功しました");
				isSuccess = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	//===============================================
	//ダッシュボードよりユーザー情報の削除(DELETE)
	//===============================================
	public boolean delete(int id) {
		String sql ="DELETE from users WHERE id = ? ";
		boolean isSuccess = false ;
		
		try(Connection con =DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps =con.prepareStatement(sql)){
			//削除したい項目[?]へセット(jspより持ってくる)
			ps.setInt(1,id);
			//実行して削除
			int result =ps.executeUpdate();
			if (result > 0 ) {
				isSuccess = true ;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
	//===============================================
		//ファインドページメソッドの作成（５件ずつリストを表示するメソッド）
	//===============================================
	public List<User> findByPage(int page){
		//offsetとlimit用いてページ数表示
		List<User> list = new ArrayList<>();
		int limit = 5;
		int offset = (page - 1)* limit;
		
		String sql ="SELECT * FROM users WHERE is_deleted = 0 LIMIT ? OFFSET ?";
		
		try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1,limit);
			ps.setInt(2, offset);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					User user = new User(
							rs.getInt("id"),
					        rs.getString("name"),
					        rs.getString("email"),
					        rs.getString("password"),
					        rs.getInt("role"),
					        rs.getString("ruby"),
					        rs.getString("gender"),
					        rs.getInt("age"),
					        rs.getString("bio"),
					        rs.getString("profile_image"),
					        rs.getInt("is_deleted"));
							
					list.add(user);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;		
	}
	//===============================================
	//countAllメソッドの作成。(全部計算してユーザー人数の合計を出す)
	//===============================================
	public int countAll() {
		String sql ="SELECT COUNT(*) AS count FROM users WHERE is_deleted = 0";
		try(Connection con =DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps =con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()){
			if (rs.next()) {
				return rs.getInt("count");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		//
		return 0;
	}

	//===============================================
	//updateRole（admin,general）切替のメソッド
	//===============================================
	public boolean updateRole(int id ,int role) {
		//デバック
		System.out.println("ID:" + id + ", Role:" + role);
		String sql ="UPDATE users SET role = ? WHERE id = ?";
		try(Connection con =DriverManager.getConnection(JDBC_URL,USER,PASS);
			PreparedStatement ps = con.prepareStatement(sql)){
			//DBよりservletへセット
			//role　1=admin 0=general
			ps.setInt(1, role);
			ps.setInt(2, id);
			
			int result = ps.executeUpdate();
			//デバック
			System.out.println("更新された行数: " + result);
			return result > 0;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	//===============================================
	//論理削除(データを復元できるように残しておく)実行メソッド 0が残す1が消去済みに設定
	//===============================================
	public boolean softDelete(int id) {
		//デバック
		System.out.println("ID"+id+"の論理削除を実行します");
		
		String sql = "UPDATE users SET is_deleted = 1 WHERE id = ?";
		boolean isSuccess = false;
		
		try(Connection con =DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1,id);
			
			int result = ps.executeUpdate();
			if (result > 0) {
				if(result > 0) {
					System.out.println("ID:"+id+"の論理削除に成功しました");
					isSuccess = true;
				}
			}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return isSuccess;
	}
	
	//===============================================
	//findDeletedAllメソッドにて削除済のアカウント(is_deletedの中の１削除済)の全件検索を行いリストに格納する
	//===============================================
		
		public List<User> findDeletedAll(){
			//空の「名簿リスト」を用意
			List<User> userList = new ArrayList<>();
			
			String sql = "SELECT * FROM users WHERE is_Deleted = 1";

			try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASS);
					PreparedStatement ps = con.prepareStatement(sql);
					// SQLの実行、結果の受け取り 
					ResultSet rs = ps.executeQuery()){
						while(rs.next()) {
							// 1件分のデータ（全ての情報）をUserオブジェクトへ
							User user = new User(
									rs.getInt("id"),
							        rs.getString("name"),
							        rs.getString("email"),
							        rs.getString("password"),
							        rs.getInt("role"),
							        rs.getString("ruby"),
							        rs.getString("gender"),
							        rs.getInt("age"),
							        rs.getString("bio"),
							        rs.getString("profile_image"),
							        rs.getInt("is_deleted"));
							// Userオブジェクトをリストに追加
							userList.add(user);}
						}catch (SQLException e) {
							e.printStackTrace();
						}
					
			return userList;
		}
	//===============================================
	//削除したアカウントを復活させるrestorメソッド（消去済み１→復活０へ数字を戻す）
	//===============================================
		public boolean restor(int id) {
			//フラグを０に戻す
			String sql ="UPDATE users SET is_deleted = 0 WHERE id = ? ";
			boolean isSuccess = false;
			
			try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
					PreparedStatement ps =con.prepareStatement(sql);){
				ps.setInt(1, id);
				
				int result = ps.executeUpdate();
				//１行以上更新されたらtrue
				if (result > 0) {
					System.out.println("ID:"+id+"の復元に成功しました");
					isSuccess = true;
				}
			}catch(SQLException e ) {
				e.printStackTrace();
			}
			return isSuccess;
		}
}
