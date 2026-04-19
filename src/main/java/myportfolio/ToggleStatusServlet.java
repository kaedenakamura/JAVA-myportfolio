package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/toggleStatus")
public class ToggleStatusServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    // GETで来ても、そのまま doPost に丸投げする！
	    doPost(request, response);
	}
	//管理者と一般の切り替えるサーブレット
	protected void doPost(HttpServletRequest request , HttpServletResponse response)
		throws ServletException , IOException {
		//jspのformより送られてきた情報を取得
		String idStr = request.getParameter("id");
        String roleStr = request.getParameter("newRole");
		
		//データチェック
		
		if (idStr != null && roleStr != null) {
			//文字列を整数intに変換
			int id = Integer.parseInt(idStr);
			int newRole = Integer.parseInt(roleStr);
			
			//DAOのインスタンスを使ってメソッドを起動
			UserDao userDao = new UserDao();
			boolean isSuccess = userDao.updateRole(id ,newRole);
			
			//結果に応じてメッセージをセッションに詰める
			if (isSuccess) {
				request.getSession().setAttribute("msg" ,"権限を更新しました！");
			}else {
				request.getSession().setAttribute("msg","更新に失敗しました");
			}
		}
		//最後に一覧画面を再表示
		response.sendRedirect("list");
	}
}