package myportfolio;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")

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
		PrintWriter out = response.getWriter();
		
	
	//　getParameterでhtmlよりimput
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		
		System.out.println("--- Servlet受け取りチェック---- ");
	    System.out.println("HTMLから届いた name: " + name);
	    System.out.println("HTMLから届いた email: " + email);
	    System.out.println("HTMLから届いた pass: " + password);
		
	// 司令塔からDAOへの命令
		User user = new User(email, password, name);
		UserDao userDao = new UserDao();
		
		out.println("<html><body>");
		if(userDao.insert(user)) {
			out.println("<h1>Webからの登録に成功しました!</h1>");
			out.println("<p>登録されたメールアドレス;"+email+"</p>");
		}else {
			out.println("<h1>登録に失敗しました....</h1>");
			out.println("<p>重複チェックやDB接続を確認してください<p>");
		}
		out.println("</body></html>");
		
		}
	
	
	
	}
