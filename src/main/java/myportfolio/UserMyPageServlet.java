package myportfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/userMyPage")
public class UserMyPageServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request ,HttpServletResponse response)
	throws ServletException , IOException {
		User loginUser = AuthUtil.requireUser(request, response);
		if (loginUser == null) {
			return;
		}
		HttpSession session = request.getSession();
		//デバック
		System.out.println(loginUser);
		//DAOメソッド起動
		UserDao dao =new UserDao();
		//IDより個人の全情報の取得（最新の）(loginSevletでfindUserしたときにIDを取得している)
		User latestUserInfo = dao.findById(loginUser.getId());
		
		//万が一DB側で削除されていた場合などのケア
		if(latestUserInfo == null) {
			//セッション破棄2
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/html/login.jsp");
			return;
		}
		//全情報をセット
		request.setAttribute("user",latestUserInfo);
		
		//今月のいいねランキングの取得
		int userId = loginUser.getId();
		
		//countLikesByPeriodメソッド起動し、いいね数取得・引数（monthlyとyearly）切替にて月間.年間取得
		int monthlyLikes = dao.countLikesByPeriod(userId, "monthly");
		int yearlyLikes = dao.countLikesByPeriod(userId,"yearly");
		
		//セット
		request.setAttribute("monthlyLikes",monthlyLikes);
		request.setAttribute("yearlyLikes",yearlyLikes);
		
		//デバック
		System.out.println(monthlyLikes);
		System.out.println(yearlyLikes);
		
		//mypage.jspへ移動
		request.getRequestDispatcher("/WEB-INF/jsp/userMyPage.jsp").forward(request, response);
		 
		
	}
}
