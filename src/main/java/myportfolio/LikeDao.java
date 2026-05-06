package myportfolio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikeDao {
	//DB接続に必要な情報を定数として定義
	 private final String JDBC_URL = "jdbc:mysql://localhost:3306/test_db?useSSL=false&serverTimezone=Asia/Tokyo";
	    private final String USER = "root";
	    private final String PASS = "ROOT";
	//接続開通テストははログイン時にするので省略（2重アクセス負担増の為。）
	
		
	//===============================================-
	//likeInsertメソッドにていいね登録するメソッド作成
	//===============================================-
	public void likeInsert(int fromUserid ,int toUserId) 
			throws Exception{
		String sql ="INSERT INTO likes(from_user_id , to_user_id) VALUES(?,?)";
		try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps =con.prepareStatement(sql)){
			ps.setInt(1,fromUserid);
			ps.setInt(2,toUserId);
			ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//===============================================
	//いいね済みかを確認メソッド（重複防止）+1件ある場合はtrueを返しいいね済とする
	//===============================================
	public boolean isLiked(int fromUserId , int toUserId ) throws Exception{
		String sql ="SELECT COUNT(*) FROM likes WHERE from_user_id = ? AND to_user_id = ? ";
		try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql)){
				ps.setInt(1,fromUserId);
				ps.setInt(2,toUserId);
				try(ResultSet rs = ps.executeQuery()){
					if(rs.next()) {
						return rs.getInt(1)>0;
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}
	//===============================================
	//いいねを解除するメソッド作成
	//===============================================
	public void delete(int fromUserId , int toUserId) throws Exception{
		String sql = "UPDATE likes SET is_delete = '1' WHERE from_user_id = ? AND to_user_id = ?";
		try(Connection con = DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql)){
				ps.setInt(1,fromUserId);
				ps.setInt(2, toUserId);
				
				ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	//===============================================
	//いいねの合計数を数えるメソッド作成(いいねをもらった数の合計)
	//===============================================
	public int countLikesByToUserId(int toUserId) throws Exception{
		String sql ="SELECT COUNT(*) FROM likes  WHERE to_user_id = ? AND "; 
		try(Connection con =DriverManager.getConnection(JDBC_URL ,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, toUserId);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					return rs.getInt(1);
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	//===============================================
	// いいね獲得数順に並べたランキングリストを取得
	//===============================================
	public List<User> getMonthlyLikeRanking() throws Exception {
		List<User> ranking = new ArrayList<>();
		
		
		String sql = "SELECT u.id , u.name , COUNT(l.id) AS like_count"
					+ " FROM users AS u JOIN likes AS l ON u.id =l.to_user_id"
					+ " WHERE"
					+ " l.created_at >= DATE_FORMAT(NOW(),'%Y-%m-01')" 
					+ " GROUP BY u.id , u.name "
					+ " ORDER BY like_count DESC";
		try(Connection con =DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs =ps.executeQuery()){
			
			while (rs.next()) {
				User user = new User();
				//DBデータの値をuserへセット
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				//userクラスのsetlikecountへ追加
				user.setLikeCount(rs.getInt("like_count"));
				
				ranking.add(user);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return ranking;
	}
	//===============================================
	// 一般ユーザーが一覧で表示されるfindAllListメソッド
	//===============================================
	public List<User> findAllList() throws Exception{
		//空のリストを作成
		List<User> userList = new ArrayList<>();
		
		String sql = "SELECT u.* , COUNT(l.id) AS like_count "
				+ "FROM users AS u LEFT JOIN likes AS l ON u.id =l.to_user_id"
				+ " WHERE is_deleted = 0 GROUP BY u.id ORDER BY id DESC";
		
		try(Connection con =DriverManager.getConnection(JDBC_URL,USER,PASS);
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()){
				
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
					        rs.getInt("is_deleted")
							);
					//joinした結果からいいね数をセット
					user.setLikeCount(rs.getInt("like_count"));
					
					userList.add(user);
				}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
	
}
