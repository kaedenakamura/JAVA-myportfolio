package myportfolio;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/list")
public class UserListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request , HttpServletResponse response)
                  throws ServletException, IOException {
		//daoからユーザー取得し、DBからユーザのリストを取得する。
		UserDao userDao = new UserDao();
		List<User> userList = userDao.findAll();
		
		//ユーザーリストのセット(jdpに出力するためにsetSttributeでリクエストスコープに保存する。)
		request.setAttribute("userList",userList);
		
		 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp");
		 dispatcher.forward(request, response);
		
		// テスト用：画面に直接文字列を出力
		//response.setContentType("text/html; charset=UTF-8");
		//response.getWriter().println("データは " + userList.size() + " 件見つかった。");
}
}