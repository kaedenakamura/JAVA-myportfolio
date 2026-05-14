package myportfolio;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/contact")
public class ContactServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request , HttpServletResponse response)
	throws ServletException ,IOException{
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		ContactDao dao = new ContactDao();
		//ダッシュボードより新規お問合せ(new)とactionが一致しているときcontactFormへ飛ばす<a>タグ処理
		if("new".equals(action)) {
			//カテゴリー一覧を取得してリクエストにセット
			CategoryDao categoryDao = new CategoryDao();
			List<Category> categoryList = categoryDao.findAll();
			request.setAttribute("categoryList",categoryList);
			System.out.println("カテゴリーチェック"+categoryList);
			//WEB-INF内なのでdispatcher(内側)で呼び出す/sendRedirectは外部から
			request.getRequestDispatcher("/WEB-INF/jsp/contactForm.jsp")
			.forward(request, response);
			return;
			
		}
		
		//detailの actionの値がいっちしているとき
		//contactList.jspより詳細のボダン押されたとき処理
		if("detail".equals(action)) {
			//DBからfindByIdを使って、１件のIDを取得するメソッド
			int id  = Integer.parseInt(request.getParameter("id"));
			Contact contact = dao.findById(id);
			
			request.setAttribute("contact",contact);
			request.getRequestDispatcher("/WEB-INF/jsp/contactDetail.jsp")
			.forward(request,response);
			return;
		}
		
		//お問い合わせ一覧(dashboard.jsp)より押されたとき処理&戻るボタンなど上記に該当しない場合
		if("list".equals(action)) {
			//お問い合わせの情報がが一致していないとき一覧を返す
			List<Contact> list = dao.findAll();
			request.setAttribute("contactList", list);
			System.out.println("コンタクトリスト"+list);
			request.getRequestDispatcher("/WEB-INF/jsp/contactList.jsp")
			.forward(request,response);
			return;
		}
		}
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException , IOException{
		ContactDao dao = new ContactDao();
		String action = request.getParameter("action");
		System.out.println(action);
		String idStr = request.getParameter("id");
		String name =request.getParameter("name");
	    String statusStr = request.getParameter("status");
	    String email = request.getParameter("email");
	    String body =request.getParameter("body");
	    
	    //バリデーション
	    // メールアドレスの形式チェック（正規表現）
	    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		
		
	    //お問合せフォームからaction=insertで受け取る
	    if("insert".equals(action)) {
	    //先にバリデーションチェック
		//新規登録画面にて入力（register.jsp）した情報のバリデーションチェック
			//名前メールが空とnullならエラー
		if (name == null || name.isEmpty() || email == null || email.isEmpty() 
				) {
			request.setAttribute("error","名前もしくはメールアドレスが空です");
	    	request.getRequestDispatcher("/WEB-INF/jsp/contactForm.jsp").forward(request, response);
	    	return;}
		//名前、メールが２５０文字以上の場合はerrorを返すバリデーション
		if (email.length() >=250 || name.length()>=250 ) {
			request.setAttribute("error","メールアドレス、名前を250文字以内で入力してください");
	    	request.getRequestDispatcher("/WEB-INF/jsp/contactForm.jsp").forward(request, response);
	    	return;
		}
	    //メールアドレスの形式が正しくなく、エラーがある場合は入力画面へ戻す
	    if(!email.matches(emailPattern)) {
	    	request.setAttribute("error","メールアドレスの形式が正しくありません");
	    	request.getRequestDispatcher("/WEB-INF/jsp/contactForm.jsp").forward(request, response);
	    	return;
	    }
	    //お問合せフォーム内容がないときエラー
	    if(body == null || body.trim().isEmpty()) {
	    	request.setAttribute("error","お問い合わせ内容を入力してください");
	    	request.getRequestDispatcher("/WEB-INF/jsp/contactForm.jsp").forward(request,response);
	    	return;
	    }
	    	//バリデーションチェックが通れば処理を開始する
	    	//jspよりデータの回収
	    	String category =request.getParameter("category");
	    	//ガード
	    	if(name !=null && !name.isEmpty() && body !=null && !body.isEmpty()) {
	    		//Dao呼び出しセット
	    		Contact newContact = new Contact(name,email,category,body);
	    		System.out.println(name);
	    		//Daoinsertめどっど起動
	    		dao.insert(newContact);		
	    		
	    		//メール送信
	    		EmailSender.sendContactEmail(newContact);
	    		System.out.println(newContact);
	    		HttpSession session = request.getSession();
	    		session.setAttribute("success" , "お問合せ送信しました。ありがとうございました。");
	    		response.sendRedirect("contact?action=new");
	    		return;
	    	}
	    
	    	//contactdetail.jspよりボダン押されたとき処理
	    	}else if("contact".equals(action)) {
		    	if(idStr !=null && statusStr != null) {
				//Idとstatusをjspより取得してupdateStatusメソッドにてＤＢへ格納
				int id = Integer.parseInt(idStr);
			    int status = Integer.parseInt(statusStr);
				dao.updateStatus(id, status);
				response.sendRedirect("contact?action=list");
			    return;
				}
	    	}
	    }
				
	}
			
