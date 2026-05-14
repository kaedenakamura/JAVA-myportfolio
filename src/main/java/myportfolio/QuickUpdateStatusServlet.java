package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//Update.jsp-updateservletよりステータスの変更のサーブレット

@WebServlet("/quickUpdateStatus")

public class QuickUpdateStatusServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException{
		
		// 1. まずは文字列として受け取る
		String idStr = request.getParameter("id");
		String nextStatusStr = request.getParameter("nextStatus");

		// 2. 「空じゃない」ことを確認してから変換する
		if (idStr != null && !idStr.isEmpty() && nextStatusStr != null && !nextStatusStr.isEmpty()) {
		    
		    int id = Integer.parseInt(idStr);
		    int nextStatus = Integer.parseInt(nextStatusStr);
		    
		    System.out.println("エラー：idまたはstatusが空で届きました"+id+nextStatus);
		    
		    UserDao dao = new UserDao();
		    boolean isSuccess = dao.updateStatus(id, nextStatus);
		    
		    if (isSuccess) {
		        request.getSession().setAttribute("msg", "ステータスを更新しました");
		    }
		} else {
		    // 値が空かチェック
		    System.out.println("エラー：idまたはstatusが空で届きました"+idStr+nextStatusStr);
		}

		// 3. 最後にリダイレクト
		response.sendRedirect("list");
	}
}
