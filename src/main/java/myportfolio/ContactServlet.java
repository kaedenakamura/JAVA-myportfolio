package myportfolio;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
		//お問い合わせ一覧(dashboard.jsp)より押されたとき処理&戻るボタンなど上記に該当しない場合
		}
		
			//お問い合わせの情報がが一致していないとき一覧を返す
			List<Contact> list = dao.findAll();
			request.setAttribute("categoryList", list);
			System.out.println("カテゴリーグループ"+list);
			request.getRequestDispatcher("/WEB-INF/jsp/contactList.jsp")
			.forward(request,response);
			return;
		}
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException , IOException{
		ContactDao dao = new ContactDao();
		String action = request.getParameter("action");
		System.out.println(action);
		String idStr = request.getParameter("id");
	    String statusStr = request.getParameter("status");
		
	    //お問合せフォームからaction=insertで受け取る
	    if("insert".equals(action)) {
	    	//jspよりデータの回収
	    	String name =request.getParameter("name");
	    	String email = request.getParameter("email");
	    	String category =request.getParameter("category");
	    	String body =request.getParameter("body");
	    	//ガード
	    	if(name !=null && !name.isEmpty() && body !=null && !body.isEmpty()) {
	    		//Dao呼び出しセット
	    		Contact newContact = new Contact(name,email,category,body);
	    		
	    		//Daoinsertめどっど起動
	    		dao.insert(newContact);		
	    		
	    		//メール送信
	    		EmailSender.sendContactEmail(newContact);
	    	}
	    	}else if(idStr !=null && statusStr != null) {
					//Idとstatusをjspより取得してupdateStatusメソッドにてＤＢへ格納
					int id = Integer.parseInt(idStr);
				    int status = Integer.parseInt(statusStr);
					dao.updateStatus(id, status);
					}
					response.sendRedirect("contact");
				}
	
}
