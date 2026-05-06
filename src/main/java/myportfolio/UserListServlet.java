package myportfolio;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/list")
public class UserListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request , HttpServletResponse response)
                  throws ServletException, IOException {
		HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("LoginUser");

        // ログインチェック
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

		
		
		//ページ番号の受け取り
		String pageStr = request.getParameter("page");
		int page = 1 ;
		
		try {
			if(pageStr != null && !pageStr.isEmpty()) {
				page = Integer.parseInt(pageStr);
			}
		}catch(NumberFormatException e) {
			//エラー来たら１ページ目に戻す作用
			page = 1;
		}
		
		//daoからユーザー取得し、findAllを削除し、findBypageメソッドへ切替
		//find all は負担大きいからリファクタリング＋ページネーションの実装
		UserDao userDao = new UserDao();
		List<User> userList = userDao.findByPage(page);
		//全ページの計算
		int totalUserCount = userDao.countAll();
		//ページ５件
		int limit = 5;
		//全ページ数の計算（端数切り上げ）
		int maxPage =(int)Math.ceil((double) totalUserCount / limit);
		
		if(userList !=null && userList.size() != 0){
    		for(User user : userList){
    		System.out.println(user.getId());
    		System.out.println(user.getName());
    		System.out.println(user.getEmail());
    		}
		}
		
		
		//ユーザーリストのセット(jdpに出力するためにsetSttributeでリクエストスコープに保存する。)
		request.setAttribute("userList",userList);
		//jspで今何ページか見る
		request.setAttribute("currentPage",page);
		//jspで最大ページ数を伝える！
		request.setAttribute("maxPage", maxPage);
		//despacherで一時的にuserListjspへリクエスト
		 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp");
		 dispatcher.forward(request, response);
		
		// テスト用：画面に直接文字列を出力(初めはgetwriterで処理その後リファクタリング)
		//response.setContentType("text/html; charset=UTF-8");
		//response.getWriter().println("データは " + userList.size() + " 件見つかった。");
}
}