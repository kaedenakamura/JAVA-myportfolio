package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/register")
@MultipartConfig

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// ブラウザからアクセス（doget,dopost）
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
		
	    doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,IOException{
		
	//設定
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		
	
	//　getParameterでhtmlよりimput
		String name = request.getParameter("name");
		String ruby = request.getParameter("ruby");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int role =Integer.parseInt(request.getParameter("role"));
		String gender = request.getParameter("gender");
		String ageStr = request.getParameter("age");
		int age = (ageStr != null && !ageStr.isEmpty()) ? Integer.parseInt(ageStr) : 0;
		String bio = request.getParameter("bio");
		//バイナリーデータを受け取る際は、Part型を使う
		Part filePart = request.getPart("profileImage");
		//profileImageをString型へ変換し、ファイル名を取り出す
		String profileImage =filePart.getSubmittedFileName();
		
		
	//新規登録画面にて入力（register.jsp）した情報のバリデーションチェック
		//名前メールが空とnullならエラー
	if (name == null || name.isEmpty() || email == null || email.isEmpty() ||
			password ==null 
			) {
		forwardWithError(request, response, "1", name, ruby, email, ageStr, bio, gender);
		
		System.out.println("バリデーションエラー：空のデータがあるため登録を中止します");
		return;
			}
	if (email.length() >=250 || name.length()>=250 || ruby.length()>=250) {
	//名前、メール、ふりがなrubyが２５０文字以上の場合はerrorを返すバリデーション
		forwardWithError(request, response, "2", name, ruby, email, ageStr, bio, gender);
	System.out.println("バリデーションエラー：名前もしくふりがな、メールアドレスが長すぎます");
	return;
	}
	// 追加：メールアドレスの形式チェック（正規表現）
	// type="text"などの変化にも対応 
	String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	if (!email.matches(emailPattern)) {
		forwardWithError(request, response, "10", name, ruby, email, ageStr, bio, gender); // 追加エラー
	    System.out.println("バリデーションエラー：メールアドレスの形式が正しくありません -> " + email);
	    return;
	}
	//パスワードが入力されているときだけバリデーションする。nullと空でないとき
	if(password != null && !password.isEmpty()) {
		//passwordUtilからisValidPasswordメソッド起動
		if(!PasswordUtil.isValidPassword(password)) {
			forwardWithError(request, response, "3", name, ruby, email, ageStr, bio, gender);
		return;
		}
	}
	//ふりがながひらがなではないときバリデーションエラー
	if(!ruby.isEmpty() && !ruby.matches("^[\\u3040-\\u309F]+$")) {
		forwardWithError(request, response, "4", name, ruby, email, ageStr, bio, gender);
		System.out.println("バリデーションエラー:ふりがなは「ひらがな」で入力してください");
		return;
		
	}
	//バリデーション追加年齢、自己紹介、性別、画像
	//年齢チェック
	if (age < 0 || age> 999) {
		forwardWithError(request, response, "6", name, ruby, email, ageStr, bio, gender);
		return;
	}
	//自己紹介チェック
	if(bio != null && bio.length() >1500) {
		forwardWithError(request, response, "7", name, ruby, email, ageStr, bio, gender);
		return;
	}
	//性別のチェック
	if(gender != null && !(gender.equals("male")|| gender.equals("female"))) {
		forwardWithError(request, response, "8", name, ruby, email, ageStr, bio, gender);
		return;
	}
	//プロフィール画像２MB以内バリデーション(1KB=1024B 1M=1024KB)
	long maxFileSize = 2 * 1024 *1024;
	//Part型（参照型。複雑な情報をまとめるときに使う。）を用いてファイルサイズを抽出
	//上で取得したfilePartを使用
	long fileSize =filePart.getSize();
	//2MBを超えていた時の処理
	if(fileSize > maxFileSize) {
		forwardWithError(request, response, "9", name, ruby, email, ageStr, bio, gender);
		return;
	}
	// もし画像が選ばれていなければ、DBにはデフォルト名を保存する
	if (profileImage == null || profileImage.isEmpty()) {
	    profileImage = "default_icon.png"; // あらかじめ用意する画像名
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
	
		
		// 受け取りチェック
		System.out.println("--- Servlet受け取りチェック---- ");
	    System.out.println("HTMLから届いた name: " + name);
	    System.out.println("HTMLから届いた email: " + email);
	    System.out.println("HTMLから届いた pass: " + password);
		
	// 司令塔からDAOへの命令
	    //IDは新規だから0で可
	    User user = new User(0, name, email, password, role, ruby, gender, age, bio, profileImage);
		UserDao userDao = new UserDao();
		
		//追加：DBのメールアドレス2重バリデーションチェック
		if (userDao.isEmailExists(email)) {
			//重複していたらerror=5を返す
			forwardWithError(request, response, "5", name, ruby, email, ageStr, bio, gender);
			return;
		}

		
		if(userDao.insert(user)) {
		    response.sendRedirect("html/login.jsp?registerSuccess=1"); 
		} else {
		    response.sendRedirect("/html/register.jsp"); 
		}
		}
	
	//エラーが発生したときに値（データ）を保持、セットして戻すメソッド
	private void forwardWithError(HttpServletRequest request , HttpServletResponse response , String errorNum,String name, String ruby , String email , String ageStr ,String bio , String gender)
			throws ServletException ,IOException{
		//passwordとprofileImageは含めずセット
		request.setAttribute("name",name);
		request.setAttribute("ruby",ruby);
		request.setAttribute("email",email);
		request.setAttribute("age",ageStr);
		request.setAttribute("bio",bio);
		request.setAttribute("gender",gender);
	
	request.getRequestDispatcher("/html/register.jsp?error=" + errorNum).forward(request, response);
	}
}
	
	
	
	
