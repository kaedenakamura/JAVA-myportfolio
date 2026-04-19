package myportfolio;


public class PasswordUtil {
	
	//passwordのバリデーションメソッドの作成
	public static boolean isValidPassword(String password) {
		if(password == null) return false;
		//8文字以上32文字以下かつ半角英数字とハイフン+アンダースコアのみ
		return password.length() >= 8 &&
				password.length() <= 32 &&
				password.matches("^[a-zA-Z0-9_-]+$");
		
	}

}
