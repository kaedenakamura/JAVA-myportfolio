package myportfolio;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProfileEditServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request , HttpServletResponse response)
		throws ServletException ,IOException {
		//文字化け防ぎ
		request.setCharacterEncoding("UTF-8");
		
		//loginServlet-myPage-よりLoginUserの受け取り
		HttpSession session = request.getSession();
		User loginUser =(User)session.getAttribute("LoginUser");
		System.out.println(loginUser);
		//ガード、ログインしてない場合のバリデーション(ログインサーブレットに飛ばす)
		if(loginUser == null) {
			response.sendRedirect(request.getContextPath()+"/html/login.jsp");
			return;
		}
		UserDao dao = new UserDao();
		//IDをつかって全情報を受け取る
		User latestUserInfo = dao.findById(loginUser.getId());
		//万が一DB側で削除されていた場合などのケア
				if(latestUserInfo == null) {
					//セッション破棄2
					session.invalidate();
					response.sendRedirect(request.getContextPath()+"/html/login.jsp");
					return;
				}
		//latestUserInfoで最新ユーザーの全情報をセットしてjspへ
		request.setAttribute("LoginUser",latestUserInfo);
		request.getRequestDispatcher("WEB-INF/jsp/profileEdit.jsp")
		.forward(request, response);
				
		
	}
	
	protected void doPost(HttpServletRequest request ,HttpServletResponse response) 
		throws IOException , ServletException{
		
		//文字化け防ぎ
		request.setCharacterEncoding("UTF-8");
		
		UserDao dao = new UserDao();
		HttpSession session = request.getSession();
		User loginUser =(User)session.getAttribute("LoginUser");
		System.out.println(loginUser);
		
		
	}
	
	
}
