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
		response.sendRedirect("html/register.jsp?error=1");
		
		System.out.println("バリデーションエラー：空のデータがあるため登録を中止します");
		return;
			}
	if (email.length() >=250 || name.length()>=250 || ruby.length()>=250) {
	//名前、メール、ふりがなrubyが２５０文字以上の場合はerrorを返すバリデーション
	response.sendRedirect("html/register.jsp?error=2");
	System.out.println("バリデーションエラー：名前もしくふりがな、メールアドレスが長すぎます");
	return;
	}
	//パスワードが入力されているときだけバリデーションする。nullと空でないとき
	if(password != null && !password.isEmpty()) {
		//passwordUtilからisValidPasswordメソッド起動
		if(!PasswordUtil.isValidPassword(password)) {
		response.sendRedirect("html/register.jsp?error=3" );
		return;
		}
	}
	//ふりがながひらがなではないときバリデーションエラー
	if(!ruby.isEmpty() && !ruby.matches("^[\\u3040-\\u309F]+$")) {
		response.sendRedirect("html/register.jsp?error=4");
		System.out.println("バリデーションエラー:ふりがなは「ひらがな」で入力してください");
		return;
		
	}
	//バリデーション追加年齢、自己紹介、性別、画像
	//年齢チェック
	if (age < 0 || age> 999) {
		response.sendRedirect("html/register.jsp?error=6" );
		return;
	}
	//自己紹介チェック
	if(bio != null && bio.length() >1500) {
		response.sendRedirect("html/register.jsp?error=7" );
		return;
	}
	//性別のチェック
	if(gender != null && !(gender.equals("male")|| gender.equals("female"))) {
		response.sendRedirect("html/register.jsp?error=8" );
		return;
	}
	//プロフィール画像２MB以内バリデーション(1KB=1024B 1M=1024KB)
	long maxFileSize = 2 * 1024 *1024;
	//Part型（参照型。複雑な情報をまとめるときに使う。）を用いてファイルサイズを抽出
	//上で取得したfilePartを使用
	long fileSize =filePart.getSize();
	//2MBを超えていた時の処理
	if(fileSize > maxFileSize) {
		response.sendRedirect("html/register.jsp?error=9");
		return;
	}
	// もし画像が選ばれていなければ、DBにはデフォルト名を保存する
	if (profileImage == null || profileImage.isEmpty()) {
	    profileImage = "default_icon.png"; // あらかじめ用意する画像名
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
		
		//DBのバリデーションチェック
		if (userDao.isEmailExists(email)) {
			//重複していたらerror=4を返す
			response.sendRedirect("html/register.jsp?error=5");
			return;
		}

		
		if(userDao.insert(user)) {
		    response.sendRedirect("html/login.jsp?registerSuccess=1"); 
		} else {
		    response.sendRedirect("/html/register.jsp"); 
		}
		}
	
	
	
	}
