package myportfolio;

import org.junit.jupiter.api.Test;

@IntegrationTest
public class ContactIntegrationTest {

	@Test
	public void testContactFlow(){
		System.out.println("==結合テスト開始(void対応版)==");
		
		try {
			//入力データの準備
			Contact contact = new Contact("test","test@example.com","質問","test内容");
			
			//Daoの実行
			ContactDao contactDao = new ContactDao();
			contactDao.insert(contact);
			
			//実行できれば成功
			System.out.println("DB処理完了");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
