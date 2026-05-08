package myportfolio;

import static org.junit.jupiter.api.Assertions.*; // 判定メソッド

import org.junit.jupiter.api.Test; //テスト用に必要

/**
 * UserDaoのユニットテスト
 * 目的：DBへの処理が正しく行われるかを検証します。
 */

public class UserDaoTest {
	@Test
	public void testInsertUser() {
		UserDao dao = new UserDao();
		
		//テスト用のダミーデータ作成
		// 全項目入りのコンストラクタ(DAO用)へのテスト
		User testUser =new User(0,"テストさん","test@example.com","Password123!",1,"てすとたろう","male",25,"自己紹介","default.png");
		
		//テストが通れば、実行結果がtrue
		boolean result = dao.insert(testUser);
		
		assertTrue(result,"ユーザーの登録処理に失敗しました");	
	}
	//
	
	
}
