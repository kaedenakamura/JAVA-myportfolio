package myportfolio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	        
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("LoginUser");

        // ログインチェック
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 管理者かチェックちがったらmyPageサーブレットへ
        if (loginUser.getRole() != 1) {
            // 管理者でないなら一般ページへ
            response.sendRedirect(request.getContextPath() + "/userMyPage");
            return;
        }

        try {
            UserDao dao = new UserDao();
            
            // 各メソッドが正しくListを返しているか確認
            List<User> monthlyRanking = dao.getMonthlyRanking();
            List<User> yearlyRanking = dao.YearlyRanking();
            int activeCount = dao.countActive();

            // 念のため、nullだったら空のリストを入れる
            request.setAttribute("monthlyRanking", monthlyRanking != null ? monthlyRanking : new ArrayList<User>());
            request.setAttribute("yearlyRanking", yearlyRanking != null ? yearlyRanking : new ArrayList<User>());
            request.setAttribute("activeCount", activeCount);

            request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
        	System.out.println("ダッシュボード表示中にエラーが発生しました");
            e.printStackTrace(); 
           
        }
    }
	}