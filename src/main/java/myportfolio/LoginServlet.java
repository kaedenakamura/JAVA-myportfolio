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
	
	// JSの window.location.href で呼ばれた時に動かすメソッド
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 直接JSPに飛ばす（フォワード）
    	// 今探そうとしている場所をコンソールに出す
        System.out.println("探しているJSPのパス: " + getServletContext().getRealPath("/html/login.jsp"));
    	request.getRequestDispatcher("/html/login.jsp").forward(request, response);
    }

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
		//findUserメソッドにてID取得
		User user = userDao.findUser(email, password);
		System.out.println(user);
		
		
		//判定
		if (user !=null) {
			//セッション
			HttpSession session =request.getSession();
			session.setAttribute("LoginUser", user);
			//ログイン失敗(rs.next()がfalseだったケース)
		}else {	System.out.println("ログイン失敗:メールアドレスまたはパスワードが違います");
		
		// 入力されたメールアドレスをリクエストへ保存
	    request.setAttribute("email", email);
	    System.out.println("ログイン失敗時のメールを格納"+email);
	    request.getRequestDispatcher("/html/login.jsp?error=1").forward(request, response);
		}
			//ユーザーが管理者（１）の時
		if(user.getRole()==1) {
				System.out.println("管理者ログイン成功:"+ user.getName() +"さん");
				response.sendRedirect("dashboard");//ダッシュボードへ(管理者)
			//ユーザーが管理者（０）の時	
			}else if(user.getRole()==0) {
				System.out.println("一般ユーザーログイン成功"+user.getName()+"さん");
				response.sendRedirect(request.getContextPath()+"/userMyPage");
				
		}
		}
	}
