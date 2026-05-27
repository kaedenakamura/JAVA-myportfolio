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

		if (AuthUtil.requireAdmin(request, response) == null) {
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
		request.setCharacterEncoding("UTF-8");
		ContactDao dao = new ContactDao();
		String action = request.getParameter("action");
		System.out.println(action);
		String idStr = request.getParameter("id");
		String name =request.getParameter("name");
	    String statusStr = request.getParameter("status");
	    String email = request.getParameter("email");
	    String body =request.getParameter("body");
	    String category = request.getParameter("category");
	    
	    //バリデーション
	    // メールアドレスの形式チェック（正規表現）
	    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		
		
	    //お問合せフォームからaction=insertで受け取る
	    if("insert".equals(action)) {
			if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
				forwardContactForm(request, response, "名前もしくはメールアドレスが空です", name, email, body, category);
				return;
			}
			//名前、メールが２５０文字以上の場合はerrorを返すバリデーション
			if (email.length() >=250 || name.length()>=250 ) {
				forwardContactForm(request, response, "メールアドレス、名前を250文字以内で入力してください", name, email, body, category);
				return;
			}
		    //メールアドレスの形式が正しくなく、エラーがある場合は入力画面へ戻す
		    if(!email.matches(emailPattern)) {
		    	forwardContactForm(request, response, "メールアドレスの形式が正しくありません", name, email, body, category);
		    	return;
		    }
		    //お問合せフォーム内容がないときエラー
		    if(body == null || body.trim().isEmpty()) {
		    	forwardContactForm(request, response, "お問い合わせ内容を入力してください", name, email, body, category);
		    	return;
		    }
			//カテゴリー未入力の際のバリデーションエラー
			if(category == null || category.trim().isEmpty()){
				forwardContactForm(request, response, "カテゴリーを選択してください", name, email, body, category);
				return;
			}

			Contact newContact = new Contact(name, email, category, body);
			System.out.println(name);
			dao.insert(newContact);

			EmailSender.sendContactEmail(newContact);
			System.out.println(newContact);
			HttpSession session = request.getSession();
			session.setAttribute("success" , "お問合せ送信しました。ありがとうございました。");
			response.sendRedirect("contact?action=new");
			return;

	    } else if("contact".equals(action)) {
	    	if (AuthUtil.requireAdmin(request, response) == null) {
	    		return;
	    	}
	    	if(idStr !=null && statusStr != null) {
				int id = Integer.parseInt(idStr);
			    int status = Integer.parseInt(statusStr);
				dao.updateStatus(id, status);
				response.sendRedirect("contact?action=list");
			    return;
			}
	    }
	}

	private void forwardContactForm(HttpServletRequest request, HttpServletResponse response,
			String error, String name, String email, String body, String category)
			throws ServletException, IOException {
		request.setAttribute("error", error);
		request.setAttribute("name", name != null ? name : "");
		request.setAttribute("email", email != null ? email : "");
		request.setAttribute("body", body != null ? body : "");
		request.setAttribute("category", category != null ? category : "");
		CategoryDao categoryDao = new CategoryDao();
		request.setAttribute("categoryList", categoryDao.findAll());
		request.getRequestDispatcher("/WEB-INF/jsp/contactForm.jsp").forward(request, response);
	}
}
