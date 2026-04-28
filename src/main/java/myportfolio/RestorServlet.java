package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/restore")
public class RestorServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request , HttpServletResponse response)
			throws ServletException , IOException {
		//IDを取得して数値に変換
		String idStr = request.getParameter("id");
		//IDがnull出ない場合変換
		if(idStr != null) {
			int id = Integer.parseInt(idStr);
			UserDao dao = new UserDao();
			
			//DAOメソッド起動
			boolean isSuccess = dao.restor(id);
			
			//セッションをセット
			HttpSession session = request.getSession();
			
			if(isSuccess) {
				//成功メッセージをセット
				session.setAttribute("msg","ID:"+id +"ユーザーを復元しました" );
			}else {
				session.setAttribute("msg","復元に失敗しました");
			}
			//リダイレクトdeletedList→deleteList.jspへ
			response.sendRedirect("deletedList");
		}
		
	}
}
