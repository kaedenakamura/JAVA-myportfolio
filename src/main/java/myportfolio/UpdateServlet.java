package myportfolio;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request , HttpServletResponse response)
		throws ServletException,IOException{
		
		//文字化けの防止
		request.setCharacterEncoding("UTF-8");
		
		//getParameterで値を受け取る
		String idStr = request.getParameter("id");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		// StringのIDをintに変換(Integer.parseInt)
		int id =Integer.parseInt(idStr);
		
		//オブジェクトへ（インスタンス化）
		User user = new User(id,name,email);
		
		//DAOメソッド起動
		UserDao userDao = new UserDao();
		boolean isSuccess = userDao.update(user);
		
		//判定してリダイレクト
		if(isSuccess) {
			//成功したらダッシュボードへ戻る
			response.sendRedirect("html/dashboard.jsp?update=success");
		}else {
			response.sendRedirect("html/dashboard.jsp?error=update_failed");
		}
		
	}

}
