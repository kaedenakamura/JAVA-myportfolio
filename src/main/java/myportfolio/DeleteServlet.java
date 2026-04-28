package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	protected static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request , HttpServletResponse response) 
	throws ServletException ,IOException {
		
		//セッションチェック(LoginUser)
		//HttpSession session = request.getSession();
		//User loginUser =(User) session.getAttribute("LoginUser");
		
		//if(loginUser==null) {
			//response.sendRedirect("html/login.jsp");
			//return;
			//}
		
		//設定お決まり
		request.setCharacterEncoding("UTF-8");
		//parameter受け取り
		String idStr = request.getParameter("id");
		System.out.println(idStr);
		HttpSession session = request.getSession();
		
		if (idStr != null) {
		try {
			//文字列を数字に変換
			int id =Integer.parseInt(idStr);
			
			//DAOを使って削除を実行
			UserDao dao = new UserDao();
			boolean isSuccess = dao.softDelete(id);
			//System.out.println(id+"の削除が完了しました");
			//削除完了したらjsp画面にsetAttribute
		if(isSuccess) {
			session.setAttribute("msg","ID"+idStr+"のユーザーを削除しました。");
			System.out.println(id + "の論理削除が完了しました");
		}else {
			session.setAttribute("msg","削除に失敗しました。（対象が見つかりません）");
		}
		}catch(NumberFormatException e){		
			e.printStackTrace();
			System.out.println("エラー；ID形式が不正(数字以外)");
		}
		}else
			System.out.println("IDが見つかりませんでした");
		//処理後にリダイレクトを行う
		response.sendRedirect("list");
	} 
	
}
