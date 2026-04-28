package myportfolio;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request , HttpServletResponse response)
	throws ServletException ,IOException{
		
		//セッションからloginUserの情報取得
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("LoginUser");
		System.out.println(loginUser);
		
		//ログインチェック　＆ 管理者チェック（ガード区）
		//loginUserがnullまたは、roleが１ではないときガード
		if(loginUser ==null || loginUser.getRole() !=1) {
			
		}
		
		//Daoメソッド起動
		UserDao dao = new UserDao();
		
		//今月のいいねランキングの取得
		List<User> monthlyRanking =dao.getMonthlyRanking();
		//年間ランキングの取得
		List<User> yearlyRanking =dao.YearlyRanking();
		
		//統計データの取得といいねランキングのメソッドを利用
		int activeCount = dao.countActive();
		
		
		// データをjspへ
		request.setAttribute("activeCount", activeCount);
		request.setAttribute("monthlyRanking",monthlyRanking);
		request.setAttribute("yearlyRanking", yearlyRanking);
		
	
	//ダッシュボードJSPへ移動
		
	request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
}
}
