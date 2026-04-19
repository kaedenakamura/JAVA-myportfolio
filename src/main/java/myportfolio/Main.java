package myportfolio;

public class Main {

	public static void main(String[] args) {
		User user = new User();
		UserDao userDao = new UserDao();
		
		if(userDao.insert(user)) {
			System.out.println("ユーザーの登録に成功しました。");
		} else {
			System.out.println("ユーザーの登録に失敗しました。");
		}

	}

}
