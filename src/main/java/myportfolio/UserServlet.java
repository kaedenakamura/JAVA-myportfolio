package myportfolio;

import java.io.IOException;

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
		request.setCharacterEncoding("UTF-8");
		
		
	
	//　getParameterでhtmlよりimput
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		
	if (name == null || name.isEmpty() || name.length()>250 || email == null || email.isEmpty() ||
			password ==null || password.length()< 8
			) {
		response.sendRedirect("html/register.jsp?error=1");
		
		System.out.println("バリデーションエラー：空のデータがあるため登録を中止します");
		return;
			}
		
		// 受け取りチェック
		System.out.println("--- Servlet受け取りチェック---- ");
	    System.out.println("HTMLから届いた name: " + name);
	    System.out.println("HTMLから届いた email: " + email);
	    System.out.println("HTMLから届いた pass: " + password);
		
	// 司令塔からDAOへの命令
		User user = new User(email, password, name);
		UserDao userDao = new UserDao();
		
		if(userDao.insert(user)) {
		    response.sendRedirect("list"); 
		} else {
		    response.sendRedirect("/html/register.jsp"); 
		}
		}
	
	
	
	}
