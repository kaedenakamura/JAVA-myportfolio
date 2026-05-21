package myportfolio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * ContactDaoのユニットテスト
 */


@IntegrationTest
public class ContactDaoTest {
	@Test
	public void testInsertContact() {
		ContactDao contactDao = new ContactDao();
		
		//テスト用データの作成
		Contact testContact = new Contact("testname","test@example","システム","ユニットテストの内容");
		
		//戻り値viodの為、実行してエラーが出ないか検証
		assertDoesNotThrow(() -> {
			contactDao.insert(testContact);
		},"insert実行中にSQLException等例外が発生しました");
		System.out.println("insertメソッドが正常に終了しました");
	}
}
