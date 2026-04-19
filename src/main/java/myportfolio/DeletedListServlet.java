package myportfolio;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/deletedList")
public class DeletedListServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException , IOException{
		//定番の文字化け防ぎ
		request.setCharacterEncoding("UTF-8");
		
		//daoメソッド使用のためインスタンス化
		UserDao dao = new UserDao();
		
		//メソッド起動消去済みの人のみリストアップ
		List<User> deletedUsers = dao.findDeletedAll();
		
		//jspへ渡すためsetAttribute
		request.setAttribute("deletedUsers", deletedUsers);
		
		//jsp フォワード
		request.getRequestDispatcher("/WEB-INF/jsp/deletedList.jsp").forward(request,response);
		
	}
}
