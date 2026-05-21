package myportfolio;

import org.junit.jupiter.api.Test;

/**
 * 登録機能の結合テスト
 * 目的：画面からの入力を想定した一連の流れが正常に終了するかを検証する
 */

@IntegrationTest
public class RegistrationIntegrationTest {
	@Test
	public void testRegistration() {
		System.out.println("==統合テスト開始：新規登録フロー ===");
		
		//サーブレットが受け取るパラメーターを想定
		String name ="結合テスト";
		String email="integrationTest@example.com";
		
		//バリデーションのロジック確認
		if(name != null && email.contains("@")) {
			System.out.println("Step 1 : バリデーション通過");
			
		//DAOへ橋渡し
			UserDao dao = new UserDao();
            User user = new User(0, name, email, "Pass1234", 1, "けつごうてすと", "female", 30, "hello", "icon.png");
        //
        if(dao.insert(user)) {
        	System.out.println("Step 2 :DB保存完了");
        	System.out.println("Result:[成功] 画面遷移へ進みます(redirect)");
        }else {
        	System.out.println("Result:[失敗] DBエラーが発生しました");
        }
		}else {
			System.out.println("Result:[失敗] バリデーションで止まりました");
		}
	
}
}
