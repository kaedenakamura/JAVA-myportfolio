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

@WebServlet("/profileEdit")
@MultipartConfig

public class ProfileEditServlet extends HttpServlet{
	//タイポミスの判断の為Override
	@Override
	protected void doGet(HttpServletRequest request , HttpServletResponse response)
		throws ServletException ,IOException {
		//文字化け防ぎ
		request.setCharacterEncoding("UTF-8");
		
		//loginServlet-myPage-よりLoginUserの受け取り
		HttpSession session = request.getSession();
		User loginUser =(User)session.getAttribute("LoginUser");
		System.out.println(loginUser);
		//ガード、ログインしてない場合のバリデーション(ログインサーブレットに飛ばす)
		if(loginUser == null) {
			response.sendRedirect(request.getContextPath()+"/html/login.jsp");
			return;
		}
		UserDao dao = new UserDao();
		//IDをつかって全情報を受け取る
		User latestUserInfo = dao.findById(loginUser.getId());
		//万が一DB側で削除されていた場合などのケア
				if(latestUserInfo == null) {
					//セッション破棄2
					session.invalidate();
					response.sendRedirect(request.getContextPath()+"/html/login.jsp");
					return;
				}
		//latestUserInfoで最新ユーザーの全情報をセットしてjspへ
		request.setAttribute("LoginUser",latestUserInfo);
		request.getRequestDispatcher("WEB-INF/jsp/profileEdit.jsp")
		.forward(request, response);
				
		
	}
	protected void doPost(HttpServletRequest request ,HttpServletResponse response) 
		throws IOException , ServletException{
		
		//文字化け防ぎ
		request.setCharacterEncoding("UTF-8");
		
		UserDao dao = new UserDao();
		HttpSession session = request.getSession();
		User loginUser =(User)session.getAttribute("LoginUser");
		System.out.println(loginUser);
		
		if(loginUser == null) {
			response.sendRedirect(request.getContextPath()+"/html/login.jsp");
			return;
		}
		//自分のプロフィール編集の為ここでgetIdで自分のIdを受け取る
		//idとroleはセッションから受け取る
		int id =loginUser.getId();
		int role = loginUser.getRole();
		//その他パラメーター受け取り
		String name = request.getParameter("name");
		String ruby = request.getParameter("ruby");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		String ageStr = request.getParameter("age");//バリデーション通ればint型へ変換
		int age = 0;//バリデーション対策で初期値設定
		String bio  = request.getParameter("bio");
		// 公開設定はフォームにないため、DBの現在値を維持する
		User currentUser = dao.findById(id);
		if (currentUser == null) {
			response.sendRedirect(request.getContextPath() + "/html/login.jsp");
			return;
		}
		int status = currentUser.getStatus();
		
		//画像の受け取り
		//バイナリーデータを受け取る際は、Part型を使う
		Part filePart = request.getPart("profileImage");
		//profileImageをString型へ変換し、ファイル名を取り出す
		String profileImage =filePart.getSubmittedFileName();
		
		// バリデーション開始
		
		//名前とメールが空の場合はじく
		if(name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
		//失敗した場合セッションに入れてredirect sesson省略形を使用
			request.getSession().setAttribute("errorMsg" , "名前とメールアドレスは必須事項です。");
			response.sendRedirect("profileEdit");
			return;
		}
		//name.email.ruby250文字以上あればはじく
		if (ruby == null) {
			ruby = "";
		}
		if(name.length() >=250 || email.length() >=250 || ruby.length() >= 250) {
			request.getSession().setAttribute("errorMsg" , "名前またはふりがな、メールアドレスは250文字以内で入力してください");
			response.sendRedirect("profileEdit");
			return;
		}
		//パスワードが入力されているときにバリデーションする
		if(password != null && !password.trim().isEmpty()) {
			//passwordUtilからisValidPasswordメソッド起動
			if(!PasswordUtil.isValidPassword(password)) {
				request.getSession().setAttribute("errorMsg","パスワードは8-32文字の英数字（ハイフン可）で入力してください");
				response.sendRedirect("profileEdit");
			return;
			}
		//パスワードが未入力（空）の場合、現在のパスワードを維持する
			}else if (password == null || password.trim().isEmpty()) {
				password = currentUser.getPassword();
			}
		//ふりがなが空の場合はじく(追記)
			if(ruby == null || ruby.trim().isEmpty()) {
				request.getSession().setAttribute("errorMsg","ふりがなは必須事項です。");
				response.sendRedirect("profileEdit");
				return;
			}
			
			//ふりがなをひらがな入力していないとバリデーションエラー
		if(!ruby.trim().isEmpty() && !ruby.matches("^[\\u3040-\\u309F]+$")) {
			request.getSession().setAttribute("errorMsg","ふりがなは「ひらがな」入力してください");
			response.sendRedirect("profileEdit");
			return;
		}
		//バリデーション追加年齢、自己紹介、性別、画像
		//age null 対策
		if (ageStr == null || ageStr.trim().isEmpty()) {
		    // 【重要】空欄なら、エラーメッセージをセットして元の画面に戻す
		    request.getSession().setAttribute("msg", "年齢を入力してください");
		    response.sendRedirect("profileEdit"); 
		    return; 
		}
		
		try {
		    age = Integer.parseInt(ageStr);
		} catch (NumberFormatException e) {
		    // 数字以外が入った時のリスク管理
		    request.getSession().setAttribute("msg", "年齢は数字で入力してください");
		    response.sendRedirect("profileEdit");
		    return;
		}
		
		//年齢チェック
		
		if(age < 0 || age> 999) {
			request.getSession().setAttribute("errorMsg","年齢を3桁以内で入力してください");
			response.sendRedirect("profileEdit");
			return;
		}
		//自己紹介チェック
		if(bio != null && bio.length() >1500) {
			request.getSession().setAttribute("errorMsg","自己紹介を1500文字以内で入力してください");
			response.sendRedirect("profileEdit");
			return;
		}
		//性別チェック
		if(gender != null && !(gender.equals("male") || gender.equals("female"))) {
			response.sendRedirect("profileEdit");
			return;
		}
		//プロフィール画像は2MB以内で(1KB=1024B 1M=1024KB)
		long maxFileSize = 2 *1024 *1024 ;
		//Part型（参照型。複雑な情報をまとめるときに使う）を用いてファイルサイズを抽出
		//上で取得したfilePartを使用
		long fileSize =filePart.getSize();
		//2MB超えていた時の処理
		if(fileSize > maxFileSize) {
			request.getSession().setAttribute("errorMsg","プロフィール画像の容量を２MB以内で収めてください");
			response.sendRedirect("profileEdit");
			return;
		}
		//もし画像が選ばれていなければ、DBには現在の画像名を保存する
		if(profileImage == null || profileImage.isEmpty()) {
			profileImage = dao.findById(id).getProfileImage();
		//画像がある時は、webapp→uploadsへセット
		}else {
			String uploadPath =getServletContext().getRealPath("/uploads");
			
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
		
		
		
		
		//オブジェクト化
		User user = new User(id, name, email, password, role, ruby, gender, age, bio, profileImage,status);
		
		//DAOメソッド起動
		UserDao userDao = new UserDao();
		boolean isSuccess = userDao.update(user);
		
		//判定してリダイレクト
		if(isSuccess) {
			System.out.println(id+name+"さんの情報を更新しました");
			User updatedUser = userDao.findById(id);
			if (updatedUser != null) {
				session.setAttribute("LoginUser", updatedUser);
			}
			session.setAttribute("msg", "情報を更新しました!");
			response.sendRedirect("profileEdit");
		}
	}
	
	
}
