package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/physicalDelete")
public class PysicalDeleteServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request ,HttpServletResponse response)
	throws ServletException , IOException{
		//IDを取得して変換
		String idStr = request.getParameter("id");
		
		//IDを取得して数値に変換
		int id = Integer.parseInt(idStr);
		//メソッド起動
		UserDao dao = new UserDao();
		
		//deleteメソッド起動
		boolean isSuccess = dao.delete(id);
		
		HttpSession session = request.getSession();
		
		//成功したら
		if(isSuccess) {
			session.setAttribute("msg","削除に成功しました");
		}else {
			session.setAttribute("msg", "削除に失敗しました");
		}
		//処理後にリダイレクト
		response.sendRedirect("deletedList");
	}
}
