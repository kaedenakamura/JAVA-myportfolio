package myportfolio;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/update")
@MultipartConfig

public class UpdateServlet extends HttpServlet {
	
	// 編集画面を表示する（一覧の「編集」リンク～）
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
		//文字化け防ぎ
		request.setCharacterEncoding("UTF-8");

	    // 1. URLからIDを取得
	    String idStr = request.getParameter("id");
	    int id = Integer.parseInt(idStr);

	    // 2. DAOでその人の今の情報を取ってくる
	    UserDao dao = new UserDao();
	    User user = dao.findById(id); 

	    // 3. データをJSPに渡して、編集画面を表示
	    request.setAttribute("user", user);
	    request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp").forward(request, response);
	}
	
	//編集画面の更新からくるpost
	protected void doPost(HttpServletRequest request , HttpServletResponse response)
		throws ServletException,IOException{
				
		//文字化けの防止
		request.setCharacterEncoding("UTF-8");		
		//何度もDaoメソッド使うため先に定義
		UserDao dao = new UserDao();
		
		//getParameterで値を受け取る
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String ruby = request.getParameter("ruby");
		String email = request.getParameter("email");
		String password = request.getParameter("password"); 
		int role = Integer.parseInt(request.getParameter("role"));
		String gender = request.getParameter("gender");
		int age = Integer.parseInt(request.getParameter("age"));
		String bio = request.getParameter("bio");
		//バイナリーデータを受け取る際は、Part型を使う
		Part filePart = request.getPart("profileImage");
		//profileImageをString型へ変換し、ファイル名を取り出す
		String profileImage =filePart.getSubmittedFileName();
		
		//バリデーションチェック
		//名前とメールアドレスが空の場合はじく
		if(name == null || name.isEmpty() || email == null || email.isEmpty()) {
			//失敗した場合セッションに入れてredirect sesson省略形を使用
			request.getSession().setAttribute("errorMsg","名前とメールアドレスは必須事項です。");
			response.sendRedirect("update?id="+ id);
			return;	
		}
		//name,email,ruby250文字以上あればはじく
		if(name.length() >250 || email.length() >250 || ruby.length()>=250) {
			request.getSession().setAttribute("errorMsg","名前またはふりがな、メールアドレスは250文字以内で入力してください");
			response.sendRedirect("update?id="+ id );
			return;
		}
		//パスワードが入力されているときだけバリデーションする。nullと空でないとき
		if(password != null && !password.isEmpty()) {
			//passwordUtilからisValidPasswordメソッド起動
			if(!PasswordUtil.isValidPassword(password)) {
			request.getSession().setAttribute("errorMsg","パスワードは8-32文字の英数字（ハイフン可）で入力してください");
			response.sendRedirect("update?id=" +id);
			return;
			
			}
		// パスワードが未入力（空）の場合、現在のパスワードを維持する 
		}else if(password == null || password.isEmpty()) {
			   User currentUser = dao.findById(id); // 現在の情報をDBから取得
			   password = currentUser.getPassword(); // 今のパスワードを再セット
			}
		
		//ふりがなをひらがな入力していないとバリデーションエラー
		if(!ruby.isEmpty() && !ruby.matches("^[\\u3040-\\u309F]+$")) {
			request.getSession().setAttribute("errorMsg","ふりがなは「ひらがな」で入力してください");
			response.sendRedirect("update?id=" +id);
			System.out.println("バリデーションエラー:ふりがなは「ひらがな」で入力してください");
			return;
			
		}
		//バリデーション追加年齢、自己紹介、性別、画像
		//年齢チェック
		if (age < 0 || age> 999) {
			request.getSession().setAttribute("errorMsg","年齢を3桁以内で入力してください");
			response.sendRedirect("update?id=" +id);
			return;
		}
		//自己紹介チェック
		if(bio != null && bio.length() >1500) {
			request.getSession().setAttribute("errorMsg","自己紹介を1500文字以内で入力してください");
			response.sendRedirect("update?id=" +id);
			return;
		}
		//性別のチェック
		if(gender != null && !(gender.equals("male")|| gender.equals("female"))) {
			request.getSession().setAttribute("errorMsg","性別を男性もしくは女性を選んでください");
			response.sendRedirect("update?id=" +id);
			return;
		}
		//プロフィール画像２MB以内バリデーション(1KB=1024B 1M=1024KB)
		long maxFileSize = 2 * 1024 *1024;
		//Part型（参照型。複雑な情報をまとめるときに使う。）を用いてファイルサイズを抽出
		//上で取得したfilePartを使用
		long fileSize =filePart.getSize();
		//2MBを超えていた時の処理
		if(fileSize > maxFileSize) {
			request.getSession().setAttribute("errorMsg","プロフィール画像の容量を２MB以内で収めてください");
			response.sendRedirect("update?id=" +id);
			return;
		}
		//もし画像が選ばれていなければ、DBには現在の画像名を保存する
		if(profileImage == null || profileImage.isEmpty()) {
			profileImage = dao.findById(id).getProfileImage();
		//画像がある時は、webapp→uploadsへセット
		}else {
			String uploadPath = getServletContext().getRealPath("/")+"uploads";
			
			//もしフォルダが存在しない場合に作成
			java.io.File uploadDir =new java.io.File(uploadPath);
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			//フォルダにファイルを書き込む
			// File.separator は Windowsの "\" や Mac/Linuxの "/" を自動で判別してくれる便利なやつ
			filePart.write(uploadPath + java.io.File.separator + profileImage);
			System.out.println("画像を保存しました: " + uploadPath 
								+ java.io.File.separator + profileImage);
		}

		
		
		//オブジェクトへ（インスタンス化）
		User user = new User(id, name, email, password, role, ruby, gender, age, bio, profileImage);
		
		//DAOメソッド起動
		UserDao userDao = new UserDao();
		boolean isSuccess = userDao.update(user);
		
		//判定してリダイレクト
		if(isSuccess) {
			//成功したらダッシュボードへ戻る
			System.out.println(id+name+"さんの情報を更新しました");
			//セッション正規形を使用
			HttpSession session = request.getSession();
			session.setAttribute("msg","情報を更新しました！");
			response.sendRedirect("list");
		}else {
			System.out.println(id+name+"情報の更新に失敗しました");
			response.sendRedirect("update?id="+id);
		}
		
	}
	
}
