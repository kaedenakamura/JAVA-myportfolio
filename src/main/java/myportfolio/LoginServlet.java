package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException ,IOException{
		//設定のお決まり
		request.setCharacterEncoding("UTF-8");
		String email =request.getParameter("email");
		String password =request.getParameter("password");
		System.out.println(request.getParameter("email"));
		System.out.println(request.getParameter("password"));
		
		//空チェック
		if (email ==null ||  email.isEmpty() || password ==null || password.isEmpty()) {
			response.sendRedirect("html/login.jsp?error=1");
			return ;
		}
		
		//DAOユーザー使ってユーザーを探す
		UserDao userDao = new UserDao();
		User user = userDao.findUser(email, password);
		System.out.println(user);
		
		//判定
		if (user !=null) {
			//セッション
			HttpSession session =request.getSession();
			session.setAttribute("LoginUser", user);
			
			System.out.println("ログイン成功:"+ user.getName() +"さん");
			response.sendRedirect("html/dashboard.jsp");//ダッシュボードへ
		}else {
			//ログイン失敗(rs.next()がfalseだったケース)
			System.out.println("ログイン失敗:メールアドレスまたはパスワードが違います");
			response.sendRedirect("html/login.jsp?error=1");
		}
		
	}
}
